package br.com.esec.icpm.samples.ap.core.utils;

import br.com.esec.mss.ap.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
	 * Upload a file to Certillion server, so you can request it to be signed.
	 *
	 * @param fileInfo    Information about the file to be uploaded, with {@code name} and {@code stream} properties set.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @return The same FileInfo, but with the {@code hash} property set.
	 * @throws ICPMException If Certillion server rejected the upload.
	 * @throws IOException   If a network error occurred while uploading.
	 */
	public static FileInfo uploadDocument(FileInfo fileInfo, String resourceUrl) throws IOException, ICPMException {
		HttpURLConnection connection = null;

		try {
			// read info's
			String fileName = fileInfo.getName();
			InputStream fileStream = fileInfo.getStream();

			// mount the "upload-file" request
			log.info("Uploading file {} ...", fileName);
			URL resource = new URL(resourceUrl);
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
			return fileInfo;

		} finally {
			IOUtils.close(connection);
		}
	}

	/**
	 * Upload multiple files to Certillion server.
	 *
	 * @param fileInfos   List of files to be uploaded.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @param executor    Service managing the upload threads.
	 * @return A future with the upload result of the files.
	 * @see #uploadDocument(FileInfo, String)
	 */
	public static ListenableFuture<List<FileInfo>> uploadDocuments(List<FileInfo> fileInfos, final String resourceUrl, ExecutorService executor) {
		List<ListenableFuture<FileInfo>> tasks = new ArrayList<ListenableFuture<FileInfo>>();
		ListeningExecutorService superExecutor = MoreExecutors.listeningDecorator(executor);

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			ListenableFuture<FileInfo> task = superExecutor.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return uploadDocument(fileInfo, resourceUrl);
				}
			});
			tasks.add(task);
		}

		return Futures.allAsList(tasks);
	}

	/**
	 * Request the signature of previously uploaded files.
	 *
	 * @param user      The identifier of the target user.
	 * @param message   The message to be shown to user explaing the content of the request.
	 * @param fileInfos List of files to be signed, with the {@code name} and {@code hash} properties set.
	 * @param endpoint  Certillion SOAP Endpoint that manages signatures.
	 * @param executor  Service to schedule the status check.
	 * @return A future with the signature result of the files, with the {@code transactionId} and {@code signatureStatus} properties set.
	 * @throws ICPMException If Certillion server rejected the request. Also thrown async when the user rejected the request.
	 * @throws IOException   Thrown async when a network error occurred.
	 */
	public static ListenableFuture<List<FileInfo>> signDocuments(String user, String message, final List<FileInfo> fileInfos, final SignaturePortType endpoint, ScheduledExecutorService executor) throws ICPMException {
		final ListeningScheduledExecutorService superExecutor = MoreExecutors.listeningDecorator(executor);
		final SettableFuture<List<FileInfo>> futureResult = SettableFuture.create();

		// read info's
		SignatureStandardType standard = getStandardFromExtension(fileInfos.get(0).getName());
		String contentType = getContentTypeFromExtension(fileInfos.get(0).getName());

		// mount the "batch-signature" request
		log.info("Sending request ...");
		BatchSignatureComplexDocumentReqType batchSignatureReq = new BatchSignatureComplexDocumentReqType();
		batchSignatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		batchSignatureReq.setDataToBeDisplayed(message);
		batchSignatureReq.setSignatureStandard(standard);
		batchSignatureReq.setTestMode(false);
//		batchSignatureComplexDocumentReqType.setSignaturePolicy(SignaturePolicyType.AD_RT);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(user);
		batchSignatureReq.setMobileUser(mobileUser);

		// set documents to be signed
		List<HashDocumentInfoType> documents = batchSignatureReq.getDocumentsToBeSigned();
		for (FileInfo info : fileInfos) {
			HashDocumentInfoType document = new HashDocumentInfoType();
			document.setDocumentName(info.getName());
			document.setContentType(contentType);
			document.setHash(info.getHash());
			documents.add(document);
		}

		// send the "batch-signature" request
		final BatchSignatureComplexDocumentRespType batchSignatureResp = endpoint.batchSignatureComplexDocument(batchSignatureReq);
		CertillionStatus batchSignatureStatus = CertillionStatus.valueOf(batchSignatureResp.getStatus().getStatusMessage());
		if (batchSignatureStatus != CertillionStatus.REQUEST_OK) {
			throw new ICPMException("Certillion rejected the signature request", batchSignatureResp.getStatus());
		}

		// schedule a task to periodically check the transaction's status
		final long transactionId = batchSignatureResp.getTransactionId();
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
					if (checkTransactionStatus == CertillionStatus.TRANSACTION_IN_PROGRESS) {
						// re-schedule this task
						log.info("Transaction {} still in progress", transactionId);
						scheduleTask(superExecutor, this);
						return;
					} else if (checkTransactionStatus != CertillionStatus.REQUEST_OK) {
						throw new ICPMException("User didn't signed the files", checkTransactionResp.getStatus());
					}

					// parse the "check-transaction" response
					log.info("Transaction {} completed successfully", transactionId);
					for (DocumentSignatureStatusInfoType document : checkTransactionResp.getDocumentSignatureStatus()) {
						long documentId = document.getTransactionId();
						String documentName = document.getDocumentName();
						CertillionStatus documentStatus = CertillionStatus.from(document.getStatus());

						// save info's
						FileInfo fileInfo = findFileByName(documentName, fileInfos);
						fileInfo.setTransactionId(documentId);
						fileInfo.setSignatureStatus(documentStatus);
					}
					futureResult.set(fileInfos);

				} catch (Exception e) {
					// pass the exception back via future
					futureResult.setException(e);
				}
			}
		});
		return futureResult;
	}

	// schedule a task to run after a predefined interval
	private static Future<?> scheduleTask(ListeningScheduledExecutorService executor, Runnable task) {
		return executor.schedule(task, WAIT_INTERVAL, TimeUnit.MILLISECONDS);
	}

	// find a FileInfo comparing by name (or returns null)
	private static FileInfo findFileByName(String name, List<FileInfo> fileInfos) {
		for (FileInfo info : fileInfos) {
			if (info.getName().equals(name)) {
				return info;
			}
		}
		return null;
	}

	/**
	 * Download the detached signature of a file signed with Certillion.
	 *
	 * @param fileInfo Information about the signed file, with the {@code transactionId property} set.
	 * @param endpoint Certillion SOAP Endpoint that manages signatures.
	 * @return The same FileInfo, but with the {@code detachedSignature} property set.
	 * @throws ICPMException If Certillion server rejected the download.
	 * @throws IOException   If a network error occurred or if could not write the signature to {@link FileInfo#getDetachedSignatureStream()}.
	 */
	public static FileInfo downloadDetachedSignature(FileInfo fileInfo, SignaturePortType endpoint) throws ICPMException, IOException {

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
		return fileInfo;
	}

	/**
	 * Download detached signature of multiple files from Certillion server.
	 *
	 * @param fileInfos List of files whose signature will be downloaded.
	 * @param endpoint  Certillion SOAP Endpoint that manages signatures.
	 * @param executor  Service managing the upload threads.
	 * @return Futures representing the download of the signatures.
	 * @see #downloadDetachedSignature(FileInfo, SignaturePortType)
	 */
	public static ListenableFuture<List<FileInfo>> downloadDetachedSignatures(List<FileInfo> fileInfos, final SignaturePortType endpoint, ExecutorService executor) {
		List<ListenableFuture<FileInfo>> tasks = new ArrayList<ListenableFuture<FileInfo>>();
		ListeningExecutorService superExecutor = MoreExecutors.listeningDecorator(executor);

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			ListenableFuture<FileInfo> task = superExecutor.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return downloadDetachedSignature(fileInfo, endpoint);
				}
			});
			tasks.add(task);
		}

		return Futures.allAsList(tasks);
	}

	/**
	 * Download the attached signature of a file signed with Certillion.
	 *
	 * @param fileInfo    Information about the signed file, including the transaction ID.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @return The same FileInfo, but with the {@code attachedSignature} property set.
	 * @throws ICPMException If Certillion server rejected the download.
	 * @throws IOException   If a network error occurred or if could not write the signature to {@link FileInfo#getDetachedSignatureStream()}.
	 */
	public static FileInfo downloadAttachedSignature(FileInfo fileInfo, String resourceUrl) throws ICPMException, IOException {
		HttpURLConnection connection = null;

		try {
			// read info's
			String fileName = fileInfo.getName();
			long transactionId = fileInfo.getTransactionId();

			// mount the "download-signature" request
			log.info("Downloading attached signature of file {}", fileName);
			URL resource = new URL(resourceUrl + "/signed/" + transactionId);
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
			return fileInfo;

		} finally {
			IOUtils.close(connection);
		}
	}

	/**
	 * Download attached signature of multiple files from Certillion server.
	 *
	 * @param fileInfos       List of files whose signature will be downloaded.
	 * @param resourceUrl     URL of the Certillion REST resource that manages files.
	 * @param executor Service managing the upload threads.
	 * @return Futures representing the download of the signatures.
	 * @see #downloadAttachedSignature(FileInfo, String)
	 */
	public static ListenableFuture<List<FileInfo>> downloadAttachedSignatures(List<FileInfo> fileInfos, final String resourceUrl, ExecutorService executor) {
		List<ListenableFuture<FileInfo>> tasks = new ArrayList<ListenableFuture<FileInfo>>();
		ListeningExecutorService superExecutor = MoreExecutors.listeningDecorator(executor);

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			ListenableFuture<FileInfo> task = superExecutor.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return downloadAttachedSignature(fileInfo, resourceUrl);
				}
			});
			tasks.add(task);
		}

		return Futures.allAsList(tasks);
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
