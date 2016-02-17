package br.com.esec.icpm.samples.ap.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.ws.Service;

import org.apache.commons.io.IOUtils;

import br.com.esec.icpm.mss.ws.MessagingModeType;
import br.com.esec.icpm.mss.ws.SignaturePortType;
import br.com.esec.icpm.mss.ws.SignatureRespType;
import br.com.esec.icpm.mss.ws.SignatureSimpleDocumentReqType;
import br.com.esec.icpm.mss.ws.SignatureStatusReqType;
import br.com.esec.icpm.mss.ws.SignatureStatusRespType;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			System.out.println(
					"usage: certillion-ap-samples sign-text [identifier] [message] \n" +
					"\n" +
					"\t identifier: email of the user \n" +
					"\t message: text to be signed \n"
			);
			System.exit(1);
		}

		// get args
		String uniqueIdentifier = args[1];
		String textToBeSigned = args[2];

		// connect to service
		log.info("Connecting to service...");
		URL serviceUrl = new URL(WebServiceInfo.getApServiceUrl());
		Service signatureService = Service.create(serviceUrl, SignaturePortType.QNAME);
		SignaturePortType signatureEndpoint = signatureService.getPort(SignaturePortType.class);

		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// mount the "signature" request
		SignatureSimpleDocumentReqType signatureReq = new SignatureSimpleDocumentReqType();
		signatureReq.setDataToBeSigned(textToBeSigned);
		signatureReq.setMobileUser(mobileUser);
		signatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
//		signatureSimpleDocumentReq.setSignaturePolicy(SignaturePolicyType.AD_RT);

		try {
			// send the "signature" request to server
			log.info("Sending request...");
			SignatureRespType signatureResp = signatureEndpoint.signatureSimpleDocument(signatureReq);
			Status signatureRespValue = Status.valueOf(signatureResp.getStatus().getStatusMessage());
			long transactionId = signatureResp.getTransactionId();

			// check the "signature" response
			if (signatureRespValue != Status.REQUEST_OK) {
				log.error("Error sending request, server returned {}", signatureRespValue);
				System.exit(1);
			}

			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			statusReq.setTransactionId(transactionId);

			// send the "get-status" request to server
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			SignatureStatusRespType statusResp = null;
			Status statusRespValue = null;
			do {
				log.info("Waiting signature from user...");
				statusResp = signatureEndpoint.statusQuery(statusReq);
				statusRespValue = Status.valueOf(statusResp.getStatus().getStatusMessage());
				Thread.sleep(10000); // sleep for 10 seconds or the server will mark you as flood
			} while (statusRespValue == Status.TRANSACTION_IN_PROGRESS);

			// check the "get-status" response
			if (statusRespValue != Status.SIGNATURE_VALID) {
				log.error("Error receiving the response, the status is {}", statusRespValue);
				System.exit(1);
			}

			// extract the signature from the response
			DataHandler signature = statusResp.getSignature();
			log.info("Signature received successfully.");

			// saves signature
			String outputFileName = "signature-" + transactionId + ".p7s";
			FileOutputStream output = new FileOutputStream(outputFileName);
			IOUtils.copy(signature.getInputStream(), output);
			output.close();
			log.info("Signature saved in file {}", outputFileName);

		} catch (Exception e) {
			log.error("Could not complete the example", e);
		}
	}

}
