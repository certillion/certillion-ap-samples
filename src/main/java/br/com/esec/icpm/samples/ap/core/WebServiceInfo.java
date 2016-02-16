package br.com.esec.icpm.samples.ap.core;

import java.net.URL;

public class WebServiceInfo {

	private static final String BASE_URL = "http://localhost:8280";
	private static final String AP_SERVICE_URL = BASE_URL + "/mss/serviceAp.wsdl";
	private static final String UPLOAD_DOCUMENT_URL = BASE_URL + "/mss/restAp/document";
	private static final String DOWNLOAD_DOCUMENT_URL = BASE_URL + "/mss/restAp/document/signed/";

	public static String getBaseUrl() {
		return BASE_URL;
	}

	public static String getApServiceUrl() {
		return AP_SERVICE_URL;
	}

	public static String getUploadDocumentUrl() {
		return UPLOAD_DOCUMENT_URL;
	}

	public static String getDownloadDocumentUrl() {
		return DOWNLOAD_DOCUMENT_URL;
	}

}
