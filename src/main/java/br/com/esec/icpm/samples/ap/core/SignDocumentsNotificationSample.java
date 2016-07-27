package br.com.esec.icpm.samples.ap.core;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionApUtils;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import br.com.esec.mss.ap.MessagingModeType;
import br.com.esec.mss.ap.SignaturePortType;
import br.com.esec.ws.APNotificationImpl;

/**
 * This example shows how to request the signature of a list of documents with server-server notification.
 *
 * It uses a endpoint that will be notified when the transaction has been signed.
 */
public class SignDocumentsNotificationSample {
	private static final Logger log = LoggerFactory.getLogger(SignDocumentsNotificationSample.class);

	public static void main(String[] args) throws Exception {
		// validate args length
		if (args.length < 4) {
			System.out.println(MessageFormat.format(
					"usage: {0} {1} <user> <message> <files...> \n" + "\n" + "\t user: email/cpf of the target user \n"
							+ "\t message: text to be displayed \n"
							+ "\t files: path for one or more files to be signed \n",
					Constants.APP_NAME, Constants.COMMAND_SIGN_DOCS_NOTIFICATION));
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
		 * Upload files to Certillion server.
		 * 
		 * The upload is made via REST for better performance.
		 * 
		 * The server returns the hash of the file, which is used as the ID of
		 * this file in the server. So, you need to upload each file only once
		 * and can request it to be signed many times.
		 */
		for (FileInfo toBeUploaded : filesToSign) {
			CertillionApUtils.uploadDocument(toBeUploaded, Constants.REST_URL);
		}

		/*
		 * Create the endpoint client.
		 * 
		 * In a real application, you can hold this instance and reuse it for
		 * all requests.
		 */
		SignaturePortType endpoint = CertillionApUtils.newEndpoint(Constants.WSDL_URL, Constants.SERVICE_QNAME);

		/*
		 * Request the signature of the documents.
		 * 
		 * The server returns the ID of the transaction, which can be used to
		 * check it's status later.
		 */
		long transactionId = CertillionApUtils.signDocuments(user, message, filesToSign, endpoint,
				MessagingModeType.ASYNCH_SERVER_SERVER);
		
		/*
		 * Wait for the notification.
		 * 
		 * The server notify the endpoint with the transaction information.
		 */
		Endpoint.publish("http://localhost:9999/ws/icpm-signature-receiver", new APNotificationImpl());
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
