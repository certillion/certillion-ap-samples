package br.com.esec.icpm.sample.ap.core.signature.template.synch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.ws.Service;

import org.apache.commons.io.IOUtils;

import br.com.esec.icpm.mss.ws.MessagingModeType;
import br.com.esec.icpm.mss.ws.SignaturePortType;
import br.com.esec.icpm.mss.ws.SignatureRespType;
import br.com.esec.icpm.mss.ws.SignatureTemplateReqType;
import br.com.esec.icpm.sample.ap.core.WsSignerAddress;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;
import br.com.esec.icpm.server.ws.StringType;
import br.com.esec.icpm.server.ws.TemplateDataType;
import br.com.esec.icpm.server.ws.TemplateType;

@SuppressWarnings("restriction")
public class SignatureByTemplateSynchronousSample {

	public static void main(String[] args) throws MalformedURLException {
		if (args.length < 2) {
			System.out.println("SignatureByTemplateSynchronousSample need this params: [uniqueIdentifier] [text]");
			System.exit(1);
		}

		String uniqueIdentifier = args[0];
		String text = args[1];
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

		// Set template
		List<TemplateDataType> params = new ArrayList<TemplateDataType>();
		params.add(new StringType(text));
		TemplateType templateType = new TemplateType();
		templateType.setTemplateId((long) 1);
		templateType.setParameters(params);

		// Set the signature request
		SignatureTemplateReqType signatureTemplateReq = new SignatureTemplateReqType();
		signatureTemplateReq.setMessagingMode(MessagingModeType.SYNCH);
		signatureTemplateReq.setMobileUser(mobileUser);
		signatureTemplateReq.setTemplateToBeSigned(templateType);

		try {
			// Request to MSS the signature by template document mode synchronous
			System.out.println("Requesting signature by template...");
			System.out.println("\n\n");

			SignatureRespType signatureResp = signaturePortType.signatureByTemplate(signatureTemplateReq);

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
