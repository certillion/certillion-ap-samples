package br.com.esec.icpm.samples.ap.core.utils;

import br.com.esec.mss.ap.ICPMException;
import br.com.esec.mss.ap.SignaturePortType;
import br.com.esec.mss.ap.SignatureStatusReqType;
import br.com.esec.mss.ap.SignatureStatusRespType;
import org.apache.commons.io.FilenameUtils;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Utilitarian methods for handling files with Certillion.
 */
public class CertillionFileUtils {

	private static final Logger log = LoggerFactory.getLogger(CertillionFileUtils.class);

	/**
	 * Upload a file to Certillion server, so you can request it to be signed.
	 *
	 * @param fileInfo Information about the file to be uploaded.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @return The same FileInfo, but with the {@code hash} property set.
	 * @throws IOException If a network error occurred while uploading.
	 * @throws ICPMException If Certillion server rejected the upload.
     */
	public static FileInfo uploadFile(FileInfo fileInfo, String resourceUrl) throws IOException, ICPMException {

		// read info's
		String fileName = FilenameUtils.getName(fileInfo.getPath());
		InputStream fileStream = fileInfo.getFileStream();

		// mount the "upload-file" request
		log.info("Uploading file {} ...", fileName);
		URL resource = new URL(resourceUrl);
		HttpURLConnection connection = (HttpURLConnection) resource.openConnection();
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
	}

	/**
	 * Download the detached signature of a file signed with Certillion.
	 *
	 * @param fileInfo Information about the signed file, including the transaction ID.
	 * @param endpoint Certillion SOAP Endpoint that manages signatures.
	 * @return The same FileInfo, but with the {@code detachedSignature} property set.
	 * @throws ICPMException If Certillion server returned an error.
     */
	public static FileInfo downloadDetachedSignature(FileInfo fileInfo, SignaturePortType endpoint) throws ICPMException {

		// read info's
		String fileName = FilenameUtils.getName(fileInfo.getPath());
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
		fileInfo.setDetachedSignature(detachedSignature);
		return fileInfo;
	}

	/**
	 * Download the attached signature of a file signed with Certillion.
	 *
	 * @param fileInfo Information about the signed file, including the transaction ID.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @return The same FileInfo, but with the {@code attachedSignature} property set.
	 * @throws IOException If a network error occurred while downloading.
	 * @throws ICPMException If Certillion server rejected the download.
     */
	public static FileInfo downloadAttachedSignature(FileInfo fileInfo, String resourceUrl) throws IOException, ICPMException {

		// read info's
		String fileName = FilenameUtils.getName(fileInfo.getPath());
		long transactionId = fileInfo.getTransactionId();

		// mount the "download-signature" request
		log.info("Downloading attached signature of file {}", fileName);
		URL resource = new URL(resourceUrl + "/signed/" + transactionId);
		HttpURLConnection connection = (HttpURLConnection) resource.openConnection();
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
		fileInfo.setAttachedSignature(attachedSignature);
		return fileInfo;
	}

	/**
	 * Upload multiple files to Certillion server.
	 *
	 * @param fileInfos List of files to be uploaded.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @param executorService Service managing the upload threads.
	 * @return List of Futures representing the uploading of each file.
	 * @see #uploadFile(FileInfo, String)
	 */
	public static List<Future<FileInfo>> uploadFiles(List<FileInfo> fileInfos, final String resourceUrl, ExecutorService executorService) {
		List<Future<FileInfo>> futures = new ArrayList<Future<FileInfo>>();

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			Future<FileInfo> future = executorService.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return uploadFile(fileInfo, resourceUrl);
				}
			});
			futures.add(future);
		}

		return futures;
	}

	/**
	 * Download detached signature of multiple files from Certillion server.
	 *
	 * @param fileInfos List of files whose signature will be downloaded.
	 * @param endpoint Certillion SOAP Endpoint that manages signatures.
	 * @param executorService Service managing the upload threads.
	 * @return List of Futures representing the download of each signature.
	 * @see #downloadDetachedSignature(FileInfo, SignaturePortType)
     */
	public static List<Future<FileInfo>> downloadDetachedSignatures(List<FileInfo> fileInfos, final SignaturePortType endpoint, ExecutorService executorService) {
		List<Future<FileInfo>> futures = new ArrayList<Future<FileInfo>>();

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			Future<FileInfo> future = executorService.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return downloadDetachedSignature(fileInfo, endpoint);
				}
			});
			futures.add(future);
		}

		return futures;
	}

	/**
	 * Download attached signature of multiple files from Certillion server.
	 *
	 * @param fileInfos List of files whose signature will be downloaded.
	 * @param resourceUrl URL of the Certillion REST resource that manages files.
	 * @param executorService Service managing the upload threads.
	 * @return List of Futures representing the download of each signature.
	 * @see #downloadAttachedSignature(FileInfo, String)
     */
	public static List<Future<FileInfo>> downloadAttachedSignatures(List<FileInfo> fileInfos, final String resourceUrl, ExecutorService executorService) {
		List<Future<FileInfo>> futures = new ArrayList<Future<FileInfo>>();

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			Future<FileInfo> future = executorService.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return downloadAttachedSignature(fileInfo, resourceUrl);
				}
			});
			futures.add(future);
		}

		return futures;
	}

}
