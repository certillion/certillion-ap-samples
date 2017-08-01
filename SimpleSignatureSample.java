import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.certillion.api.CertificateFiltersType;
import com.certillion.api.CertificateInfoType;
import com.certillion.api.DocumentSignatureStatusInfoTypeV3;
import com.certillion.api.HsmCertificateFilterType;
import com.certillion.api.ICPMException;
import com.certillion.api.MessagingModeType;
import com.certillion.api.MobileStatus;
import com.certillion.api.SignatureInfoTypeV2;
import com.certillion.api.SignaturePolicyType;
import com.certillion.api.SignaturePortTypeV2;
import com.certillion.api.SignatureStatusReqType;
import com.certillion.api.SimpleSignatureReqTypeV4;
import com.certillion.api.SimpleSignatureRespTypeV4;
import com.certillion.api.StatusRespType;
import com.certillion.api.StatusType;
import com.certillion.api.StatusTypeV2;
import com.certillion.api.UserType;
import com.certillion.utils.CertillionStatus;

/**
 * Exemplo de assinatura textual simples, versão 3.
 * 
 * NOTA: ESTE EXEMPLO TEM COMO OBJETIVO PRINCIPAL A DIDÁTICA, PARA QUE O 
 * 		 DESENVOLVEDOR ENTENDA O FUNCIONAMENTO DE MODO RÁPIDO E EFETIVO. ANTES
 *       DE COPIAR TRECHOS DESSE CÓDIGO, AVALIE O IMPACTO NA ARQUITETURA DO
 *       SEU SISTEMA (EX: J2EE), POIS ALGUMAS ABORDAGENS PODEM AFETAR DESEMPENHO
 *       (EX: Thread.sleep) OU SER INCOMPATÍVEIS (EX: LOG).  
 * 
 * @author CertillionTeam
 */
public class SimpleSignatureSample {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		if (args.length < 4 || args.length > 5) {
			System.out.println("usage:   java SimpleSignatureSample [-hsm|-hsmAuth] email-id message sendername output-filename");
			System.out.println("example: java SimpleSignatureSample user@gmail.com \"hello world\" \"me\" signature.p7m");
			System.exit(1);
		}
		
		// reading command-line parameters
		int shift = 1;
		boolean useHSM = false;
		boolean useAuthentic = false;		
		
		if (args[0].toLowerCase().trim().equals("-hsm")) {
			useHSM = true;
		}
		else if (args[0].toLowerCase().trim().equals("-hsmauth")) {
			useHSM = useAuthentic = true;
		}
		else {
			shift = 0;
		}

		String userId = args[0 + shift];
		String message = args[1 + shift];
		String sender = args[2 + shift];
		String filename = args[3 + shift];
		
		// To use e-Sec's development server (must require access)
		final String WSDL_URL = "http://labs.certillion.com/mss/SignatureService/SignatureEndpointBeanV2.wsdl";

		// To use your own ws-signer
		//final String WSDL_URL = "http://localhost:8280/mss/SignatureService/SignatureEndpointBeanV2.wsdl";
		
		//Do you want to see the generated soap messages?
		//com.certillion.utils.WSUtils.dumpToConsole(true);
		
		// connect to service
		System.out.println("Connecting to service at " + WSDL_URL);
		URL serviceUrl = new URL(WSDL_URL);
		QName qname = new QName("http://esec.com.br/mss/ap", "SignatureService");
		Service signatureService = Service.create(serviceUrl, qname);
		SignaturePortTypeV2 signatureEndpoint = signatureService.getPort(SignaturePortTypeV2.class);
		
		// set the target user
		UserType user = new UserType();
		user.setIdentifier(userId);
		
		// mount the "signature" request
		SimpleSignatureReqTypeV4 signatureReq = new SimpleSignatureReqTypeV4();
		
		signatureReq.setDataToBeSigned(message + "\n\n" + "Enviado por " + sender);
		signatureReq.setUser(user);
		signatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		signatureReq.setSignaturePolicy(SignaturePolicyType.AD_RB);
		
		boolean usingICPBRASILCertificates = true;
		
		signatureReq.setTestMode(!usingICPBRASILCertificates);

		if (useHSM) {
			System.out.println("Request to sign in HSM mode");
			HsmCertificateFilterType hsmFilter = new HsmCertificateFilterType();
			CertificateFiltersType filter = new CertificateFiltersType();
			
			hsmFilter.setValue(true);
			filter.getTrustChainOrOwnerCertificateOrAlgorithm().add(hsmFilter);
			signatureReq.setCertificateFilters(filter);
		}
		
