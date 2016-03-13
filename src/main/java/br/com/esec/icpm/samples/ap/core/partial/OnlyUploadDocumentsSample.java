package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.core.SignDocumentsSample;
import br.com.esec.icpm.samples.ap.core.WebServiceInfo;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This example was extracted from {@link SignDocumentsSample} and perform only the first part, the file upload.
 */
public class OnlyUploadDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(OnlyUploadDocumentsSample.class);
	public static final int N_THREADS = 20;

	public static void main(String[] args) {
		try {

			// validate args length
			if (args.length < 2) {
				System.out.println("usage: certillion-ap-samples only-upload-documents [config_csv] \n");
				System.exit(1);
			}

			// get args
			ConfigCsv config = new ConfigCsv(args[1]);
			config.read();

			// upload files and save their info's
			uploadFiles(config);
			config.write();

		} catch (Exception e) {
			log.error("Could not complete the example", e);
			System.exit(1);
		}
	}

	private static void uploadFiles(ConfigCsv config) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

		for (final ConfigCsv.FileInfo info : config.getFileInfos()) {
			executorService.submit(new Runnable() {
				public void run() {
					try {
						String path = info.getPath();

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

						// save the hash of the uploaded document
						info.setHash(response);

					} catch (Exception e) {
						log.error("Error uploading files", e);
						System.exit(1);
					}
				}
			});
		}

		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.HOURS);
	}

}
