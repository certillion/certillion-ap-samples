package br.com.esec.icpm.sample.ap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
import br.com.esec.icpm.mss.ws.DocumentSignatureStatusInfoType;
import br.com.esec.icpm.mss.ws.HashDocumentInfoType;
import br.com.esec.icpm.mss.ws.MessagingModeType;
import br.com.esec.icpm.mss.ws.SignaturePortType;
import br.com.esec.icpm.mss.ws.SignatureStandardType;
import br.com.esec.icpm.mss.ws.SignatureStatusReqType;
import br.com.esec.icpm.sample.ap.core.WsSignerAddress;
import br.com.esec.icpm.sample.util.DocumentLinks;
import br.com.esec.icpm.sample.util.ImportDocs;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.MobileUserType;

@SuppressWarnings("restriction")
public class BatchSignatureAsynchronousApUtilsSample {
	private static final String wsUri = WsSignerAddress.get()
			+ "/mss/restAp/uploadDocument";
	private static List<String> urls = new ArrayList<String>();
	private static String uniqueIdentifier = null;
	private static String dataToBeDisplayed = null;
	private static String standard = null;
	private static String pathDocs = null;

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			System.out
					.println("BatchSignatureSynchronousSample need this params: [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [pathDocs]");
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
			} else if (i == 3) {
				pathDocs = args[i];
			}
		}

		urls = ImportDocs.getListDocs(pathDocs);

		List<HashDocumentInfoType> documents = new ArrayList<HashDocumentInfoType>();

		// Get the signaturePort
		String endpointAddr = WsSignerAddress.get() + "/mss/serviceAp.wsdl";

		System.out
				.println("Connecting to Signature Service... " + endpointAddr);

		System.out.println();
		System.out.println();
		System.out.println();

		URL urlws = new URL(endpointAddr);
		Service signatureService = Service.create(urlws,
				SignaturePortType.QNAME);
		SignaturePortType signaturePortType = signatureService
				.getPort(SignaturePortType.class);

		// Set the mobileUser
		MobileUserType mobileUser = new MobileUserType();
		mobileUser.setUniqueIdentifier(uniqueIdentifier);

		// Create the batch request
		BatchSignatureComplexDocumentReqType request = new BatchSignatureComplexDocumentReqType();
		request.setMobileUser(mobileUser);
		request.setMessagingMode(MessagingModeType.ASYNCH_SERVER_SERVER);
		request.setDataToBeDisplayed(dataToBeDisplayed);
		request.setSignatureStandard(SignatureStandardType.valueOf(standard));
		request.setTestMode(true);

		// upload files from each url
		for (String url : urls) {
			StringBuffer response = upload(url);

			// Get hash from uploaded file, create document and save the
			// document in the list
			HashDocumentInfoType document = new HashDocumentInfoType();
			document.setDocumentName(FilenameUtils.getName(url));
			document.setHash(response.toString());
			document.setContentType(MimeTypeConstants.getMimeType(FilenameUtils
					.getExtension(url).toLowerCase()));
			document.setUrlToDocument(url);
			documents.add(document);

		}

		// Set the request document
		request.setDocumentsToBeSigned(documents);

		// Obtem resposta com o TID do batch
		BatchSignatureComplexDocumentRespType signatureResp = signaturePortType
				.batchSignatureComplexDocument(request);

		// Recupera a lista de TIDs de cada documento enviado.
		BatchSignatureTIDsRespType signatureStatusResp = null;
		List<DocumentSignatureStatusInfoType> docInfos = null;
		if (signatureResp.getStatus().getStatusCode() == Status.REQUEST_OK
				.getCode()) {
			SignatureStatusReqType signatureStatusReq = new SignatureStatusReqType();
			signatureStatusReq.setTransactionId(signatureResp
					.getTransactionId());

			// Check the request of the signature
			// Stop when the doc status isn't null
			do {
				System.out.println("Waiting doc signature infos...");
				Thread.sleep(2 * 1000); // Stop for 2 seconds
				signatureStatusResp = signaturePortType
						.batchSignatureTIDsStatus(signatureStatusReq);
				docInfos = signatureStatusResp.getDocumentSignatureStatus();

			} while (docInfos == null);
		}

		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("Status Request: "
				+ signatureResp.getStatus().getStatusCode() + " "
				+ signatureResp.getStatus().getStatusMessage());
		System.out.println("Batch TransactionID: "
				+ signatureResp.getTransactionId());

		// Salvar links dos documentos originais com os respectivos TIDs.
		DocumentLinks docLinks = DocumentLinks.getInstance(pathDocs,
				signatureResp.getTransactionId());

		docLinks.store(signatureStatusResp);

	}

	private static StringBuffer upload(String url)
			throws MalformedURLException, IOException, ProtocolException {
		System.out.println("Sending document '" + url + "'... ");

		URL fileUrl = new URL(url);
		InputStream sourceIn = fileUrl.openStream();

		URL obj = new URL(wsUri);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);

		con.setRequestMethod("POST");

		con.setRequestProperty("Content-type", "application/octet-stream");

		OutputStream out = con.getOutputStream();
		IOUtils.copy(sourceIn, out);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}
}