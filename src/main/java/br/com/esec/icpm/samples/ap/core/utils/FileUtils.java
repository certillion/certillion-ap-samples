package br.com.esec.icpm.samples.ap.core.utils;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.mss.ap.*;
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
 * Utilitary methods for handling files with Certillion.
 */
public class FileUtils {

	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * Upload a file to Certillion server, so you can request it to be signed.
	 *
	 * @param fileInfo Informations about the file to be uploaded.
	 * @return The same FileInfo, but with the {@code hash} property set.
	 * @throws IOException If a network error occurred while uploading.
	 * @throws ICPMException If server rejected the upload.
     */
	public static FileInfo uploadFile(FileInfo fileInfo) throws IOException, ICPMException {

		// read info's
		String fileName = FilenameUtils.getName(fileInfo.getPath());
		InputStream fileStream = fileInfo.getFileStream();

		// mount the "upload-file" request
		log.info("Uploading file {} ...", fileName);
		URL restUrl = new URL(Constants.UPLOAD_URL);
		HttpURLConnection connection = (HttpURLConnection) restUrl.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/octet-stream");

		// send the "upload-file" request
		IOUtils.copy(fileStream, connection.getOutputStream());
		if (connection.getResponseCode() != HttpStatus.SC_OK) {
			throw new ICPMException("Certillion rejected the file upload", Status.INTERNAL_ERROR.toStatusType());
		}

		// parse the "upload-file" response
		String hash = IOUtils.toString(connection.getInputStream());
		log.info("File {} uploaded, hash is {}", fileName, hash);

		// save info's
		fileInfo.setHash(hash);
		return fileInfo;
	}

	/**
	 * 
	 *
	 * @param fileInfo
	 * @param signatureEndpoint
	 * @return
	 * @throws ICPMException
     */
	public static FileInfo downloadDetachedSignature(FileInfo fileInfo, SignaturePortType signatureEndpoint) throws ICPMException {

		// read info's
		String fileName = FilenameUtils.getName(fileInfo.getPath());
		long transactionId = fileInfo.getTransactionId();

		// mount the "download-signature" request
		SignatureStatusReqType request = new SignatureStatusReqType();
		request.setTransactionId(transactionId);

		// send the "download-signature" request
		SignatureStatusRespType response = signatureEndpoint.statusQuery(request);
		Status responseStatus = Status.from(response.getStatus());
		if (responseStatus.isError()) {
			throw new ICPMException("Certillion rejected the file download", response.getStatus());
		}

		// parse the "download-signature" response
		byte[] detachedSignature = response.getSignature();
		log.info("Downloaded signature of document {}", fileName);

		// save info's
		fileInfo.setDetachedSignature(detachedSignature);
		return fileInfo;
	}

	public static FileInfo downloadAttachedSignature(FileInfo fileInfo) throws IOException {
		// read info's
		String fileName = FilenameUtils.getName(fileInfo.getPath());
		long transactionId = fileInfo.getTransactionId();

		// mount the "download-signature" request
		URL restUrl = new URL(Constants.DOWNLOAD_URL + transactionId);
		HttpURLConnection connection = (HttpURLConnection) restUrl.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(false);
		connection.setRequestMethod("GET");

		// send the "download-signature" request
		if (connection.getResponseCode() != HttpStatus.SC_OK) {
			throw new IOException("Certillion rejected the file download");
		}

		// parse the "download-signature" response
		InputStream attachedSignature = connection.getInputStream();
		log.info("Downloaded signature of document {}", fileName);

		// save info's
		fileInfo.setAttachedSignature(attachedSignature);
		return fileInfo;
	}

	/**
	 * Upload files to Certillion server.
	 *
	 * @param fileInfos List of files to be uploaded.
	 * @param executorService Service managing the upload threads.
	 */
	public static List<Future<FileInfo>> uploadFiles(List<FileInfo> fileInfos, ExecutorService executorService) {
		List<Future<FileInfo>> futures = new ArrayList<Future<FileInfo>>();

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			Future<FileInfo> future = executorService.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return uploadFile(fileInfo);
				}
			});
			futures.add(future);
		}

		return futures;
	}

	private static List<Future<FileInfo>> downloadDetachedSignatures(List<FileInfo> fileInfos, final SignaturePortType signatureEndpoint, ExecutorService executorService) throws InterruptedException {
		List<Future<FileInfo>> futures = new ArrayList<Future<FileInfo>>();

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			Future<FileInfo> future = executorService.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return downloadDetachedSignature(fileInfo, signatureEndpoint);
				}
			});
			futures.add(future);
		}

		return futures;
	}

	private static void downloadAttachedSignatures(List<FileInfo> fileInfos, ExecutorService executorService) throws InterruptedException {
		List<Future<FileInfo>> futures = new ArrayList<Future<FileInfo>>();

		// submit one task for each file
		for (final FileInfo fileInfo : fileInfos) {
			Future<FileInfo> future = executorService.submit(new Callable<FileInfo>() {
				public FileInfo call() throws Exception {
					return downloadAttachedSignature(fileInfo);


				}
			});
		}
	}

}
