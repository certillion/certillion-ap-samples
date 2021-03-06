import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.certillion.api.BatchInfoType;
import com.certillion.api.BatchSignatureReqTypeV2;
import com.certillion.api.BatchSignatureRespTypeV2;
import com.certillion.api.CertificateFiltersType;
import com.certillion.api.CertificateInfoType;
import com.certillion.api.DocumentSignatureStatusInfoTypeV3;
import com.certillion.api.HsmCertificateFilterType;
import com.certillion.api.ICPMException;
import com.certillion.api.MessagingModeType;
import com.certillion.api.MobileStatus;
import com.certillion.api.SignatureInfoTypeV2;
import com.certillion.api.SignaturePortTypeV2;
import com.certillion.api.SignatureStandardType;
import com.certillion.api.SignatureStatusReqType;
import com.certillion.api.StatusRespType;
import com.certillion.api.StatusType;
import com.certillion.api.StatusTypeV2;
import com.certillion.api.UserType;
import com.certillion.utils.CertillionStatus;

/**
 * Exemplo de assinatura de documentos, vers�o 2.
 * 
 * NOTA: ESTE EXEMPLO TEM COMO OBJETIVO PRINCIPAL A DID�TICA, PARA QUE O 
 * 		 DESENVOLVEDOR ENTENDA O FUNCIONAMENTO DE MODO R�PIDO E EFETIVO. ANTES
 *       DE COPIAR TRECHOS DESSE C�DIGO, AVALIE O IMPACTO NA ARQUITETURA DO
 *       SEU SISTEMA (EX: J2EE), POIS ALGUMAS ABORDAGENS PODEM AFETAR DESEMPENHO
 *       (EX: Thread.sleep) OU SER INCOMPAT�VEIS (EX: LOG).  
 * 
 * @author CertillionTeam
 */
public class BatchSignatureSample {

	private static String REST_URL;
	private static String WSDL_URL;
	private static String FINGERPRINT;
	
