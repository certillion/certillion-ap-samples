package br.com.esec.icpm.samples.ap.core;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionApUtils;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import br.com.esec.icpm.samples.ap.core.utils.TryFunction;
import br.com.esec.mss.ap.MessagingModeType;
import br.com.esec.mss.ap.SignaturePortType;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This example shows how to request the signature of a list of documents.
 *
 * It uses the "polling" method to check the signature status, but with the help of the Guava library to make it
 * asynchronous and non-blocking. This way, it can be used in a Java SE or Java EE environment. The only difference is
 * that in a Java EE environment you don't create and don't shutdown a thread pool, but use the ExecutorService provided
 * by the container instead.
 */
public class SignDocumentsSample {

	private static final Logger log = LoggerFactory.getLogger(SignDocumentsSample.class);
	private static final String SIGNED_DIR = "signeds";

	public static void main(String[] args) throws Exception {

		// validate args length
		if (args.length < 4) {
			System.out.println(MessageFormat.format(
					"usage: {0} {1} <user> <message> <files...> \n" +
					"\n" +
					"\t user: email/cpf of the target user \n" +
					"\t message: text to be displayed \n" +
					"\t files: path for one or more files to be signed \n",
					Constants.APP_NAME, Constants.COMMAND_SIGN_DOCS
			));
			System.exit(1);
		}

		// get args
		String user = args[1];
		String message = args[2];
		String[] filePaths = Arrays.copyOfRange(args, 3, args.length);
		validateArgs(user, message, filePaths);

		// open an input stream to read each file
		List<FileInfo> filesToSign = new ArrayList<FileInfo>();
		for (String path : filePaths) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setName(FilenameUtils.getName(path));
			fileInfo.setStream(new FileInputStream(path));
			filesToSign.add(fileInfo);
		}

		/*
		Upload files to Certillion server.

		The upload is made via REST for better performance.

		The server returns the hash of the file, which is used as the ID of this file in the server.
		So, you need to upload each file only once and can request it to be signed many times.
		 */
		for (FileInfo toBeUploaded : filesToSign) {
			CertillionApUtils.uploadDocument(toBeUploaded, Constants.REST_URL);
		}

		/*
		Create the endpoint client.

		In a real application, you can hold this instance and reuse it for all requests.
		 */
		SignaturePortType endpoint = CertillionApUtils.newEndpoint(Constants.WSDL_URL, Constants.SERVICE_QNAME);

		/*
		Request the signature of the documents.

		The server returns the ID of the transaction, which can be used to check it's status later.
		 */
		long transactionId = CertillionApUtils.signDocuments(user, message, filesToSign, endpoint, MessagingModeType.ASYNCH_CLIENT_SERVER);

		/*
		Get a service to schedule the status checking.

		This is useful because you don't need to block your thread using sleep() or similar methods.

		In a real application (running in an application server) you should not create new threads,
		instead, you use the thread pool managed by the container.

		If you are using Java EE 7, inject the service like this:
			@Resource private ManagedScheduledExecutorService executorService;
		If you are using Java EE 6, replace it with TimerService or create your own executor like this:
			http://stackoverflow.com/a/13945536/1188441
		If you are porting to other programming language, use an equivalent scheduling mechanism.
		 */
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		/*
		Wait for the signature to be completed.

		This method will schedule a task to periodically check the status of the transaction. This mechanism is efficient
		only if you don't have too many signature requests running in parallel. If your server's load is of hundreds of
		signatures per minute, you should migrate to the notification mechanism (see the manual)

		The returned object is a Future that represents the transaction in progress. You can wait it to be completed in
		a synchronous way, calling the get() method, or in an asynchronous way, calling the addListener() method or one
		of the helper methods of Guava.

		See:
		http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html
		http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/util/concurrent/ListenableFuture.html
		http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/util/concurrent/Futures.html
		 */
		ListenableFuture<List<FileInfo>> signatureFuture = CertillionApUtils.awaitDocumentsSignature(transactionId, endpoint, executorService);
		ListenableFuture<Void> saveFilesFuture = Futures.transform(signatureFuture, new TryFunction<List<FileInfo>, Void>() {
			public Void tryApply(List<FileInfo> signedFiles) throws Exception {

				// download the attached signature
				FileUtils.forceMkdir(new File(SIGNED_DIR));
				for (FileInfo signedFile : signedFiles) {

					// skip if user rejected
					if (signedFile.getSignatureStatus().isError()) {
						log.warn("File {} not signed, status is {}", signedFile.getName(), signedFile.getSignatureStatus());
						continue;
					}

					// save the signed file
					FileOutputStream outputStream = new FileOutputStream(new File(SIGNED_DIR, signedFile.getName()));
					signedFile.setAttachedSignatureStream(outputStream);
					CertillionApUtils.downloadAttachedSignature(signedFile, Constants.REST_URL);
					outputStream.close();
				}

				return null;

			}
		});

		// shutdown the thread pool
		saveFilesFuture.addListener(new Runnable() {
			public void run() {
				executorService.shutdown();
			}
		}, MoreExecutors.directExecutor());
	}

	private static void validateArgs(String uniqueIdentifier, String dataToBeDisplayed, String[] filesPath) {
		String extension = filesPath[0].substring(filesPath[0].lastIndexOf('.'));
		for (int i = 0; i < filesPath.length; i++) {
			if (!filesPath[i].endsWith(extension)) {
				System.out.println("All files must have the same extension");
				System.exit(1);
			}
		}
	}

}
