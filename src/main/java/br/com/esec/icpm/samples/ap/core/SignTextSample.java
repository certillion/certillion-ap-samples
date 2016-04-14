package br.com.esec.icpm.samples.ap.core;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.icpm.samples.ap.core.utils.CertillionStatus;
import br.com.esec.mss.ap.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Service;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * This example shows how to request the signature of a simple text message.
 *
 * To get the response, this example uses the "polling" method, which periodically check the status of the transaction
 * with the server. In a real application, you should move this "polling" logic to an appropriate mechanism, such as an
 * ExecutorService, TimerService, etc.
 */
public class SignTextSample {

	private static final Logger log = LoggerFactory.getLogger(SignTextSample.class);

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		// validate args length
		if (args.length < 3) {
			System.out.println(MessageFormat.format(
					"usage: {0} {1} <user> <message> \n" +
					"\n" +
					"\t user: email of the target user \n" +
					"\t message: text to be signed \n",
					Constants.APP_NAME, Constants.COMMAND_SIGN_TEXT
			));
			System.exit(1);
		}

		// get args
		String uniqueIdentifier = args[1];
		String textToBeSigned = args[2];

		// connect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(Constants.WSDL_URL);
		Service signatureService = Service.create(serviceUrl, Constants.SERVICE_QNAME);
		SignaturePortType signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// mount the "signature" request
		SignatureSimpleDocumentReqType signatureReq = new SignatureSimpleDocumentReqType();
		signatureReq.setDataToBeSigned(textToBeSigned);
		signatureReq.setMobileUser(mobileUser);
		signatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		signatureReq.setTestMode(false);
//		signatureSimpleDocumentReq.setSignaturePolicy(SignaturePolicyType.AD_RT);

		try {
			// send the "signature" request to server
			log.info("Sending request...");
			SignatureRespType signatureResp = signatureEndpoint.signatureSimpleDocument(signatureReq);
			CertillionStatus signatureRespValue = CertillionStatus.valueOf(signatureResp.getStatus().getStatusMessage());
			long transactionId = signatureResp.getTransactionId();

			// check the "signature" response
			if (signatureRespValue != CertillionStatus.REQUEST_OK) {
				log.error("Error sending request, server returned {}", signatureRespValue);
				System.exit(1);
			}

			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			statusReq.setTransactionId(transactionId);

			// send the "get-status" request to server
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			SignatureStatusRespType statusResp = null;
			CertillionStatus statusRespValue = null;
			do {
				log.info("Waiting signature from user...");
				statusResp = signatureEndpoint.statusQuery(statusReq);
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
				Thread.sleep(10000); // sleep for 10 seconds or the server will mark you as flood
			} while (statusRespValue == CertillionStatus.TRANSACTION_IN_PROGRESS);

			// check the "get-status" response
			if (statusRespValue != CertillionStatus.SIGNATURE_VALID) {
				log.error("Error receiving the response, the status is {}", statusRespValue);
				System.exit(1);
			}

			// extract the signature from the response
			byte[] signature = statusResp.getSignature();
			log.info("Signature received successfully.");

			// saves signature
			String outputFileName = "signature-" + transactionId + ".p7s";
			FileOutputStream output = new FileOutputStream(outputFileName);
			IOUtils.write(signature, output);
			output.close();
			log.info("Signature saved in file {}", outputFileName);

		} catch (Exception e) {
			log.error("Could not complete the example", e);
		}
	}

}
