package br.com.esec.ws;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.esec.icpm.mss.ws.notification.APNotificationPortType;
import br.com.esec.icpm.mss.ws.notification.BatchSignatureNotificationType;
import br.com.esec.icpm.mss.ws.notification.SignatureNotificationType;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.factory.StatusTypeFactory;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.StatusRespType;
import br.com.esec.icpm.server.ws.StatusType;

@WebService(endpointInterface = "br.com.esec.icpm.mss.ws.notification.APNotificationPortType")
public class APNotificationImpl implements APNotificationPortType {
	private static final Logger log = LoggerFactory.getLogger(APNotificationImpl.class);

	@Override
	public StatusRespType batchSignatureNotification(BatchSignatureNotificationType notificationReq)
			throws ICPMException {
		long transactionId = notificationReq.getTransactionId();
		log.info("Notification received - " + transactionId);

		long batchTID = transactionId;

		StatusRespType response = new StatusRespType();
		try {
			StatusType status = notificationReq.getStatus();

			if (status.getStatusCode() == Status.REQUEST_OK.getCode()) {
				log.info("The transaction {} was signed.", batchTID);

				response.setStatus(StatusTypeFactory.create(Status.REQUEST_OK)); // Set
																					// response

			} else {
				log.info("The transaction {} was rejeted or has any error.", batchTID);

				response.setStatus(StatusTypeFactory.create(Status.REQUEST_OK)); // Set
																					// response
				
			}

			return response;
		} catch (Exception e) {
			log.error("Internal error - ", e);
			throw new InternalError(e);
		}

	}

	@Override
	public StatusRespType signatureNotification(SignatureNotificationType notificationReq) throws ICPMException {
		// TODO Auto-generated method stub
		return null;
	}
}