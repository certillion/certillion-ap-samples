package br.com.esec.icpm.samples.ap.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionStatus;
import br.com.esec.mss.ap.BatchInfoType;
import br.com.esec.mss.ap.BatchSignatureReqTypeV2;
import br.com.esec.mss.ap.BatchSignatureRespTypeV2;
import br.com.esec.mss.ap.BatchSignatureTIDsRespType;
import br.com.esec.mss.ap.CertificateFiltersType;
import br.com.esec.mss.ap.DocumentSignatureStatusInfoType;
import br.com.esec.mss.ap.HsmCertificateFilterType;
import br.com.esec.mss.ap.MessagingModeType;
import br.com.esec.mss.ap.MobileUserType;
import br.com.esec.mss.ap.SignaturePortType;
import br.com.esec.mss.ap.SignatureStandardType;
import br.com.esec.mss.ap.SignatureStatusReqType;

public class SignHsmSample {
	private static final Logger log = LoggerFactory.getLogger(SignHsmSample.class);
	private static SignaturePortType signatureEndpoint = null;
	private static SignatureStandardType standard;
	private static HsmCertificateFilterType hsmFilter;
	private static CertificateFiltersType filter;

	public static void main(String[] args) throws Exception {

		// validate args length
		if (args.length < 5) {
			System.out.println(
					"usage: certillion-ap-samples sign-hsm [identifier] [message] [fingerprint] [standardType] [file] \n"
							+ "\n" + "\t identifier: email of the user \n" + "\t message: text to be displayed \n"
							+ "\t fingerprint: user fingerprint for identification \n"
							+ "\t standardType: [cades] , [xades] or [adobeftp] \n"
							+ "\t file: path for one file to be signed \n");
			System.exit(1);
		}

		// get args
		String uniqueIdentifier = args[1];
		String dataToBeDisplayed = args[2];
		String fingerprint = args[3];
		standard = SignatureStandardType.fromValue(args[4]);
		String[] filesPath = Arrays.copyOfRange(args, 5, args.length);

		List<BatchInfoType> documents = uploadFiles(filesPath);

		// connnect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(Constants.WSDL_URL);
		Service signatureService = Service.create(serviceUrl, Constants.SERVICE_QNAME);
		signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// mount the "batch-signature" request
		BatchSignatureReqTypeV2 batchSignatureReq = new BatchSignatureReqTypeV2();
		batchSignatureReq.setMobileUser(mobileUser);
		batchSignatureReq.setFingerprint(fingerprint);
		batchSignatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		batchSignatureReq.setDataToBeDisplayed(dataToBeDisplayed);
		batchSignatureReq.setSignatureStandard(standard);
		hsmFilter = new HsmCertificateFilterType();
		hsmFilter.setValue(true);
		filter = new CertificateFiltersType();
		filter.getTrustChainOrOwnerCertificateOrAlgorithm().add(hsmFilter);
		batchSignatureReq.setCertificateFilters(filter);
		batchSignatureReq.getDocumentsToBeSigned().addAll(documents);

		try {
			// send the "batch-signature" request to server
			log.info("Sending request...");
			BatchSignatureRespTypeV2 batchSignatureResp = signatureEndpoint.batchSignatureV2(batchSignatureReq);
			CertillionStatus batchSignatureRespValue = CertillionStatus
					.valueOf(batchSignatureResp.getStatus().getStatusMessage());

			// check the "batch-signature" response
			if (batchSignatureRespValue != CertillionStatus.REQUEST_OK) {
				log.error("Error sending request, server returned {}", batchSignatureRespValue);
				System.exit(1);
			}

			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			statusReq.setTransactionId(batchSignatureResp.getTransactionId());

			// send the "get-status" request to server
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user
			// responds
			BatchSignatureTIDsRespType statusResp = null;
			CertillionStatus statusRespValue = null;
			do {
				log.info("Waiting signature from user...");
				statusResp = signatureEndpoint.batchSignatureTIDsStatus(statusReq);
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
				Thread.sleep(10000); // sleep for 10 seconds or the server will
										// mark you as flood
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

	private static List<BatchInfoType> uploadFiles(String[] paths) throws IOException {
		List<BatchInfoType> result = new ArrayList<BatchInfoType>();

		for (String path : paths) {

			// check if file exists
			File file = new File(path);
			URL fileUrl = file.toURI().toURL();
			if (!file.exists()) {
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
