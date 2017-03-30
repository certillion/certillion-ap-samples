import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.certillion.api.CertificateInfoType;
import com.certillion.api.ICPMException;
import com.certillion.api.SignatureInfoTypeV2;
import com.certillion.api.SignaturePortType;
import com.certillion.api.ValidateReqType;
import com.certillion.api.ValidateRespTypeV2;

/**
 * Exemplo de verificação de assinatura, versão 2.
 * 
 * NOTA: ESTE EXEMPLO TEM COMO OBJETIVO PRINCIPAL A DIDÁTICA, PARA QUE O 
 * 		 DESENVOLVEDOR ENTENDA O FUNCIONAMENTO DE MODO RÁPIDO E EFETIVO. ANTES
 *       DE COPIAR TRECHOS DESSE CÓDIGO, AVALIE O IMPACTO NA ARQUITETURA DO
 *       SEU SISTEMA (EX: J2EE), POIS ALGUMAS ABORDAGENS PODEM AFETAR DESEMPENHO
 *       (EX: Thread.sleep) OU SER INCOMPATÍVEIS (EX: LOG).
 * 
 * @author CertillionTeam
 */
public class ValidateSignatureSample {

	public static void main(String[] args) throws Exception {

		if (args.length < 1 || args.length > 2) {
			System.out.println("usage:   java ValidateSignatureSample signature-filename [content-filename]");
			System.out.println("example: java ValidateSignatureSample signature.p7m                  (p7m file is a CAdES attached mode signature file");
			System.out.println("      or java ValidateSignatureSample signature.p7s content-filename (p7s file is a CAdES detached mode signature file)");
			System.exit(1);
		}
		
		String signatureFilename = args[0];
		String contentFilename = null;
		
		if (args.length == 2)
			contentFilename = args[1];
		
		// To use e-Sec's development server (must require access)
		final String WSDL_URL = "http://labs.certillion.com/mss/SignatureService/SignatureEndpointBean.wsdl";
		
		// To use your own ws-signer
		//final String WSDL_URL = "http://localhost:8280/mss/SignatureService/SignatureEndpointBean.wsdl";
		
		//Do you want to see the generated soap messages?
		//com.certillion.utils.WSUtils.dumpToConsole(true);
		
		// Get the signaturePort
		System.out.println("Connecting to Signature Service... ");
		URL serviceUrl = new URL(WSDL_URL);
		QName qname = new QName("http://esec.com.br/mss/ap", "SignatureService");
		Service signatureService = Service.create(serviceUrl, qname);
		SignaturePortType signaturePortType = signatureService.getPort(SignaturePortType.class);
		
		// Request to MSS the signature of text mode
		try {
			System.out.println("Requesting validation...");
	
			// set the validation request
			ValidateReqType validateReq = new ValidateReqType();
			
			// set the signature and content data sources
			FileDataSource file = new FileDataSource(new File(signatureFilename));
			DataHandler signature = new DataHandler(file);
			
			validateReq.setSignature(signature);
			
			if (contentFilename != null) {
				validateReq.setContent(loadFile(contentFilename));
			}
			
			// get the validation response
			ValidateRespTypeV2 validateResp = signaturePortType.validateV2(validateReq);
	
			if (validateResp.getError() != null) {
				System.out.println("Error verifying signature: " + validateResp.getError());
			}
	
			// print results
			List<SignatureInfoTypeV2> list = validateResp.getSignatures();
			if (list.size() > 0) {
				System.out.println("Number of Signatures: " + list.size());
				int count = 0;
				for (SignatureInfoTypeV2 signatureInfoType : list) {
					System.out.println("\t\t--------------- Signature " + (++count) + " ---------------\n");
					
					System.out.println("Status: " + (signatureInfoType.isValid() ? "VALID" : "INVALID"));
					
					System.out.println("Signer Certificate");
					
					CertificateInfoType signerCertificateInfoType = signatureInfoType.getSignerCertificate();
					
					System.out.println("\tIssuerDn: " + signerCertificateInfoType.getIssuerDn());
					System.out.println("\tSubjectDn: " + signerCertificateInfoType.getSubjectDn());
					System.out.println("\tNotAfter: " + signerCertificateInfoType.getNotAfter());
					System.out.println("\tNotBefore: " + signerCertificateInfoType.getNotBefore());
					System.out.println("\tSerialNumber: " + signerCertificateInfoType.getSerialNumber());
					System.out.println();
					
					System.out.println("Issuer Certificate");
					
					CertificateInfoType issuerCertificateInfoType = signerCertificateInfoType.getIssuerCertificate();
					
					System.out.println("\tIssuerDn: " + issuerCertificateInfoType.getIssuerDn());
					System.out.println("\tSubjectDn: " + issuerCertificateInfoType.getSubjectDn());
					System.out.println("\tNotAfter: " + issuerCertificateInfoType.getNotAfter());
					System.out.println("\tNotBefore: " + issuerCertificateInfoType.getNotBefore());
					System.out.println("\tSerialNumber: " + issuerCertificateInfoType.getSerialNumber());
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
		}
		catch (ICPMException e) {
			System.err.println("Error " + e.getFaultInfo().getStatusCode() + ": " + e.getFaultInfo().getStatusDetail());
		}
	}
	
	private static byte[] loadFile(String filename) throws Exception {
		File file = new File(filename);
		
		if (!file.exists()) {
			throw new Exception("File \"" + filename + "\" not found");
		}
		if (file.length() == 0) {
			throw new Exception("Empty file \"" + filename + "\"");
		}
		
		InputStream is = new FileInputStream(file); 
		byte buffer[] = new byte[is.available()];
		
		is.read(buffer); // only works because it's a FileInputStream
		
		return buffer;
	}
}
