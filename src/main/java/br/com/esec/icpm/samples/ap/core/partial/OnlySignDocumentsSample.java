package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionApUtils;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import br.com.esec.mss.ap.MessagingModeType;
import br.com.esec.mss.ap.SignaturePortType;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This example was extracted from {@code SignDocumentsSample} and perform only the second part, the signature request.
 */
public class OnlySignDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(OnlySignDocumentsSample.class);

	public static void main(String[] args) throws Exception {
		try {

			// validate args length
			if (args.length < 2) {
				System.out.println(MessageFormat.format("usage: {0} {1} <config_csv> <identifier> \n",
						Constants.APP_NAME, Constants.COMMAND_ONLY_SIGN));
				System.exit(1);
			}

			// get args
			ConfigCsv config = new ConfigCsv(args[1]);
			String uniqueIdentifier = args[2];
			config.read();

			// sign files
			Service signatureService = Service.create(new URL(Constants.WSDL_URL), Constants.SERVICE_QNAME);
			SignaturePortType signatureEndpoint = signatureService.getPort(SignaturePortType.class);
			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
			long transactionId = CertillionApUtils.signDocuments(uniqueIdentifier, "Certillion Test", config.getFileInfos(), signatureEndpoint, MessagingModeType.ASYNCH_CLIENT_SERVER);
			ListenableFuture<List<FileInfo>> future = CertillionApUtils.awaitDocumentsSignature(transactionId, signatureEndpoint, executorService);

			// shutdown thread pool
			FileInfo.mergeByName(future.get(), config.getFileInfos());
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
