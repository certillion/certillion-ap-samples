package br.com.esec.icpm.samples.ap.core.utils;

import br.com.esec.mss.ap.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Utilitarian methods to integrate with Certillion.
 */
public final class CertillionApUtils {

	private static final Logger log = LoggerFactory.getLogger(CertillionApUtils.class);

	// time to wait before checking the transaction status again (in milliseconds)
	// if you set to a lower value, Certillion server may mark your requests as flood
	private static final long WAIT_INTERVAL = 10000;

	// only static methods
	private CertillionApUtils() {
	}

	/**
	 * @param wsdlUrl     URL of the WSDL of the Certillion SOAP Web Service.
	 * @param serviceName Qualified name of the {@code wsdl:service} element inside the WSDL.
	 * @return A new endpoint instance for the given service.
	 * @throws MalformedURLException If the WSDL URL is malformed.
	 */
	public static SignaturePortType newEndpoint(String wsdlUrl, QName serviceName) throws MalformedURLException {
		Service signatureService = Service.create(new URL(wsdlUrl), serviceName);
		return signatureService.getPort(SignaturePortType.class);
	}

	/**
	 * Upload a file to Certillion server, so you can request it to be signed.
	 *
	 * @param fileInfo Information about the file to be uploaded, with {@code stream} property set.
	 *                 At the end, the {@code hash} property is set.
	 * @param restUrl  URL of the Certillion REST Web Service.
	 * @throws ICPMException If Certillion server rejected the upload.
	 * @throws IOException   If a network error occurred while uploading.
	 */
	public static void uploadDocument(FileInfo fileInfo, String restUrl) throws IOException, ICPMException {
		HttpURLConnection connection = null;

		try {
			// read info's
			String fileName = fileInfo.getName();
			InputStream fileStream = fileInfo.getStream();

			// mount the "upload-file" request
			log.info("Uploading file {} ...", fileName);
			URL resource = new URL(restUrl + "/uploadDocument");
			connection = (HttpURLConnection) resource.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/octet-stream");

			// send the "upload-file" request
			IOUtils.copy(fileStream, connection.getOutputStream());
			if (connection.getResponseCode() != HttpStatus.SC_OK) {
				throw new ICPMException("Certillion rejected the file upload", CertillionStatus.INTERNAL_ERROR.toStatusType());
			}

			// parse the "upload-file" response
			String hash = IOUtils.toString(connection.getInputStream());
			log.info("File {} successfully uploaded", fileName);

			// save info's
			fileInfo.setHash(hash);

		} finally {
			IOUtils.close(connection);
		}
	}

	/**
	 * Request the signature of previously uploaded files.
	 *
	 * @param user      The identifier of the target user.
	 * @param message   The message to be shown to user explaing the content of the request.
	 * @param fileInfos List of files to be signed, with the {@code name} and {@code hash} properties set.
	 * @param endpoint  Certillion SOAP Endpoint.
	 * @return The ID of the signature transaction within Certillion server.
	 * @throws ICPMException If Certillion server rejected the request.
	 */
	public static long signDocuments(String user, String message, final List<FileInfo> fileInfos, final SignaturePortType endpoint, MessagingModeType msgModeType) throws ICPMException {

		// read info's
		SignatureStandardType standard = getStandardFromExtension(fileInfos.get(0).getName());
		String contentType = getContentTypeFromExtension(fileInfos.get(0).getName());

		// mount the "batch-signature" request
		log.info("Sending request ...");
		BatchSignatureReqType batchSignatureReq = new BatchSignatureReqType();
		batchSignatureReq.setMessagingMode(msgModeType);
		batchSignatureReq.setDataToBeDisplayed(message);
		batchSignatureReq.setSignatureStandard(standard);
		batchSignatureReq.setTestMode(false);
//		batchSignatureComplexDocumentReqType.setSignaturePolicy(SignaturePolicyType.AD_RT);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(user);
		batchSignatureReq.setMobileUser(mobileUser);

		// set documents to be signed
		List<BatchInfoType> documents = batchSignatureReq.getDocumentsToBeSigned();
		for (FileInfo info : fileInfos) {
			BatchInfoType document = new BatchInfoType();
			document.setDocumentName(info.getName());
			document.setContentType(contentType);
			document.setHash(info.getHash());
			documents.add(document);
		}

		// send the "batch-signature" request
		final BatchSignatureComplexDocumentRespType batchSignatureResp = endpoint.batchSignature(batchSignatureReq);
		CertillionStatus batchSignatureStatus = CertillionStatus.valueOf(batchSignatureResp.getStatus().getStatusMessage());
		if (batchSignatureStatus != CertillionStatus.REQUEST_OK) {
			throw new ICPMException("Certillion rejected the signature request", batchSignatureResp.getStatus());
		}

		// return result
		long transactionId = batchSignatureResp.getTransactionId();
		log.info("Request sent, transaction ID is {}", transactionId);
		return transactionId;
	}

