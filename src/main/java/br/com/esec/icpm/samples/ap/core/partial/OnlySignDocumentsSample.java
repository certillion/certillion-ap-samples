package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.libs.signature.helper.MimeTypeConstants;
import br.com.esec.icpm.mss.ws.*;
import br.com.esec.icpm.samples.ap.core.SignDocumentsSample;
import br.com.esec.icpm.samples.ap.core.WebServiceInfo;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This example was extracted from {@link SignDocumentsSample} and perform only the second part, the signature request.
 */
public class OnlySignDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(OnlySignDocumentsSample.class);
	private static final int N_THREADS = 20;

	private static SignaturePortType signatureEndpoint = null;

	public static void main(String[] args) throws Exception {
		try {

			// validate args length
			if (args.length < 3) {
				System.out.println("usage: certillion-ap-samples only-sign-documents [config_csv] [identifier] \n");
				System.exit(1);
			}

			// get args
			ConfigCsv config = new ConfigCsv(args[1]);
			String uniqueIdentifier = args[2];
			config.read();

			// sign files and save detached signatures
			BatchSignatureTIDsRespType statusResp = signFiles(uniqueIdentifier, config);
			downloadSignatures(statusResp, config);
			config.write();

		} catch (Exception e) {
			log.error("Could not complete the example", e);
			System.exit(1);
		}
	}

	private static BatchSignatureTIDsRespType signFiles(String uniqueIdentifier, ConfigCsv config) throws MalformedURLException, ICPMException, InterruptedException {
		String dataToBeDisplayed = "certillion-ap-samples at " + new Date().toString();
		SignatureStandardType standard = getStandardFromExtension(config.getFileInfos().get(0).getPath());

		// connnect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(WebServiceInfo.getApServiceUrl());
		Service signatureService = Service.create(serviceUrl, SignaturePortType.QNAME);
		signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// retrieve info about the documents to be signed
		List<HashDocumentInfoType> documents = new ArrayList<HashDocumentInfoType>();
		for (ConfigCsv.FileInfo info : config.getFileInfos()) {
			String path = info.getPath();
			HashDocumentInfoType document = new HashDocumentInfoType();
			document.setDocumentName(FilenameUtils.getName(path));
			document.setContentType(MimeTypeConstants.getMimeType(FilenameUtils.getExtension(path)));
			document.setHash(info.getHash());
			documents.add(document);
		}

		// mount the "batch-signature" request
		BatchSignatureComplexDocumentReqType batchSignatureReq = new BatchSignatureComplexDocumentReqType();
		batchSignatureReq.setMobileUser(mobileUser);
		batchSignatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		batchSignatureReq.setDataToBeDisplayed(dataToBeDisplayed);
		batchSignatureReq.setSignatureStandard(standard);
		batchSignatureReq.setDocumentsToBeSigned(documents);
		batchSignatureReq.setTestMode(false);
//		batchSignatureComplexDocumentReqType.setSignaturePolicy(SignaturePolicyType.AD_RT);

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
			Thread.sleep(1000);
		} while (statusResp.getStatus().getStatusCode() == Status.TRANSACTION_IN_PROGRESS.getCode());

		// check the "get-status" response
		if (statusRespValue != Status.REQUEST_OK) {
			log.error("Error receiving the response, the status is {}", statusRespValue);
			System.exit(1);
		}

		return statusResp;
	}

	private static void downloadSignatures(BatchSignatureTIDsRespType statusResp, final ConfigCsv config) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

		for (final DocumentSignatureStatusInfoType documentInfo : statusResp.getDocumentSignatureStatus()) {
			executorService.submit(new Runnable() {
				public void run() {
					try {
						String documentName = documentInfo.getDocumentName();
						long transactionId = documentInfo.getTransactionId();
						Status status = Status.valueOf(documentInfo.getStatus().getStatusMessage());
						log.info("Retrieving signature of document {} ...", documentName);

						// check if the user rejected this document
						if (status != Status.SIGNATURE_VALID) {
							log.warn("Not saving signature of document {}, status is {}", documentName, status);
							return;
						}

						// mount the "download-signature" request
						SignatureStatusReqType request = new SignatureStatusReqType();
						request.setTransactionId(transactionId);

						// send the "download-signature" request
						SignatureStatusRespType response = signatureEndpoint.statusQuery(request);
						byte[] signedBytes = IOUtils.toByteArray(response.getSignature().getInputStream());

						// save the signature
						ConfigCsv.FileInfo info = config.findByName(documentName);
						String signature = Base64.encodeBase64String(signedBytes);
						info.setId(Long.toString(transactionId));
						info.setSignature(signature);
						log.info("The signature of {} is {}", documentName, signature);

					} catch (Exception e) {
						log.error("Error downloading signatures", e);
						System.exit(1);
					}
				}
			});
		}

		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.HOURS);
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

}
