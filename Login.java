import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.certillion.api.CertificateFiltersType;
import com.certillion.api.CertificateInfoType;
import com.certillion.api.DocumentSignatureStatusInfoTypeV3;
import com.certillion.api.HsmCertificateFilterType;
import com.certillion.api.ICPMException;
import com.certillion.api.MessagingModeType;
import com.certillion.api.SignatureInfoTypeV2;
import com.certillion.api.SignaturePortTypeV2;
import com.certillion.api.SignatureStatusReqType;
import com.certillion.api.SimpleSignatureReqTypeV4;
import com.certillion.api.SimpleSignatureRespTypeV4;
import com.certillion.api.StatusRespType;
import com.certillion.api.StatusType;
import com.certillion.api.UserType;
import com.certillion.utils.CertillionStatus;

/**
 * Exemplo de login utilizando o Certillion.
 * O login no Certillion é uma assinatura de textosimples, baseada em SimpleSignature.
 * A ideia é enviar um texto dinâmico (isto é, com um código variável gerado pelo
 * próprio sistema) para o usuário assinar e, se a assinatura retornar com sucesso,
 * liberar o acesso.
 * 
 * NOTA: ESTE EXEMPLO TEM COMO OBJETIVO PRINCIPAL A DIDÁTICA, PARA QUE O 
 * 		 DESENVOLVEDOR ENTENDA O FUNCIONAMENTO DE MODO RÁPIDO E EFETIVO. ANTES
 *       DE COPIAR TRECHOS DESSE CÓDIGO, AVALIE O IMPACTO NA ARQUITETURA DO
 *       SEU SISTEMA (EX: J2EE), POIS ALGUMAS ABORDAGENS PODEM AFETAR DESEMPENHO
 *       (EX: Thread.sleep) OU SER INCOMPATÍVEIS (EX: LOG).  
 * 
 * @author CertillionTeam
 */
public class Login {

	public static void main(String[] args) throws MalformedURLException, ICPMException, UnsupportedEncodingException {
		if (args.length < 2 || args.length > 3) {
			System.out.println("uso: java SimpleSignatureV2Sample [-hsm|-hsmAuth] email-id domain");
			System.out.println("ex:  java SimpleSignatureV2Sample user@gmail.com www.domain.com");
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
		String domain = args[1 + shift];
		
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
		//SignatureSimpleDocumentReqType signatureReq = new SignatureSimpleDocumentReqType();
		SimpleSignatureReqTypeV4 signatureReq = new SimpleSignatureReqTypeV4();
		int random = Math.abs((new Random().nextInt() % 100000));
		
		// "I've verified the code" + random + " to login at " + domain; 
		String dataToBeSigned = "Login em \"" + domain + "\". Apos digitar a senha, copie o código ["
									+ random + "] na tela do sistema.";
		
		signatureReq.setDataToBeSigned(dataToBeSigned);
		signatureReq.setUser(user);
		signatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);

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
			
			System.out.println("Exception in login: CODE[" 
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
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
			} while (statusRespValue == CertillionStatus.TRANSACTION_IN_PROGRESS);

			// check the "get-status" response
			if (statusRespValue == CertillionStatus.USER_CANCELED) {
				System.out.println("Login failure: user cancelled");
			}
			else if (statusRespValue != CertillionStatus.SIGNATURE_VALID) {
				System.out.println("Login failure, status: " + statusRespValue);
			}
			else {
				List<DocumentSignatureStatusInfoTypeV3> documentSignatureStatusInfoType = statusResp.getDocumentSignatureStatus();
				
				for (DocumentSignatureStatusInfoTypeV3 doc : documentSignatureStatusInfoType) {
					System.out.println("Login success");
					
					SignatureInfoTypeV2 signatureInfo = doc.getSignatureInfo();
					
					if (signatureInfo != null) {
						CertificateInfoType certificateInfo = signatureInfo.getSignerCertificate();
						String subjectDN = certificateInfo.getSubjectDn();
						
						System.out.println("\tUser: " + subjectDN.substring(subjectDN.indexOf("CN=") + 3));
						System.out.println("\tLogin time: " + signatureInfo.getSigningTime());
					}
				}
			}
		}
	}
}
