package br.com.esec.icpm.samples.ap.core;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionStatus;
import br.com.esec.mss.ap.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This example shows how to request the signature of on XMLDSIG_ENVELOPING document.
 * 
 * See our manual for further informations. It's possible to sign as XMLDSIG_ENVELOPED and use other parameters like sign by attribute name or Id.
 *
 * To get the response, this example uses the "polling" method, which periodically check the status of the transaction
 * with the server. In a real application, you should move this "polling" logic to an appropriate mechanism, such as an
 * ExecutorService, TimerService, etc.
 */
public class SignXmldsigSample {

	private static final Logger log = LoggerFactory.getLogger(SignXmldsigSample.class);
	private static SignaturePortType signatureEndpoint = null;
	private static final String BASE_TYPE = "xmldsig_";
	private static SignatureStandardType standard;
	private static String[] ids;

	public static void main(String[] args) throws Exception {

		// validate args length
		if (args.length < 4) {
			System.out.println(
					"usage: certillion-ap-samples sign-xmldsig [identifier] [message] [file] \n" +
					"\n" +
					"\t identifier: email of the user \n" +
					"\t message: text to be displayed \n" +
					"\t standardType: [enveloping] or [enveloped] \n" +
					"\t IDs: xml ids to be signed (only for enveloped type). Ex: [1,2,3] \n" +
					"\t file: path for one file to be signed \n"
			);
			System.exit(1);
		}

		// get args
		String uniqueIdentifier = args[1];
		String dataToBeDisplayed = args[2];
		standard = SignatureStandardType.fromValue(BASE_TYPE+args[3]);
		String[] filesPath;
		if(standard.equals(SignatureStandardType.XMLDSIG_ENVELOPED)){
			ids = args[4].split(",");
			filesPath = Arrays.copyOfRange(args, 5, args.length);
		}else{
			filesPath = Arrays.copyOfRange(args, 4, args.length);
		}
		List<BatchInfoType> documents = uploadFiles(filesPath);

		// connnect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(Constants.WSDL_URL);
		Service signatureService = Service.create(serviceUrl, Constants.SERVICE_QNAME);
		signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		UserType mobileUser = new UserType();
		mobileUser.setIdentifier(uniqueIdentifier);

		// mount the "batch-signature" request
		BatchSignatureReqTypeV2 batchSignatureReq = new BatchSignatureReqTypeV2();
		batchSignatureReq.setUser(mobileUser);
		batchSignatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		batchSignatureReq.setDataToBeDisplayed(dataToBeDisplayed);
		batchSignatureReq.setSignatureStandard(standard);
//		batchSignatureComplexDocumentReqType.setSignaturePolicy(SignaturePolicyType.AD_RT);

		batchSignatureReq.getDocumentsToBeSigned().addAll(documents);

		try {
			// send the "batch-signature" request to server
			log.info("Sending request...");
			BatchSignatureRespTypeV2 batchSignatureResp = signatureEndpoint.batchSignatureV2(batchSignatureReq);
			CertillionStatus batchSignatureRespValue = CertillionStatus.valueOf(batchSignatureResp.getStatus().getStatusMessage());

			// check the "batch-signature" response
			if (batchSignatureRespValue != CertillionStatus.REQUEST_OK) {
				log.error("Error sending request, server returned {}", batchSignatureRespValue);
				System.exit(1);
			}

			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			statusReq.setTransactionId(batchSignatureResp.getTransactionId());

			// send the "get-status" request to server
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			BatchSignatureTIDsRespType statusResp = null;
			CertillionStatus statusRespValue = null;
			do {
				log.info("Waiting signature from user...");
				statusResp = signatureEndpoint.batchSignatureTIDsStatus(statusReq);
				log.info("Mobile Status is: "+ statusResp.getStatus().getMobileStatus());
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
				Thread.sleep(10000); // sleep for 10 seconds or the server will mark you as flood
			} while (statusResp.getStatus().getStatusCode() == CertillionStatus.TRANSACTION_IN_PROGRESS.getCode());

			// check the "get-status" response
			if (statusRespValue != CertillionStatus.REQUEST_OK) {
				log.error("Error receiving the response, the status is {}", statusRespValue);
				System.exit(1);
			}

			// extract the signatures from the response
			List<DocumentSignatureStatusInfoType> documentsStatus = statusResp.getDocumentSignatureStatus();
			log.info("Signature received successfully.");

			// saves signatures
			for (DocumentSignatureStatusInfoType doc : documentsStatus) {
				saveAttached(doc);
			}

		} catch (Exception e) {
			log.error("Could not complete the example", e);
		}
	}

	private static void validateArgs(String[] args) {
		String extension = args[3].substring(args[3].lastIndexOf('.'));
		for (int i = 2; i < args.length; i++) {
			if (!args[i].endsWith(extension)) {
				System.out.println("All files must have the same extension");
				System.exit(1);
			}
		}
	}

	private static List<BatchInfoType> uploadFiles(String[] paths) throws IOException {
		List<BatchInfoType> result = new ArrayList<BatchInfoType>();

		for (String path : paths) {

			// check if file exists
			File file = new File(path);
			URL fileUrl = file.toURI().toURL();
			if(!file.exists()) {
				throw new IllegalStateException("The file " + path + " can not be found.");
			}

			// upload file via REST
			log.info("Uploading file {} ...", path);
			URL restUrl = new URL(Constants.REST_URL + "/uploadDocument");
			HttpURLConnection connection = (HttpURLConnection) restUrl.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/octet-stream");
			IOUtils.copy(fileUrl.openStream(), connection.getOutputStream());
			String response = IOUtils.toString(connection.getInputStream());
			log.info("File uploaded, hash is {}", response);

			// save the ID of the uploaded document (which is it's hash)
			BatchInfoType documentInfo = new BatchInfoType();
			documentInfo.setDocumentName(FilenameUtils.getName(path));
			documentInfo.setHash(response);
			documentInfo.setContentType("application/octet-stream");
			documentInfo.setUrlToDocument(path);
			if(standard.equals(SignatureStandardType.XMLDSIG_ENVELOPED)){
				ElementsIdXmldsigOptionType xmlOptions = new ElementsIdXmldsigOptionType();
				xmlOptions.getValue().addAll(Arrays.asList(ids));
				SignatureStandardOptionsType signatureOptions = new SignatureStandardOptionsType();
				signatureOptions.getElementsIdOrAttributeIdNameOrElementsName().add(xmlOptions);
				documentInfo.setSignatureStandardOptions(signatureOptions);
			}
			result.add(documentInfo);
		}

		return result;
	}

	private static void saveAttached(DocumentSignatureStatusInfoType documentInfo) throws IOException {

		// check if the user rejected this file
		CertillionStatus status = CertillionStatus.valueOf(documentInfo.getStatus().getStatusMessage());
		if (status != CertillionStatus.SIGNATURE_VALID) {
			log.warn("Not saving signature of document {}, status is {}", documentInfo.getDocumentName(), status);
			return;
		}

		// save signature
		URL url = new URL(Constants.REST_URL + "/document/signed/" + documentInfo.getTransactionId());
		InputStream inputStream = url.openStream();
		String outputFileName = "signature-" + documentInfo.getTransactionId() + ".xml";
		FileOutputStream outputStream = new FileOutputStream(outputFileName);
		IOUtils.copy(inputStream, outputStream);
		IOUtils.closeQuietly(inputStream);
		IOUtils.closeQuietly(outputStream);
		log.info("Signature saved in file {}", outputFileName);
	}

}
