package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.SignDocumentsSample;
import br.com.esec.icpm.samples.ap.core.utils.CertillionStatus;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import br.com.esec.icpm.samples.ap.core.utils.CertillionFileUtils;
import br.com.esec.mss.ap.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
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
				System.out.println(MessageFormat.format("usage: {0} {1} <config_csv> <identifier> [<skip_download>] \n",
						Constants.APP_NAME, Constants.COMMAND_ONLY_SIGN));
				System.exit(1);
			}

			// get args
			ConfigCsv config = new ConfigCsv(args[1]);
			String uniqueIdentifier = args[2];
			boolean skipDownload = Boolean.parseBoolean(args.length > 3 ? args[3] : "false");

			// read config
			config.read();

			// create thread pool
			ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

			// sign files
			BatchSignatureTIDsRespType statusResp = signFiles(config.getFileInfos(), uniqueIdentifier);

			// download detached signature
			if (!skipDownload) {
				CertillionFileUtils.downloadDetachedSignatures(config.getFileInfos(), signatureEndpoint, executorService);
			}

			// shutdown thread pool
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);

			// save config
			config.write();

		} catch (Exception e) {
			log.error("Could not complete the example", e);
			System.exit(1);
		}
	}

	private static BatchSignatureTIDsRespType signFiles(String uniqueIdentifier) throws MalformedURLException, ICPMException, InterruptedException {
		String dataToBeDisplayed = "certillion-ap-samples at " + new Date().toString();
		SignatureStandardType standard = getStandardFromExtension(config.getFileInfos().get(0).getPath());

		// connnect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(Constants.WSDL_URL);
		Service signatureService = Service.create(serviceUrl, SignaturePortType.QNAME);
		signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// retrieve info about the documents to be signed
		List<HashDocumentInfoType> documents = new ArrayList<HashDocumentInfoType>();
		for (FileInfo info : config.getFileInfos()) {
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
		CertillionStatus batchSignatureRespValue = CertillionStatus.valueOf(batchSignatureResp.getStatus().getStatusMessage());
		long transactionId = batchSignatureResp.getTransactionId();

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
			statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
			Thread.sleep(1000);
		} while (statusResp.getStatus().getStatusCode() == CertillionStatus.TRANSACTION_IN_PROGRESS.getCode());

		// check the "get-status" response
		if (statusRespValue != CertillionStatus.REQUEST_OK) {
			log.error("Error receiving the response, the status is {}", statusRespValue);
			System.exit(1);
		}

		// save transaction-id of each document
		for (DocumentSignatureStatusInfoType documentInfo : statusResp.getDocumentSignatureStatus()) {
			FileInfo configInfo = config.findByName(documentInfo.getDocumentName());
			configInfo.setTransactionId(Long.toString(documentInfo.getTransactionId()));
		}

		log.info("Signature completed.");
		return statusResp;
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
