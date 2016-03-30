package br.com.esec.icpm.samples.ap.core;

import br.com.esec.icpm.samples.ap.Constants;
import br.com.esec.mss.ap.*;
import org.apache.commons.io.IOUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.ws.Service;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@SuppressWarnings("restriction")
public class ValidateSignatureSample {

	// private static final String ADRRESS = WebServiceInfo.getBaseUrl();
	private static final String ADRRESS = "http://labs.certillion.com";

	private static final String CERTILLION_SERVER_SOAP_WSDL_URL = ADRRESS + "/mss/serviceAp_prod.wsdl";
	private static SignaturePortType signaturePortType = null;

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("usage: certillion-ap-samples validate-signature [signature-file] [data-file]");
			System.exit(1);
		}

		String signatureFile = args[1];
		String dataFile = args[2];
		DataHandler signature;
		byte[] content;

		// Get the signaturePort

		System.out.println("Connecting to Signature Service... " + CERTILLION_SERVER_SOAP_WSDL_URL);
		System.out.println("\n\n");

		URL serviceUrl = new URL(Constants.WSDL_URL);
		Service signatureService = Service.create(serviceUrl, Constants.SERVICE_QNAME);
		signaturePortType = signatureService.getPort(SignaturePortType.class);

		// set the signature and content data sources
		FileDataSource file = new FileDataSource(new File(signatureFile));
		signature = new DataHandler(file);

		FileDataSource data = new FileDataSource(new File(dataFile));
		content = IOUtils.toByteArray(data.getInputStream());

		try {
			// Request to MSS the signature of text mode synchronous
			System.out.println("Requesting validation...");
			System.out.println("\n\n");

			// set the validation request
			ValidateReqType validateReq = new ValidateReqType();
			validateReq.setSignature(signature);
			validateReq.setContent(content);

			// get the validation response
			ValidateRespType validateResp = signaturePortType.validate(validateReq);

			if (validateResp.getError() != null) {
				System.out.println("Error verifying signature: " + validateResp.getError());
			}

			// print results
			List<SignatureInfoType> list = validateResp.getSignatures();
			if (list.size() > 0) {
				System.out.println("Number of Signatures: " + list.size());
				int count = 0;
				for (SignatureInfoType signatureInfoType : list) {
					System.out.println("\t\t--------------- Signature " + (++count) + " ---------------\n");
					
					
					System.out.println("Status: " + (signatureInfoType.isValid() ? "VALID" : "INVALID"));
					
					System.out.println("Signer Certificate");
					System.out.println("\tIssuerDn: " + signatureInfoType.getSignerCertificate().getIssuerDn());
					System.out.println("\tSubjectDn: " + signatureInfoType.getSignerCertificate().getSubjectDn());
					System.out.println("\tNotAfter: " + signatureInfoType.getSignerCertificate().getNotAfter());
					System.out.println("\tNotBefore: " + signatureInfoType.getSignerCertificate().getNotBefore());
					System.out.println("\tSerialNumber: " + signatureInfoType.getSignerCertificate().getSerialNumber());
					System.out.println();
					System.out.println("Issuer Certificate");
					System.out.println("\tIssuerDn: " + signatureInfoType.getSignerCertificate().getIssuerCertificate().getIssuerDn());
					System.out.println("\tSubjectDn: " + signatureInfoType.getSignerCertificate().getIssuerCertificate().getSubjectDn());
					System.out.println("\tNotAfter: " + signatureInfoType.getSignerCertificate().getIssuerCertificate().getNotAfter());
					System.out.println("\tNotBefore: " + signatureInfoType.getSignerCertificate().getIssuerCertificate().getNotBefore());
					System.out.println("\tSerialNumber: " + signatureInfoType.getSignerCertificate().getIssuerCertificate().getSerialNumber());
					System.out.println();
					System.out.println("Signature Status: " + (signatureInfoType.isValid() ? "valid" : "invalid"));
					System.out.println("SigningTime: " + signatureInfoType.getSigningTime());
					System.out.println("PolicyId: " + signatureInfoType.getPolicyId());
					System.out.println("PolicyUrl: " + signatureInfoType.getPolicyUrl());

					if (!signatureInfoType.isValid()) {
						System.out.println();
						System.out.println("InvalidReason: " + signatureInfoType.getInvalidReason());
					}

				}
			}
		} catch (ICPMException e) {
			System.err.println("Error " + e.getFaultInfo().getStatusCode() + " " + e.getFaultInfo().getStatusDetail());
		}
	}
}
