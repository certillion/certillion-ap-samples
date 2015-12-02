package br.com.esec.icpm.sample.ap;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import br.com.esec.icpm.libs.signature.helper.MimeTypeConstants;
import br.com.esec.icpm.mss.ws.BatchSignatureComplexDocumentReqType;
import br.com.esec.icpm.mss.ws.BatchSignatureComplexDocumentRespType;
import br.com.esec.icpm.mss.ws.BatchSignatureTIDsRespType;
import br.com.esec.icpm.mss.ws.DocumentSignatureStatusInfoType;
import br.com.esec.icpm.mss.ws.FindSignatureReqType;
import br.com.esec.icpm.mss.ws.HashDocumentInfoType;
import br.com.esec.icpm.mss.ws.MessagingModeType;
import br.com.esec.icpm.mss.ws.SignaturePortType;
import br.com.esec.icpm.mss.ws.SignatureStandardType;
import br.com.esec.icpm.mss.ws.SignatureStatusReqType;
import br.com.esec.icpm.mss.ws.SignatureStatusRespType;
import br.com.esec.icpm.sample.ap.core.WsSignerAddress;
import br.com.esec.icpm.sample.util.ImportDocs;
import br.com.esec.icpm.server.factory.Status;
import br.com.esec.icpm.server.ws.ICPMException;
import br.com.esec.icpm.server.ws.MobileUserType;

@Deprecated
public class BatchSignatureAsynchronousApUtilsPollSample {
	private static final String wsUri = WsSignerAddress.get()
			+ "/mss/restAp/uploadDocument";
	private static List<String> urls = new ArrayList<String>();
	private static String uniqueIdentifier = null;
	private static String dataToBeDisplayed = null;
	private static String standard = null;
	private static String docsPath = null;

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			System.out
					.println("BatchSignatureAsynchronousApUtilsPollSample need this params: [uniqueIdentifier] [dataToBeDisplayed] [signatureStandard] [docsPath]");
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
				docsPath = args[i];
			}
		}

		urls = ImportDocs.getListDocs(docsPath);
		HashMap<String, String> docsMap = ImportDocs.getHashDocs(docsPath);

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

		try {
			// Request to MSS the signature of complex document mode
			// asynchronous client-server
			System.out.println("Requesting signature of document complex...");

			// Send the requet
			BatchSignatureComplexDocumentRespType signatureResp = signaturePortType
					.batchSignatureComplexDocument(request);

			if (signatureResp == null) {
				System.err
						.println("Could not get the signature response. Check your WS-Signer configuration.");
				return;
			}

			System.out.println("Signature requested. The transaction id is "
					+ signatureResp.getTransactionId() + ".");

			System.out.println();
			System.out.println();
			System.out.println();

			BatchSignatureTIDsRespType signatureStatusResp = null;
			if (signatureResp.getStatus().getStatusCode() == Status.REQUEST_OK
					.getCode()) {
				SignatureStatusReqType signatureStatusReq = new SignatureStatusReqType();
				signatureStatusReq.setTransactionId(signatureResp
						.getTransactionId());

				// Check the request of the signature
				// Stop when the transaction is completed
				do {
					System.out.println("Waiting signature...");
					signatureStatusResp = signaturePortType
							.batchSignatureTIDsStatus(signatureStatusReq);
					Thread.sleep(2 * 1000); // Stop for 2 seconds

				} while (signatureStatusResp.getStatus().getStatusCode() == Status.TRANSACTION_IN_PROGRESS
						.getCode());
			}

			System.out.println();
			System.out.println();
			System.out.println();

			System.out.println("Status "
					+ signatureStatusResp.getStatus().getStatusCode() + " "
					+ signatureStatusResp.getStatus().getStatusDetail());

			for (DocumentSignatureStatusInfoType documentSignature : signatureStatusResp
					.getDocumentSignatureStatus()) {
				checkSignatureStatus(docsMap, signaturePortType,
						documentSignature);
			}
		} catch (ICPMException e) {
			System.err.println("Error " + e.getFaultInfo().getStatusCode()
					+ " " + e.getFaultInfo().getStatusDetail());
		}
	}

	private static void checkSignatureStatus(HashMap<String, String> docsMap,
			SignaturePortType signaturePortType,
			DocumentSignatureStatusInfoType documentSignature)
			throws ICPMException, IOException, MalformedURLException {
		if (documentSignature.getStatus().getStatusCode() == Status.SIGNATURE_VALID
				.getCode()) {
			long transactionIdDocument = documentSignature.getTransactionId();

			FindSignatureReqType findSignatureRequest = new FindSignatureReqType(); // create
																					// finder
																					// signature
			findSignatureRequest.setAttached(false);
			findSignatureRequest.setTransactionId(transactionIdDocument);

			SignatureStatusRespType findSignatureResponse = signaturePortType
					.findSignature(findSignatureRequest); // create
															// finder
															// signature
															// response

			InputStream signatureInputStream = findSignatureResponse
					.getSignature().getInputStream();
			ByteArrayOutputStream signatureOutputStream = new ByteArrayOutputStream();
			IOUtils.copy(signatureInputStream, signatureOutputStream);
			String signatureBase64 = Base64
					.encodeBase64String(signatureOutputStream.toByteArray());

			URL url = new URL("http://localhost:8480/pdfs/signeds"); // connect
																		// to
																		// ap-utils
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();

			try {
				HttpPost httppost = new HttpPost(url.toString());

				MultipartEntity entity = new MultipartEntity();
				entity.addPart("signature-base64-encoded", new StringBody(
						signatureBase64));
				entity.addPart("transaction-id",
						new StringBody(String.valueOf(transactionIdDocument)));

				File docFile = new File(docsMap.get(
						documentSignature.getDocumentName()).replace("file:\\",
						""));
				entity.addPart("file", new FileBody(docFile));

				System.out
						.println(String
								.format("\n transaction-id: %d \n signature-base64-encoded: %s \n file: %s \n",
										transactionIdDocument, signatureBase64,
										docFile.getAbsolutePath()));

				httppost.setEntity(entity);

				HttpResponse httpResponse = httpClient.execute(httppost); // execute
																			// post

				Integer responseCode = httpResponse.getStatusLine()
						.getStatusCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream signedPdfInputStream = httpResponse.getEntity()
							.getContent();
					File outSignedFile = new File(docsPath
							+ "/docsAssinados/signed-"
							+ documentSignature.getDocumentName());
					OutputStream outPut = new FileOutputStream(outSignedFile);
					IOUtils.copy(signedPdfInputStream, outPut);
					System.out.println("Document signed-"
							+ documentSignature.getDocumentName()
							+ " created with sucess");
				} else {
					System.out.println(httpResponse.getStatusLine());
					httpResponse.getEntity().writeTo(System.out);

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				httpClient.close();
			}

		} else {
			System.out.println(documentSignature.getStatus().getStatusDetail());
		}
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