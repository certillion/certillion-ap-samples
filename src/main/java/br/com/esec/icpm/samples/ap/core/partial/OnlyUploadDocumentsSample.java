package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionApUtils;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This example was extracted from {@code SignDocumentsSample} and perform only the first part, the file upload.
 */
public class OnlyUploadDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(OnlyUploadDocumentsSample.class);
	public static final int N_THREADS = 20;

	public static void main(String[] args) {
		try {

			// validate args length
			if (args.length < 2) {
				System.out.println(MessageFormat.format("usage: {0} {1} <config_csv> \n",
						Constants.APP_NAME, Constants.COMMAND_ONLY_UPLOAD));
				System.exit(1);
			}

			// get args
			ConfigCsv config = new ConfigCsv(args[1]);
			config.read();

			// upload files
			final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
			for (final FileInfo fileInfo : config.getFileInfos()) {
				executorService.submit(new Callable<Void>() {
					public Void call() throws Exception {
						CertillionApUtils.uploadDocument(fileInfo, Constants.REST_URL);
						return null;
					}
				});
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

}
