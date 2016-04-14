package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionApUtils;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import br.com.esec.mss.ap.SignaturePortType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This example was extracted from {@code SignDocumentsSample} and perform only the third part A, the download of the
 * detached signature.
 */
public class OnlyDownloadDetachedSample {

	private static final Logger log = LoggerFactory.getLogger(OnlyDownloadDetachedSample.class);
	private static final int N_THREADS = 20;

	public static void main(String[] args) throws Exception {
		try {

			// validate args length
			if (args.length < 2) {
				System.out.println(MessageFormat.format("usage: {0} {1} <config_csv> \n",
						Constants.APP_NAME, Constants.COMMAND_ONLY_DOWNLOAD_DETACHED));
				System.exit(1);
			}

			// get args
			final ConfigCsv config = new ConfigCsv(args[1]);
			config.read();

			// create output stream
			FileUtils.forceMkdir(new File("signeds"));
			for (FileInfo fileInfo : config.getFileInfos()) {
				fileInfo.setDetachedSignatureStream(new FileOutputStream("signeds/" + fileInfo.getTransactionId() + ".p7s"));
			}

			// download signature
			Service signatureService = Service.create(new URL(Constants.WSDL_URL), Constants.SERVICE_QNAME);
			final SignaturePortType signatureEndpoint = signatureService.getPort(SignaturePortType.class);
			final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
			for (final FileInfo fileInfo : config.getFileInfos()) {
				executorService.submit(new Callable<Void>() {
					public Void call() throws Exception {
						CertillionApUtils.downloadDetachedSignature(fileInfo, signatureEndpoint);
						return null;
					}
				});
			}

			// shutdown thread pool
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);

			// save config
			for (FileInfo fileInfo : config.getFileInfos()) {
				fileInfo.getDetachedSignatureStream().close();
			}
			config.write();

		} catch (Exception e) {
			log.error("Could not complete the example", e);
			System.exit(1);
		}
	}

}