		if (useAuthentic) {
			System.out.println("Requesting authorization for automatic signature");
			signatureReq.setFingerprint("AUTH");
		}

		// send the "signature" request to server
		System.out.println("Sending request to " + userId);
		SimpleSignatureRespTypeV4 signatureResp = null;
		
		try {
			signatureResp = signatureEndpoint.simpleSignature(signatureReq);
		}
		catch (ICPMException e) {
			StatusType exception = e.getFaultInfo();
			
			System.out.println("Exception in signature request: CODE[" 
							+ exception.getStatusCode() + "], DETAIL: " 
							+ exception.getStatusDetail() + ", MESSAGE: "
							+ exception.getStatusMessage());
			
			throw e;
		}
		
		CertillionStatus signatureRespValue = CertillionStatus.valueOf(signatureResp.getStatus().getStatusMessage());
		long transactionId = signatureResp.getTransactionId();
		
		// check the "signature" response
		if (signatureRespValue != CertillionStatus.REQUEST_OK) {
			System.out.println("Error sending request, server returned: " + signatureRespValue);
		}
		else {
			System.out.println("Request sent, transaction ID is: " + transactionId);
			
			if (useHSM && !useAuthentic)
				System.out.println("\tverification code: " + signatureResp.getVerificationCode());

			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			
			statusReq.setTransactionId(transactionId);
			// send the "get-status" request to server
			
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			StatusRespType statusResp = null;
			CertillionStatus statusRespValue = null;
			
			do {
				System.out.println("Waiting signature from user...");
				
				try {
					// TODO REVIEW: DON'T USE THIS ON PRODUCTION OR YOU'LL HAVE PERFORMANCE ISSUES!!!
					Thread.sleep(10000); // wait for at least 10 seconds or the server will mark you as flood
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				statusResp = signatureEndpoint.signatureStatusQuery(statusReq);
				
				StatusTypeV2 statusType = statusResp.getStatus();
				MobileStatus mobileStatus = statusType.getMobileStatus();
				int statusCode = statusType.getStatusCode();
				String statusDetail = statusType.getStatusDetail();
				String statusMessage = statusType.getStatusMessage();
				
				System.out.println("\tmobileStatus: " + (mobileStatus == null ? "null" : mobileStatus.toString()));
				System.out.println("\tstatusCode: " + statusCode);
				System.out.println("\tstatusDetail: " + statusDetail);
				System.out.println("\tstatusMessage: " + statusMessage);
				System.out.println();
				
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
			} while (statusRespValue == CertillionStatus.TRANSACTION_IN_PROGRESS);

			// check the "get-status" response
			if (statusRespValue != CertillionStatus.SIGNATURE_VALID) {
				System.out.println("Error receiving the response, the status is " + statusRespValue);
			}
			else {
				List<DocumentSignatureStatusInfoTypeV3> documentSignatureStatusInfoType = statusResp.getDocumentSignatureStatus();
				
				for (DocumentSignatureStatusInfoTypeV3 doc : documentSignatureStatusInfoType) {
					System.out.println("Signature received successfully for transactionID = " + doc.getTransactionId());
					
					SignatureInfoTypeV2 signatureInfo = doc.getSignatureInfo();
					
					if (signatureInfo != null) {
						System.out.println("\tLegal identifier : \"" + signatureInfo.getLegalIdentifier() + "\"");
						System.out.println("\tPolicy id: \"" + signatureInfo.getPolicyId() + "\"");
						System.out.println("\tPolicy url: \"" + signatureInfo.getPolicyUrl() + "\"");
						System.out.println("\tSigning time: " + signatureInfo.getSigningTime());
						
						CertificateInfoType certificateInfo = signatureInfo.getSignerCertificate();
						String subjectDN = certificateInfo.getSubjectDn();
						
						System.out.println("\tSigner: " + subjectDN.substring(subjectDN.indexOf("CN=") + 3));
						System.out.println();
					}
					
					// extract the signature from the response
					byte[] signature = doc.getSignature();
					
					// saves signature
					File file = new File(filename);
					FileOutputStream output = new FileOutputStream(file);
					
					output.write(signature);
					output.flush();
					output.close();
					
					System.out.println("Signature saved in file " + file.getAbsolutePath());
					System.out.println();
				}
			}
		}
	}
}
