package br.com.esec.icpm.sample.ap.core.signature.simple.synch;

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
import br.com.esec.icpm.sample.ap.core.WsSignerAddress;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;

@SuppressWarnings("restriction")
public class SignatureSimpleDocumentSynchronousSample {

	public static void main(String[] args) throws MalformedURLException {
		if (args.length < 2) {
			System.out.println("SignatureSimpleDocumentSynchronousSample need this params: [uniqueIdentifier] [textToBeSigned]");
			System.exit(1);
		}

		String uniqueIdentifier = args[0];
		String textToBeSigned = args[1];

		DataHandler signature = null;
		long transactionId = 0;

		// Get the signaturePort
		String endpointAddr = WsSignerAddress.get() + "/mss/serviceAp.wsdl";

		System.out.println("Connecting to Signature Service... " + endpointAddr);
		System.out.println("\n\n");

		URL url = new URL(endpointAddr);
		Service signatureService = Service.create(url, SignaturePortType.QNAME);
		SignaturePortType signaturePortType = signatureService.getPort(SignaturePortType.class);

		// Set the mobileUser
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// Set the signature request
		SignatureSimpleDocumentReqType signatureSimpleDocumentReq = new SignatureSimpleDocumentReqType();
		signatureSimpleDocumentReq.setDataToBeSigned(textToBeSigned);
		signatureSimpleDocumentReq.setMobileUser(mobileUser);
		signatureSimpleDocumentReq.setMessagingMode(MessagingModeType.SYNCH);

		// validate signature

		try {
			// Request to MSS the signature of text mode synchronous
			System.out.println("Requesting signature of text...");
			System.out.println("\n\n");

			SignatureRespType signatureResp = signaturePortType.signatureSimpleDocument(signatureSimpleDocumentReq);

			System.out.println("Status " + signatureResp.getStatus().getStatusCode() + " " + signatureResp.getStatus().getStatusDetail());

			signature = signatureResp.getSignature();
			transactionId = signatureResp.getTransactionId();

		} catch (ICPMException e) {
			System.err.println("Error " + e.getFaultInfo().getStatusCode() + " " + e.getFaultInfo().getStatusDetail());
		}

		// saves signature
		FileOutputStream output;
		try {
			output = new FileOutputStream(new File("signature-" + transactionId + ".p7s"));
			IOUtils.copy(signature.getInputStream(), output);
			output.close();
			output = new FileOutputStream(new File("data-" + transactionId + ".txt"));
			IOUtils.write(textToBeSigned, output);
			output.close();
			System.out.println("\n####### FILES GENERATED #######");
			System.out.println("\tsignature-" + transactionId + ".p7s");
			System.out.println("\tdata-" + transactionId + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
