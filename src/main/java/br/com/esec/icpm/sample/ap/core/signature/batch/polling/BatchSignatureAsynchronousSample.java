package br.com.esec.icpm.sample.ap.core.signature.batch.polling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import br.com.esec.icpm.libs.signature.helper.MimeTypeConstants;
import br.com.esec.icpm.mss.ws.BatchSignatureComplexDocumentReqType;
import br.com.esec.icpm.mss.ws.BatchSignatureComplexDocumentRespType;
import br.com.esec.icpm.mss.ws.BatchSignatureTIDsRespType;
import br.com.esec.icpm.mss.ws.DocumentSignatureInfoType;
import br.com.esec.icpm.mss.ws.DocumentSignatureStatusInfoType;
import br.com.esec.icpm.mss.ws.HashDocumentInfoType;
import br.com.esec.icpm.mss.ws.MessagingModeType;
import br.com.esec.icpm.mss.ws.SignaturePortType;
import br.com.esec.icpm.mss.ws.SignatureStandardType;
import br.com.esec.icpm.mss.ws.SignatureStatusReqType;
import br.com.esec.icpm.sample.ap.core.WsSignerAddress;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;

@SuppressWarnings("restriction")
public class BatchSignatureAsynchronousSample {

	private static final String CADES_EXTENSION = "p7s";
	private static final String XADES_EXTENSION = "xml";
	private static final String PDF_EXTENSION = "pdf";

	private static final String uploadUrl = WsSignerAddress.get() + "/mss/restAp/document";
	private static final String downloadUrl = WsSignerAddress.get() + "/mss/restAp/document/signed/%s";

	private static List<String> urls = new ArrayList<String>();
	private static String uniqueIdentifier = null;
	private static String dataToBeDisplayed = null;
	private static String standard = null;

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			System.out.println("BatchSignatureSynchronousSample need this params: [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [urls] ");
			System.exit(1);
		}

		// Get arguments from request
		for (int i = 0; i < args.length; i++) {
			if (i == 0) {
				uniqueIdentifier = args[i];
			} else if (i == 1) {
				dataToBeDisplayed = args[i];
			} else if (i == 2) {
				standard = args[i];
			} else {
				urls.add(args[i]);
			}
		}

		List<HashDocumentInfoType> documents = new ArrayList<HashDocumentInfoType>();
		List<DocumentSignatureInfoType> signatures = null;
		List<DocumentSignatureStatusInfoType> signaturesStatus = null;
		long transactionId = 0;

		// Get the signaturePort
		String endpointAddr = WsSignerAddress.get() + "/mss/serviceAp.wsdl";

		System.out.println("Connecting to Signature Service... " + endpointAddr);
		System.out.println();
		System.out.println();
		System.out.println();

		URL urlws = new URL(endpointAddr);
		Service signatureService = Service.create(urlws, SignaturePortType.QNAME);
		SignaturePortType signaturePortType = signatureService.getPort(SignaturePortType.class);

		// Set the mobileUser
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// Create the batch request
		BatchSignatureComplexDocumentReqType request = new BatchSignatureComplexDocumentReqType();
		request.setMobileUser(mobileUser);
		request.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		request.setDataToBeDisplayed(dataToBeDisplayed);
		request.setSignatureStandard(SignatureStandardType.valueOf(standard));

		// upload files from each url
		for (String url : urls) {
			System.out.println("Sending document '" + url + "'... ");
			URL fileUrl = new URL(url);
			InputStream sourceIn = fileUrl.openStream();

			URL obj = new URL(uploadUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);

			con.setRequestMethod("POST");

			con.setRequestProperty("Content-type", "application/octet-stream");

			OutputStream out = con.getOutputStream();
			IOUtils.copy(sourceIn, out);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Get hash from uploaded file, create document and save the
			// document in the list
			HashDocumentInfoType document = new HashDocumentInfoType();
			document.setDocumentName(FilenameUtils.getName(url));
			document.setHash(response.toString());
			document.setContentType(MimeTypeConstants.getMimeType(FilenameUtils.getExtension(url).toLowerCase()));
			document.setUrlToDocument(url);
			documents.add(document);
		}

		// Set the request document
		request.setDocumentsToBeSigned(documents);

		try {
			// Request to MSS the signature of complex document mode
			// asynchronous client-server
			System.out.println("Requesting signature of document complex...");

			// Send the requet
			BatchSignatureComplexDocumentRespType signatureResp = signaturePortType.batchSignatureComplexDocument(request);
			if (signatureResp == null) {
				System.err.println("Could not get the signature response. Check your WS-Signer configuration.");
				return;
			}
			System.out.println("Signature requested. The transaction id is " + signatureResp.getTransactionId() + ".");
			System.out.println();
			System.out.println();
			System.out.println();
			BatchSignatureTIDsRespType signatureStatusResp = null;
			if (signatureResp.getStatus().getStatusCode() == Status.REQUEST_OK.getCode()) {
				SignatureStatusReqType signatureStatusReq = new SignatureStatusReqType();
				signatureStatusReq.setTransactionId(signatureResp.getTransactionId());

				// Check the request of the signature
				// Stop when the transaction is completed
				do {
					System.out.println("Waiting signature...");
					signatureStatusResp = signaturePortType.batchSignatureTIDsStatus(signatureStatusReq);
					Thread.sleep(2 * 1000); // Stop for 2 seconds

				} while (signatureStatusResp.getStatus().getStatusCode() == Status.TRANSACTION_IN_PROGRESS.getCode());
			}

			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("Status " + signatureStatusResp.getStatus().getStatusCode() + " " + signatureStatusResp.getStatus().getStatusDetail());
			signaturesStatus = signatureStatusResp.getDocumentSignatureStatus();
			transactionId = signatureResp.getTransactionId();

		} catch (ICPMException e) {
			System.err.println("Error " + e.getFaultInfo().getStatusCode() + " " + e.getFaultInfo().getStatusDetail());
		}
		// saves signature
		if (request.getSignatureStandard() == SignatureStandardType.CADES) {
			downloadSignatures(signaturesStatus, transactionId, CADES_EXTENSION);
		} else if (request.getSignatureStandard() == SignatureStandardType.XADES) {
			downloadSignatures(signaturesStatus, transactionId, XADES_EXTENSION);
		} else if (request.getSignatureStandard() == SignatureStandardType.ADOBEPDF) {
			downloadSignatures(signaturesStatus, transactionId, PDF_EXTENSION);
		}
	}

	private static void downloadSignatures(List<DocumentSignatureStatusInfoType> signaturesStatus, long transactionId, String signExtension) throws IOException {
		File signatureFile = new File("signature-" + transactionId + "." + signExtension);
		try {
			int i = 0;
			for (DocumentSignatureStatusInfoType document : signaturesStatus) {
				InputStream signedDocumentIn = null;
				FileOutputStream output = null;
				try {
					output = new FileOutputStream(signatureFile);
					if (document.getStatus().getStatusCode() != Status.USER_CANCELED.getCode()) {
						signedDocumentIn = new URL(String.format(downloadUrl, String.valueOf(document.getTransactionId()))).openStream();
						IOUtils.copy(signedDocumentIn, output);
					} else {
						i++;
					}
				} finally {
					IOUtils.closeQuietly(signedDocumentIn);
					IOUtils.closeQuietly(output);
				}
			}
			if (i == signaturesStatus.size()) {
				signatureFile.delete();
				System.out.println("\n####### Signature Rejected #######");
			} else {
				System.out.println("\n####### FILES GENERATED #######");
				System.out.println("\tsignature-" + transactionId + "." + signExtension);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
