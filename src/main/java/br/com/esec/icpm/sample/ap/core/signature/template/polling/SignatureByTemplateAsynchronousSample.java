package br.com.esec.icpm.sample.ap.core.signature.template.polling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.io.IOUtils;

import br.com.esec.icpm.mss.ws.MessagingModeType;
import br.com.esec.icpm.mss.ws.SignaturePortType;
import br.com.esec.icpm.mss.ws.SignatureRespType;
import br.com.esec.icpm.mss.ws.SignatureStatusReqType;
import br.com.esec.icpm.mss.ws.SignatureStatusRespType;
import br.com.esec.icpm.mss.ws.SignatureTemplateReqType;
import br.com.esec.icpm.sample.ap.core.WsSignerAddress;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;
import br.com.esec.icpm.server.ws.StringType;
import br.com.esec.icpm.server.ws.TemplateDataType;
import br.com.esec.icpm.server.ws.TemplateType;

@SuppressWarnings("restriction")
public class SignatureByTemplateAsynchronousSample {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		if (args.length < 2) {
			System.out.println("SignatureByTemplateAsynchronousSample need this params: [uniqueIdentifier] [text]");
			System.exit(1);
		}

		String uniqueIdentifier = args[0];
		String text = args[1];
		DataHandler signature = null;
		long transactionId = 0;

		// Get the signaturePort
		String endpointAddr = WsSignerAddress.get() + "/mss/serviceAp.wsdl";

		System.out
				.println("Connecting to Signature Service... " + endpointAddr);
		System.out.println("\n\n");

		URL url = new URL(endpointAddr);
		QName qName = new QName("http://esec.com.br/mss/ap", "SignatureService");
		Service signatureService = Service.create(url, qName);
		SignaturePortType signaturePortType = signatureService.getPort(SignaturePortType.class);

		// Set the mobileUser
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// Set template
		List<TemplateDataType> params = new ArrayList<TemplateDataType>();
		params.add(new StringType(text));
		TemplateType templateType = new TemplateType();
		templateType.setTemplateId((long) 1);
		templateType.setParameters(params);

		// Set the signature request
		SignatureTemplateReqType signatureTemplateReq = new SignatureTemplateReqType();
		signatureTemplateReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		signatureTemplateReq.setMobileUser(mobileUser);
		signatureTemplateReq.setTemplateToBeSigned(templateType);

		try {
			// Request to MSS the signature by template mode asynchronous
			// client-server
			System.out.println("Requesting signature by template...");
			System.out.println("\n\n");

			SignatureRespType signatureResp = signaturePortType.signatureByTemplate(signatureTemplateReq);

			SignatureStatusRespType signatureStatusResp = null;

			if (Status.valueOf(signatureResp.getStatus().getStatusMessage()) == Status.REQUEST_OK) {
				SignatureStatusReqType signatureStatusReq = new SignatureStatusReqType();
				signatureStatusReq.setTransactionId(signatureResp.getTransactionId());

				// Check the request of the signature
				// Stop when the transaction is completed
				do {
					System.out.println("Waiting signature...");
					signatureStatusResp = signaturePortType.statusQuery(signatureStatusReq);
					// Stop for 10 seconds
					Thread.sleep(10000);
				} while (Status.valueOf(signatureStatusResp.getStatus().getStatusMessage()) == Status.TRANSACTION_IN_PROGRESS);
			}

			System.out.println("Status "
					+ signatureStatusResp.getStatus().getStatusCode() + " "
					+ signatureStatusResp.getStatus().getStatusDetail());

			signature = signatureStatusResp.getSignature();
			transactionId = signatureResp.getTransactionId();

		} catch (ICPMException e) {
			System.err.println("Error " + e.getFaultInfo().getStatusCode() + " " + e.getFaultInfo().getStatusDetail());
		}

		// saves signature
		FileOutputStream output;
		try {
			output = new FileOutputStream(new File("signature-" + transactionId
					+ ".p7s"));
			IOUtils.copy(signature.getInputStream(), output);
			output.close();
			output = new FileOutputStream(new File("data-" + transactionId
					+ ".txt"));
			IOUtils.write(text, output);
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
