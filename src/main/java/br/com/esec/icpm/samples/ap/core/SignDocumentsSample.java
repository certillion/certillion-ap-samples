package br.com.esec.icpm.samples.ap.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.ws.Service;

import br.com.esec.icpm.mss.ws.*;
import br.com.esec.icpm.samples.ap.core.WebServiceInfo;
import br.com.esec.icpm.server.ws.SignaturePolicyType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import br.com.esec.icpm.libs.signature.helper.MimeTypeConstants;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example shows how to request the signature of a list of documents.
 *
 * To get the response, this example uses the "polling" method, which periodically check the status of the transaction
 * with the server. In a real application, you should move this "polling" logic to an appropriate mechanism, such as an
 * ExecutorService, TimerService, etc.
 */
public class SignDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(SignDocumentsSample.class);
	private static SignaturePortType signatureEndpoint = null;

	public static void main(String[] args) throws Exception {

		// validate args length
		if (args.length < 3) {
			System.out.println("SignDocumentsSample need this params: [uniqueIdentifier] [dataToBeDisplayed] [filesPath] ");
			System.exit(1);
		}

		// get args
		String uniqueIdentifier = args[0];
		String dataToBeDisplayed = args[1];
		String[] filesPath = Arrays.copyOfRange(args, 2, args.length);
		SignatureStandardType standard = getStandardFromExtension(filesPath[0]);
		validateArgs(args);

		List<HashDocumentInfoType> documents = uploadFiles(filesPath);

		// connnect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(WebServiceInfo.getApServiceUrl());
		Service signatureService = Service.create(serviceUrl, SignaturePortType.QNAME);
		signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// mount the "batch-signature" request
		BatchSignatureComplexDocumentReqType batchSignatureReq = new BatchSignatureComplexDocumentReqType();
		batchSignatureReq.setMobileUser(mobileUser);
		batchSignatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		batchSignatureReq.setDataToBeDisplayed(dataToBeDisplayed);
		batchSignatureReq.setSignatureStandard(standard);
		batchSignatureReq.setDocumentsToBeSigned(documents);
//		batchSignatureComplexDocumentReqType.setSignaturePolicy(SignaturePolicyType.AD_RT);

		try {
			// send the "batch-signature" request to server
			log.info("Sending request...");
			BatchSignatureComplexDocumentRespType batchSignatureResp = signatureEndpoint.batchSignatureComplexDocument(batchSignatureReq);
			Status batchSignatureRespValue = Status.valueOf(batchSignatureResp.getStatus().getStatusMessage());
			long transactionId = batchSignatureResp.getTransactionId();

			// check the "batch-signature" response
			if (batchSignatureRespValue != Status.REQUEST_OK) {
				log.error("Error sending request, server returned {}", batchSignatureRespValue);
				System.exit(1);
			}

			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			statusReq.setTransactionId(batchSignatureResp.getTransactionId());

			// send the "get-status" request to server
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			BatchSignatureTIDsRespType statusResp = null;
			Status statusRespValue = null;
			do {
				log.info("Waiting signature from user...");
				statusResp = signatureEndpoint.batchSignatureTIDsStatus(statusReq);
				statusRespValue = Status.valueOf(statusResp.getStatus().getStatusMessage());
				Thread.sleep(10000); // sleep for 10 seconds or the server will mark you as flood
			} while (statusResp.getStatus().getStatusCode() == Status.TRANSACTION_IN_PROGRESS.getCode());

			// check the "get-status" response
			if (statusRespValue != Status.REQUEST_OK) {
				log.error("Error receiving the response, the status is {}", statusRespValue);
				System.exit(1);
			}

			// extract the signatures from the response
			List<DocumentSignatureStatusInfoType> documentsStatus = statusResp.getDocumentSignatureStatus();
			log.info("Signature received successfully.");

			// saves signatures
			for (DocumentSignatureStatusInfoType doc : documentsStatus) {
				if (standard == SignatureStandardType.CADES) {
					saveDetached(doc, getExtensionFromStandard(standard));
				} else {
					saveAttached(doc, getExtensionFromStandard(standard));
				}
			}

		} catch (Exception e) {
			log.error("Could not complete the example", e);
		}
	}

	private static void validateArgs(String[] args) {
		String extension = args[2].substring(args[2].lastIndexOf('.'));
		for (int i = 2; i < args.length; i++) {
			if (!args[i].endsWith(extension)) {
				System.out.println("All files must have the same extension");
				System.exit(1);
			}
		}
	}

	private static List<HashDocumentInfoType> uploadFiles(String[] paths) throws IOException {
		List<HashDocumentInfoType> result = new ArrayList<HashDocumentInfoType>();

		for (String path : paths) {

			// check if file exists
			File file = new File(path);
			URL fileUrl = file.toURI().toURL();
			if(!file.exists()) {
				throw new IllegalStateException("The file " + path + " can not be found.");
			}

			// upload file via REST
			log.info("Uploading file {} ...", path);
			URL restUrl = new URL(WebServiceInfo.getUploadDocumentUrl());
			HttpURLConnection connection = (HttpURLConnection) restUrl.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/octet-stream");
			IOUtils.copy(fileUrl.openStream(), connection.getOutputStream());
			String response = IOUtils.toString(connection.getInputStream());
			log.info("File uploaded, hash is {}", response);

			// save the ID of the uploaded document (which is it's hash)
			HashDocumentInfoType documentInfo = new HashDocumentInfoType();
			documentInfo.setDocumentName(FilenameUtils.getName(path));
			documentInfo.setHash(response);
			documentInfo.setContentType(MimeTypeConstants.getMimeType(FilenameUtils.getExtension(path).toLowerCase()));
			documentInfo.setUrlToDocument(path);
			result.add(documentInfo);
		}

		return result;
	}

	private static void saveDetached(DocumentSignatureStatusInfoType documentInfo, String extension) throws IOException, ICPMException {

		// check if the user rejected this file
		Status status = Status.valueOf(documentInfo.getStatus().getStatusMessage());
		if (status != Status.SIGNATURE_VALID) {
			log.warn("Not saving signature of document {}, status is {}", documentInfo.getDocumentName(), status);
			return;
		}

		// save signature
		SignatureStatusReqType request = new SignatureStatusReqType();
		request.setTransactionId(documentInfo.getTransactionId());
		SignatureStatusRespType response = signatureEndpoint.statusQuery(request);
		DataHandler signature = response.getSignature();
		String outputFileName = "signature-" + documentInfo.getTransactionId() + extension;
		FileOutputStream output = new FileOutputStream(outputFileName);
		IOUtils.copy(signature.getInputStream(), output);
		output.close();
		log.info("Signature saved in file {}", outputFileName);
	}

	private static void saveAttached(DocumentSignatureStatusInfoType documentInfo, String extension) throws IOException {

		// check if the user rejected this file
		Status status = Status.valueOf(documentInfo.getStatus().getStatusMessage());
		if (status != Status.SIGNATURE_VALID) {
			log.warn("Not saving signature of document {}, status is {}", documentInfo.getDocumentName(), status);
			return;
		}

		// save signature
		URL url = new URL(WebServiceInfo.getDownloadDocumentUrl() + documentInfo.getTransactionId());
		InputStream inputStream = url.openStream();
		String outputFileName = "signature-" + documentInfo.getTransactionId() + extension;
		FileOutputStream outputStream = new FileOutputStream(outputFileName);
		IOUtils.copy(inputStream, outputStream);
		IOUtils.closeQuietly(inputStream);
		IOUtils.closeQuietly(outputStream);
		log.info("Signature saved in file {}", outputFileName);
	}

	private static SignatureStandardType getStandardFromExtension(String filePath) {
		if (filePath.endsWith(".pdf")) {
			return SignatureStandardType.ADOBEPDF;
		} else if (filePath.endsWith(".xml")) {
			return SignatureStandardType.XADES;
		} else {
			return SignatureStandardType.CADES;
		}
	}

	private static String getExtensionFromStandard(SignatureStandardType standard) {
		switch (standard) {
			case CADES:
				return ".p7s";
			case XADES:
				return ".xml";
			case ADOBEPDF:
				return ".pdf";
			default:
				throw new IllegalStateException("Unknown standard");
		}
	}

}