	/**
	 * Asynchronously wait for the signature transaction to be completed.
	 *
	 * @param transactionId ID of the transaction to wait.
	 * @param endpoint      Certillion SOAP Endpoint.
	 * @param executor      Service to schedule the status check.
	 * @return A future with the signature result of the files, with the {@code transactionId} and {@code signatureStatus} properties set.
	 * @throws ICPMException Thrown async when the user rejected the request.
	 * @throws IOException   Thrown async when a network error occurred.
	 */
	public static ListenableFuture<List<FileInfo>> awaitDocumentsSignature(final long transactionId, final SignaturePortType endpoint, ScheduledExecutorService executor) {
		final ListeningScheduledExecutorService superExecutor = MoreExecutors.listeningDecorator(executor);
		final SettableFuture<List<FileInfo>> futureResult = SettableFuture.create();

		// schedule a task to periodically check the transaction's status
		log.info("Request sent, transaction ID is {}", transactionId);
		final Future<?> scheduledTask = scheduleTask(superExecutor, new Runnable() {
			public void run() {
				try {

					// skip if cancelled
					if (futureResult.isDone()) {
						return;
					}

					// mount the "check-transaction" request
					SignatureStatusReqType checkTransactionReq = new SignatureStatusReqType();
					checkTransactionReq.setTransactionId(transactionId);

					// send the "check-transaction" request
					BatchSignatureTIDsRespType checkTransactionResp = endpoint.batchSignatureTIDsStatus(checkTransactionReq);
					CertillionStatus checkTransactionStatus = CertillionStatus.valueOf(checkTransactionResp.getStatus().getStatusMessage());
					if (checkTransactionStatus.isError()) {
						throw new ICPMException("User didn't signed the files", checkTransactionResp.getStatus());
					}

					// if still in progress, re-schedule this task
					if (checkTransactionStatus == CertillionStatus.TRANSACTION_IN_PROGRESS) {
						log.info("Transaction {} still in progress", transactionId);
						scheduleTask(superExecutor, this);
						return;
					}

					// parse the "check-transaction" response
					List<FileInfo> fileInfos = new ArrayList<FileInfo>();
					for (DocumentSignatureStatusInfoType document : checkTransactionResp.getDocumentSignatureStatus()) {
						long documentId = document.getTransactionId();
						String documentName = document.getDocumentName();
						CertillionStatus documentStatus = CertillionStatus.from(document.getStatus());

						// save info's
						FileInfo fileInfo = new FileInfo();
						fileInfo.setTransactionId(documentId);
						fileInfo.setName(documentName);
						fileInfo.setSignatureStatus(documentStatus);
						fileInfos.add(fileInfo);
					}

					// return async result
					log.info("Transaction {} finished", transactionId);
					futureResult.set(fileInfos);

				} catch (Exception e) {
					// pass the exception back via future
					futureResult.setException(e);
				}
			}
		});

		// return the ListenableFuture, so the client can listen/await it
		return futureResult;
	}

