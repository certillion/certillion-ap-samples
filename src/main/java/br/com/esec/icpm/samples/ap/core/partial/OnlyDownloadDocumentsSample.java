package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import br.com.esec.icpm.samples.ap.core.SignDocumentsSample;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * This example was extracted from {@link SignDocumentsSample} and perform only the third part, the download of the
 * attached signature.
 */
public class OnlyDownloadDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(OnlyDownloadDocumentsSample.class);
	private static final int N_THREADS = 20;
	private static final String SIGNED_DIR = "signeds";

	public static void main(String[] args) throws Exception {
		try {

			// validate args length
			if (args.length < 2) {
				System.out.println(MessageFormat.format("usage: {0} {1} <config_csv> \n",
						Constants.APP_NAME, Constants.COMMAND_ONLY_DOWNLOAD));
				System.exit(1);
			}

			// get args
			ConfigCsv config = new ConfigCsv(args[1]);
			config.read();

			// download signatures
			FileUtils.forceMkdir(new File(SIGNED_DIR));
			downloadFiles(config);

		} catch (Exception e) {
			log.error("Could not complete the example", e);
		}
	}

	private static void downloadFiles(List<FileInfo> fileInfos, ExecutorService executorService) throws InterruptedException {



		for (final FileInfo fileInfo : fileInfos) {
			executorService.submit(new Runnable() {
				public void run() {
					try {
						String fileName = FilenameUtils.getName(fileInfo.getPath());
						File outputFile = new File(SIGNED_DIR, fileName);

						// check if user signed this file
						if (fileInfo.getSignature() == null) {
							log.warn("Not saving signature of file {}", fileName);
							return;
						}

						// save signature
						URL url = new URL(Constants.DOWNLOAD_URL + fileInfo.getTransactionId());
						InputStream inputStream = url.openStream();
						FileOutputStream outputStream = new FileOutputStream(outputFile);
						IOUtils.copy(inputStream, outputStream);
						IOUtils.closeQuietly(inputStream);
						IOUtils.closeQuietly(outputStream);
						log.info("Signature saved in file {}", outputFile.getPath());

					} catch (Exception e) {
						log.error("Error downloading files", e);
						System.exit(1);
					}
				}
			});
		}
	}

}
