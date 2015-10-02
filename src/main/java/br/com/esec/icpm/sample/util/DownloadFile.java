package br.com.esec.icpm.sample.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.esec.icpm.sample.ap.core.WsSignerAddress;

public class DownloadFile {

	private static final int BUFFER_SIZE = 4096;
	private static final String PDF_URL = WsSignerAddress.get() + "/mss/restAp/DownloadSigned/Pdf/";

	public static void signedPdf(long id, String saveDir) throws IOException {
		File parentDir = new File(saveDir);
		parentDir.mkdirs();

		System.out.println("\t\tDownloading document...");
		String fileURL = PDF_URL + id;
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			File saveFile = new File(parentDir, fileName + ".pdf");

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFile);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("\t\tFile downloaded and saved at: " + saveFile.getAbsolutePath());
		} else {
			System.err.println("\t\tNo file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}
}