	private static boolean DUMP_XML;
	
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("use: java BatchSignatureSample [-hsm|-hsmAuth] mail-id textnote file1 [file2] ... [fileN]");
			System.out.println("where:");
			System.out.println("\t-hsm: use key stored in hsm");
			System.out.println("\t-hsmAuth: use key stored in hsm with automatic signature (no pin required) - must be authorized at 1st use");
			System.out.println("ex:  java BatchSignatureV2Sample -hsm user@gmail.com \"Segue docs para assinatura\" doc1.pdf doc2.jpg doc3.xls");
			System.exit(1);
		}

		loadURLs();

		//Do you want to see the generated soap messages?
		com.certillion.utils.WSUtils.dumpToConsole(DUMP_XML);
		
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
		
		String userId = args[0+shift];
		String note = args[1+shift];
		
		shift += 2;
		
		File files[] = new File[args.length - shift];
		String hashes[] = new String[files.length];
		
		for (int i = shift; i < args.length; i++)
			files[i-shift] = new File(args[i]);
		
		// uploading documents through Rest protocol
		for (int i = 0; i < files.length; i++)
			hashes[i] = uploadFile(files[i], REST_URL + "/uploadDocument");
			
		// connecting to endpoint
		Service signatureService = Service.create(new URL(WSDL_URL), new QName("http://esec.com.br/mss/ap", "SignatureService"));
		SignaturePortTypeV2 endpoint = signatureService.getPort(SignaturePortTypeV2.class);
		
		SignatureStandardType standard = getStandardFromExtension(files[0].getAbsolutePath());
		
		System.out.println("Standard = \"" + standard + "\"");
		
		// mount the "batch-signature" request
		System.out.println("Sending request to " + userId);
		BatchSignatureReqTypeV2 batchSignatureReq = new BatchSignatureReqTypeV2();
		
		batchSignatureReq.setMessagingMode(MessagingModeType.ASYNCH_CLIENT_SERVER);
		batchSignatureReq.setTimeOut(new BigInteger("120"));
		batchSignatureReq.setDataToBeDisplayed(note);
		batchSignatureReq.setSignatureStandard(standard);
		//batchSignatureReq.setSignatureStandard(SignatureStandardType.PADES);
		
		boolean usingICPBRASILCertificates = true;
		
		batchSignatureReq.setTestMode(!usingICPBRASILCertificates);
		
		if (useHSM) {
			System.out.println("Request to sign in HSM mode");
			HsmCertificateFilterType hsmFilter = new HsmCertificateFilterType();
			CertificateFiltersType filter = new CertificateFiltersType();
			
			hsmFilter.setValue(true);
			filter.getTrustChainOrOwnerCertificateOrAlgorithm().add(hsmFilter);
			batchSignatureReq.setCertificateFilters(filter);
		}
		
		if (useAuthentic) {
			System.out.println("Requesting authorization for automatic signature");
			batchSignatureReq.setFingerprint(FINGERPRINT);
		}
		
		// set the target user
		UserType user = new UserType();
		
		user.setIdentifier(userId);
		batchSignatureReq.setUser(user);
		
		List<BatchInfoType> documents = batchSignatureReq.getDocumentsToBeSigned();
		
		for (int i = 0; i < hashes.length; i++) {
			if (hashes[i] != null) {
				BatchInfoType document = new BatchInfoType();
				
				document.setDocumentName(files[i].getName());
				document.setContentType(getContentTypeFromExtension(files[i].getName()));
				document.setHash(hashes[i]);
				/*
				SignatureStandardOptionsType signatureStandardOptions = new SignatureStandardOptionsType();
				List<Object> options = signatureStandardOptions.getElementsIdOrAttributeIdNameOrElementsName();
				
				
				// ---------- BEGIN ADOBEPDF/PADES ----------
				// how to set a visible signature on PAdES standard
				SignatureTextPadesOptionType text = new SignatureTextPadesOptionType();
				SignaturePagePadesOptionType page = new SignaturePagePadesOptionType();
				SignaturePosXPadesOptionType posX = new SignaturePosXPadesOptionType();
				SignaturePosYPadesOptionType posY = new SignaturePosYPadesOptionType();
				
				HeightPadesOptionType height = new HeightPadesOptionType();
				WidthPadesOptionType width = new WidthPadesOptionType();
				FontSizePadesOptionType size = new FontSizePadesOptionType();
				
				text.setValue("Signed by ");
				page.setValue(1);
				posX.setValue(100);
				posY.setValue(100);
				
				width.setValue(480);
				height.setValue(80);
				size.setValue(24);
				
				options.add(text);
				options.add(page);
				options.add(posX);
				options.add(posY);
				
				options.add(height);
				options.add(width);
				options.add(size);
				// ---------- END ADOBEPDF/PADES ----------
				
				// ---------- BEGIN XMLDSIG (NFe -> ENVELOPED) ----------
				AttributeIdNameXmldsigOptionType attributeIdName = new AttributeIdNameXmldsigOptionType();
		        ElementsNameXmldsigOptionType elementsName = new ElementsNameXmldsigOptionType();
		        AddSubjectNameXmldsigOptionType addSubjectName = new AddSubjectNameXmldsigOptionType();
		        AddKeyValXmldsigOptionType addKeyVal = new AddKeyValXmldsigOptionType();
			
		        elementsName.getValue().add("infNFe");
		        attributeIdName.setValue("Id");
		        addSubjectName.setValue(false);
		        addKeyVal.setValue(true);
		        
		        options.add(elementsName);
		        options.add(attributeIdName);
		        options.add(addSubjectName);
		        options.add(addKeyVal);
		        // ---------- END XMLDSIG ----------
				
				document.setSignatureStandardOptions(signatureStandardOptions);
				*/
				documents.add(document);
			}
		}
		BatchSignatureRespTypeV2 batchSignatureResp = null;
		
		try {
			batchSignatureResp = endpoint.batchSignature(batchSignatureReq);
		}
		catch (ICPMException e) {
			StatusType exception = e.getFaultInfo();
			
			System.out.println("Exception in signature request: CODE[" 
							+ exception.getStatusCode() + "], DETAIL: " 
							+ exception.getStatusDetail() + ", MESSAGE: "
							+ exception.getStatusMessage());
			
			throw e;
		}
		
		CertillionStatus batchSignatureStatus = CertillionStatus.valueOf(batchSignatureResp.getStatus().getStatusMessage());
		
		if (batchSignatureStatus != CertillionStatus.REQUEST_OK) {
			System.out.println("Certillion rejected the signature request: " + batchSignatureResp.getStatus());
		}
		else {
			// return result
			long transactionId = batchSignatureResp.getTransactionId();
			
			System.out.println("Request sent, transaction ID is: " + transactionId);
			
			if (useHSM && !useAuthentic)
				System.out.println("\tverification code: " + batchSignatureResp.getVerificationCode());
			
			// mount the "check-transaction" request
			SignatureStatusReqType signatureStatusReqType = new SignatureStatusReqType();
			
			signatureStatusReqType.setTransactionId(transactionId);
			
			// server keep returning "TRANSACTION_IN_PROGRESS" until the user responds
			StatusRespType statusResp = null;
			CertillionStatus statusRespValue = null;
			MobileStatus mobileStatus = null;
			
			System.out.println("Waiting signature from user");
			do {
				try {
					// TODO REVIEW: DON'T USE THIS ON PRODUCTION OR YOU'LL HAVE PERFORMANCE ISSUES!!!
					Thread.sleep(10000); // wait for at least 10 seconds or the server will mark you as flood
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				statusResp = endpoint.signatureStatusQuery(signatureStatusReqType);
				
				StatusTypeV2 statusType = statusResp.getStatus();
				int statusCode = statusType.getStatusCode();
				String statusDetail = statusType.getStatusDetail();
				String statusMessage = statusType.getStatusMessage();
				
				mobileStatus = statusType.getMobileStatus();
				
				System.out.println("\tmobileStatus: " + (mobileStatus == null ? "null" : mobileStatus.toString()));
				System.out.println("\tstatusCode: " + statusCode);
				System.out.println("\tstatusDetail: " + statusDetail);
				System.out.println("\tstatusMessage: " + statusMessage);
				System.out.println("\tstatusResp: " + statusResp.getStatus().getStatusMessage());
				System.out.println();
				
				statusRespValue = CertillionStatus.valueOf(statusResp.getStatus().getStatusMessage());
			//} while (mobileStatus != MobileStatus.DONE);
			} while (statusRespValue == CertillionStatus.TRANSACTION_IN_PROGRESS);
			
			if (statusRespValue != CertillionStatus.REQUEST_OK) {
				System.out.println("Error receiving the response, the status is " + statusRespValue);
			}
			else {
				System.out.println("Signature received, the status is " + statusRespValue);
				
				List<DocumentSignatureStatusInfoTypeV3> documentInfos = statusResp.getDocumentSignatureStatus();
				File path = new File(System.getProperty("user.dir"), "signed");
				
				path.mkdirs();
				
				if (!path.exists()) {
					System.out.println("Couldn't create dir \"" + path.getAbsolutePath() + "\": aborting download");
				}
				else {
					System.out.println("Saving files in \"" + path.getAbsolutePath() + "\"");
					
					for (DocumentSignatureStatusInfoTypeV3 document : documentInfos) {
						long documentId = document.getTransactionId();
						String documentName = document.getDocumentName();
						CertillionStatus documentStatus = CertillionStatus.from(document.getStatus());
						
						System.out.println("\t- Transaction [" + documentId + "], document \"" + documentName + "\", status: " + documentStatus);
						
						if (documentStatus == CertillionStatus.SIGNATURE_VALID) {
							SignatureInfoTypeV2 signatureInfo = document.getSignatureInfo();
							
							if (signatureInfo != null) {
								System.out.println("\t\tLegal identifier : \"" + signatureInfo.getLegalIdentifier() + "\"");
								System.out.println("\t\tPolicy id: \"" + signatureInfo.getPolicyId() + "\"");
								System.out.println("\t\tPolicy url: \"" + signatureInfo.getPolicyUrl() + "\"");
								System.out.println("\t\tSigning time: " + signatureInfo.getSigningTime());
								
								CertificateInfoType certificateInfo = signatureInfo.getSignerCertificate();
								String subjectDN = certificateInfo.getSubjectDn();
								
								System.out.println("\t\tSigner: " + subjectDN.substring(subjectDN.indexOf("CN=") + 3));
							}
							
							byte signature[] = document.getSignature();
							
							// writing signature detached from original file (aways returned)  
							OutputStream sigfile = new FileOutputStream(new File(path, documentName + ".p7s"));
							
							sigfile.write(signature);
							sigfile.flush();
							sigfile.close();
							
							// [OPTIONAL] requesting, downloading and writing signature with original file attached 
							downloadFile(documentName + ".p7m", documentId, path, REST_URL + "/document/signed/");
						}
					}
				}
			}
		}
	}

	/**
	 * Upload the file to the server through rest protocol.
	 * 
	 * @param file the file to upload
	 * @param url the server address to upload the file
	 * @return the file's hashcode or null if the file could not be uploaded
	 * @throws Exception if any io exception is thrown
	 */
	private static String uploadFile(File file, String url) throws Exception {
		String filename = file.getAbsolutePath();
		
		if (!file.exists()) {
			System.out.println("File \"" + filename + "\" not found");
			return null;
		}
		if (file.length() == 0) {
			System.out.println("Empty file \"" + filename + "\"");
			return null;
		}
		
		System.out.print("Uploading file \"" + filename + "\": ");
		URL resource = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) resource.openConnection();
		
		connection.setDoInput(true);
		connection.setDoOutput(true);
		//connection.setUseCaches(false);
		//connection.setDefaultUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/octet-stream");
		
		connection.connect();
		
		InputStream is = new FileInputStream(file); 
		OutputStream os = connection.getOutputStream();
		byte buffer[] = new byte[1024];
		long length = file.length();
		long total = 0;
		int read;
		int percent1;
		int percent2 = 0;
		
		while ((read = is.read(buffer)) > 0) {
			// enviando arquivo
			os.write(buffer, 0, read);
			
			// imprimindo percentual de upload
			total += read;
			percent1 = (int) ((total * 100)/ length);
			for (; percent2 < percent1; percent2++) {
				if (percent2 % 10 == 0)
					System.out.print(percent2 + "%");
				else
					System.out.print(".");
			}
		}
		System.out.println("100%");
		is.close();
		
		if (connection.getResponseCode() != 200) {
			StatusType statusType = CertillionStatus.INTERNAL_ERROR.toStatusType();
			throw new Exception("Certillion rejected file upload: CODE[" 
							+ statusType.getStatusCode() + "], DETAIL: " 
							+ statusType.getStatusDetail() + ", MESSAGE: "
							+ statusType.getStatusMessage());
		}
		
		// get file hash
		InputStream connectionIS = connection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		
		while ((read = connectionIS.read(buffer)) > -1) {
			baos.write(buffer, 0, read);
		}
		
		connection.disconnect();
		
		String hash = new String(baos.toByteArray());
		
		System.out.println("\tdocument hash = " + hash + " (base 32 encoding)");
		
		return hash;
	}

	/**
	 * Download the file from the server using rest protocol.
	 * 
	 * @param fileName the name of the file to be downloaded
	 * @param transactionId the transactionId of the signed file
	 * @param path the path to store the downloaded file
	 * @param restUrl the url to download the file
	 */
	private static void downloadFile(String fileName, long transactionId, File path, String restUrl) throws Exception {
		// mount the "download-signature" request
		URL resource = new URL(restUrl + transactionId);
		HttpURLConnection connection = (HttpURLConnection) resource.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(false);
		connection.setRequestMethod("GET");

		// send the "download-signature" request
		if (connection.getResponseCode() != 200) {
			throw new Exception("\t\tServer rejected the file download: " + CertillionStatus.INTERNAL_ERROR.toStatusType());
		}
		
		System.out.print("\t\tDownloading file: ");
		
		// parse the "download-signature" response
		InputStream is = connection.getInputStream();
		OutputStream os = new FileOutputStream(new File(path, fileName));
		byte buffer[] = new byte[1024];
		long length = connection.getContentLength();
		long total = 0;
		int read;
		int percent1;
		int percent2 = 0;
		
		// baixando arquivo
		while ((read = is.read(buffer)) > 0) {
			os.write(buffer, 0, read);
			
			// imprimindo percentual de download
			total += read;
			percent1 = (int) ((total * 100)/ length);
			for (; percent2 < percent1; percent2++) {
				if (percent2 % 10 == 0)
					System.out.print(percent2 + "%");
				else
					System.out.print(".");
			}
		}
		System.out.println("100%");
		
		os.flush();
		os.close();
		
		connection.disconnect();
	}

	public static SignatureStandardType getStandardFromExtension(String filePath) {
		String lc = filePath.toLowerCase();
		
		if (lc.endsWith(".pdf")) {
			return SignatureStandardType.ADOBEPDF;
		} else if (lc.endsWith(".xml")) {
			return SignatureStandardType.XMLDSIG_ENVELOPED;
		} else {
			return SignatureStandardType.CADES;
		}
	}
	
	public static String getContentTypeFromExtension(String filePath) {
		if (filePath.endsWith(".pdf")) {
			return "application/pdf";
		} else if (filePath.endsWith(".xml")) {
			return "application/xml";
		} else {
			return "application/octet-stream";
		}
	}
	
	private static void loadURLs() {
		Properties prop = new Properties();
		File file = new File("certillion-urls.txt");
		boolean loaded = false;
		
		try {
			if (!file.exists())
				System.out.println(file.getAbsolutePath() + " does not exist. Using default urls.");
			else if (!file.isFile())
				System.out.println(file.getAbsolutePath() + " is not a file. Using default urls.");
			else {
				// load a properties file
				prop.load(new FileInputStream(file));
				loaded = true;
			}
		}
		catch (IOException ex) {
			System.out.println("Error loading urls from " + file.getName() + ". Using default urls.");
		}
		
		//To use your own ws-signer
		//String BASE_DEFAULT = "http://localhost:8280";
		
		// To use e-Sec's development server (must require access)
		String BASE_DEFAULT = "http://labs.certillion.com";
		
		String REST_DEFAULT = "/mss/restful/applicationProvider";
		String WSDL_DEFAULT = "/mss/SignatureService/SignatureEndpointBeanV2.wsdl";
		
		boolean DUMP_DEFAULT = true;
		
		String base = null;
		String rest = null;
		String wsdl = null;
		boolean dump = false;
		String fingerprint = null;

		String BASE_PROPERTY_NAME = "BASE";
		String REST_PROPERTY_NAME = "REST";
		String WSDL_PROPERTY_NAME = "WSDL";
		String DUMP_PROPERTY_NAME = "DUMP";
		String FING_PROPERTY_NAME = "FING";
		
		if (loaded) {
			base = prop.getProperty(BASE_PROPERTY_NAME);
			if (base == null) {
				System.out.println("\"" + BASE_PROPERTY_NAME + "\" property not found. Using default \"" + BASE_DEFAULT + "\".");
				base = BASE_DEFAULT;
			}
			
			rest = prop.getProperty(REST_PROPERTY_NAME);
			if (rest == null) {
				System.out.println("\"" + REST_PROPERTY_NAME + "\" property not found. Using default \"" + REST_DEFAULT + "\".");
				rest = REST_DEFAULT;
			}
			
			wsdl = prop.getProperty(WSDL_PROPERTY_NAME);
			if (wsdl == null) {
				System.out.println("\"" + WSDL_PROPERTY_NAME + "\" property not found. Using default \"" + WSDL_DEFAULT + "\".");
				wsdl = WSDL_DEFAULT;
			}
			
			String dumpStr = prop.getProperty(DUMP_PROPERTY_NAME);
			if (dumpStr == null) {
				System.out.println("\"" + DUMP_PROPERTY_NAME + "\" property not found. Using default \"" + DUMP_DEFAULT + "\".");
				dump = DUMP_DEFAULT;
			}
			else
				dump = Boolean.parseBoolean(dumpStr);
			
			fingerprint = prop.getProperty(FING_PROPERTY_NAME);
		}
		else {
			base = BASE_DEFAULT;
			rest = REST_DEFAULT;
			wsdl = WSDL_DEFAULT;
			dump = DUMP_DEFAULT;
		}
		
		System.out.println("\t" + BASE_PROPERTY_NAME + " = \"" + base + "\"");
		System.out.println("\t" + REST_PROPERTY_NAME + " = \"" + rest + "\"");
		System.out.println("\t" + WSDL_PROPERTY_NAME + " = \"" + wsdl + "\"");
		System.out.println("\t" + DUMP_PROPERTY_NAME + " = \"" + dump + "\"");
		System.out.println("\t" + FING_PROPERTY_NAME + " = \"" + (fingerprint != null ? fingerprint : "") + "\" (must use \"-hsmauth\" to work)");
		
		REST_URL = base + rest;
		WSDL_URL = base + wsdl;
		DUMP_XML = dump;
		FINGERPRINT = fingerprint;
		//FINGERPRINT = "UseToken=false;OtpValue=123456";
	}
}
