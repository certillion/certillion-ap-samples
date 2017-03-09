import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.certillion.api.CertificateInfoType;
import com.certillion.api.ICPMException;
import com.certillion.api.MessagingModeType;
import com.certillion.api.MobileUserType;
import com.certillion.api.SignatureInfoTypeV2;
import com.certillion.api.SignaturePortType;
import com.certillion.api.SignatureStatusReqType;
import com.certillion.api.SignatureStatusRespTypeV2;
import com.certillion.api.SimpleSignatureReqTypeV3;
import com.certillion.api.SimpleSignatureRespTypeV3;
import com.certillion.api.StatusType;
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
		if (args.length != 2) {
			System.out.println("uso: java SimpleSignatureV2Sample email-id domain");
			System.out.println("ex:  java SimpleSignatureV2Sample user@gmail.com www.domain.com");
			System.exit(1);
		}

		String userId = args[0];
		String domain = args[1];
		
		// To use e-Sec's development server (must require access)
		final String WSDL_URL = "http://labs.certillion.com/mss/SignatureService/SignatureEndpointBean.wsdl";
		
		// To use your own ws-signer
		//final String WSDL_URL = "http://localhost:8280/mss/SignatureService/SignatureEndpointBean.wsdl";
		
		//Do you want to see the generated soap messages?
		//com.certillion.utils.WSUtils.dumpToConsole(true);
		
		// connect to service
		System.out.println("Conectando ao servico...");
		URL serviceUrl = new URL(WSDL_URL);
		QName qname = new QName("http://esec.com.br/mss/ap", "SignatureService");
		Service signatureService = Service.create(serviceUrl, qname);
		SignaturePortType signatureEndpoint = signatureService.getPort(SignaturePortType.class);
		
		// set the target user
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(userId);
		
		// mount the "signature" request
		//SignatureSimpleDocumentReqType signatureReq = new SignatureSimpleDocumentReqType();
		SimpleSignatureReqTypeV3 signatureReq = new SimpleSignatureReqTypeV3();
		int random = Math.abs((new Random().nextInt() % 100000));
		
		String dataToBeSigned = "Confirmo o codigo " + random
									+ " para fazer login em \"" + domain + "\"";
		
		signatureReq.setDataToBeSigned(dataToBeSigned);
		signatureReq.setMobileUser(mobileUser);
		signatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		signatureReq.setTestMode(true);

		// send the "signature" request to server
		System.out.println("Enviando solicitacao...");
		SimpleSignatureRespTypeV3 signatureResp = null;
		
		try {
			signatureResp = signatureEndpoint.simpleSignatureV3(signatureReq);
		}
		catch (ICPMException e) {
			StatusType exception = e.getFaultInfo();
			
			System.out.println("Falha de login: CODE[" 
							+ exception.getStatusCode() + "], DETAIL: " 
							+ exception.getStatusDetail() + ", MESSAGE: "
							+ exception.getStatusMessage());
			
			throw e;
		}
		
		CertillionStatus signatureRespValue = CertillionStatus.valueOf(signatureResp.getStatus().getStatusMessage());
		long transactionId = signatureResp.getTransactionId();
		
		// check the "signature" response
		if (signatureRespValue != CertillionStatus.REQUEST_OK) {
			System.out.println("Falha ao enviar assinatura, o servidor retornou: " + signatureRespValue);
		}
		else {
			// mount the "get-status" request
			SignatureStatusReqType statusReq = new SignatureStatusReqType();
			
			statusReq.setTransactionId(transactionId);
			// send the "get-status" request to server
			
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			SignatureStatusRespTypeV2 statusResp = null;
			CertillionStatus statusRespValue = null;
			
			do {
				System.out.println("Aguardando assinatura do usuario...");
				
				try {
					Thread.sleep(10000); // sleep for 10 seconds or the server will mark you as flood
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				statusResp = signatureEndpoint.statusQueryV2(statusReq);
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
			} while (statusRespValue == CertillionStatus.TRANSACTION_IN_PROGRESS);

			// check the "get-status" response
			if (statusRespValue == CertillionStatus.USER_CANCELED) {
				System.out.println("Falha de login: usuario cancelou");
			}
			else if (statusRespValue != CertillionStatus.SIGNATURE_VALID) {
				System.out.println("Falha na resposta, status: " + statusRespValue);
			}
			else {
				System.out.println("Login efetuado com sucesso");
				
				SignatureInfoTypeV2 signatureInfo = statusResp.getSignatureInfo();
				
				if (signatureInfo != null) {
					CertificateInfoType certificateInfo = signatureInfo.getSignerCertificate();
					String subjectDN = certificateInfo.getSubjectDn();
					
					System.out.println("\tUsuario: " + subjectDN.substring(subjectDN.indexOf("CN=") + 3));
					System.out.println("\tHora do login: " + signatureInfo.getSigningTime());
				}
			}
		}
	}
}