	// schedule a task to run after a predefined interval
	private static Future<?> scheduleTask(ListeningScheduledExecutorService executor, Runnable task) {
		return executor.schedule(task, WAIT_INTERVAL, TimeUnit.MILLISECONDS);
	}

	/**
	 * Download the detached signature of a file signed with Certillion.
	 *
	 * @param fileInfo Information about the signed file, with the {@code transactionId} property set.
	 *                 At the end, the signature is written to the {@code detachedSignature} property.
	 * @param endpoint Certillion SOAP Endpoint.
	 * @throws ICPMException If Certillion server rejected the download.
	 * @throws IOException   If a network error occurred or if could not write the signature to the output stream.
	 */
	public static void downloadDetachedSignature(FileInfo fileInfo, SignaturePortType endpoint) throws ICPMException, IOException {

		// read info's
		String fileName = fileInfo.getName();
		long transactionId = fileInfo.getTransactionId();

		// mount the "download-signature" request
		log.info("Downloading detached signature of file {}", fileName);
		SignatureStatusReqType request = new SignatureStatusReqType();
		request.setTransactionId(transactionId);

		// send the "download-signature" request
		SignatureStatusRespType response = endpoint.statusQuery(request);
		CertillionStatus responseStatus = CertillionStatus.from(response.getStatus());
		if (responseStatus.isError()) {
			throw new ICPMException("Certillion rejected the file download", response.getStatus());
		}

		// parse the "download-signature" response
		byte[] detachedSignature = response.getSignature();
		log.info("Detached signature of file {} successfully downloaded", fileName);

		// save info's
		fileInfo.getDetachedSignatureStream().write(detachedSignature);
	}

	/**
	 * Download the attached signature of a file signed with Certillion.
	 *
	 * @param fileInfo Information about the signed file, with the {@code transactionId} property set.
	 *                 At the end, the signature is written to the {@code attachedSignature} property.
	 * @param restUrl  URL of the Certillion REST Web Service.
	 * @throws ICPMException If Certillion server rejected the download.
	 * @throws IOException   If a network error occurred or if could not write the signature to {@link FileInfo#getDetachedSignatureStream()}.
	 */
	public static void downloadAttachedSignature(FileInfo fileInfo, String restUrl) throws ICPMException, IOException {
		HttpURLConnection connection = null;

		try {
			// read info's
			String fileName = fileInfo.getName();
			long transactionId = fileInfo.getTransactionId();

			// mount the "download-signature" request
			log.info("Downloading attached signature of file {}", fileName);
			URL resource = new URL(restUrl + "/document/signed/" + transactionId);
			connection = (HttpURLConnection) resource.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setRequestMethod("GET");

			// send the "download-signature" request
			if (connection.getResponseCode() != HttpStatus.SC_OK) {
				throw new ICPMException("Certillion rejected the file download", CertillionStatus.INTERNAL_ERROR.toStatusType());
			}

			// parse the "download-signature" response
			InputStream attachedSignature = connection.getInputStream();
			log.info("Attached signature of file {} successfully downloaded", fileName);

			// save info's
			IOUtils.copy(attachedSignature, fileInfo.getAttachedSignatureStream());

		} finally {
			IOUtils.close(connection);
		}
	}

	/**
	 * @param filePath Path to a file.
	 * @return The Signature-Standard to be used, based on the file extension.
	 */
	public static SignatureStandardType getStandardFromExtension(String filePath) {
		if (filePath.endsWith(".pdf")) {
			return SignatureStandardType.ADOBEPDF;
		} else if (filePath.endsWith(".xml")) {
			return SignatureStandardType.XADES;
		} else {
			return SignatureStandardType.CADES;
		}
	}

	/**
	 * @param filePath Path to a file.
	 * @return The Content-Type of the file, based on it's extension.
	 */
	public static String getContentTypeFromExtension(String filePath) {
		if (filePath.endsWith(".pdf")) {
			return "application/pdf";
		} else if (filePath.endsWith(".xml")) {
			return "application/xml";
		} else {
			return "application/octet-stream";
		}
	}

}
