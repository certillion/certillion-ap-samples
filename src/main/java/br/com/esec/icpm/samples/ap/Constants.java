package br.com.esec.icpm.samples.ap;

import javax.xml.namespace.QName;

/**
 * Constants shared by more than one class.
 */
public class Constants {

	// app name
	public static final String APP_NAME = "certillion-ap-samples";

	// commands
	public static final String COMMAND_SIGN_TEXT = "sign-text";
	public static final String COMMAND_SIGN_DOCS = "sign-documents";
	public static final String COMMAND_VALIDATE = "validate-signature";
	public static final String COMMAND_ATTACH = "attach-signature";

	// hidden commands
	public static final String COMMAND_ONLY_UPLOAD = "only-upload-documents";
	public static final String COMMAND_ONLY_SIGN = "only-sign-documents";
	public static final String COMMAND_ONLY_DOWNLOAD = "only-download-documents";

	// web service url strings
	public static final String BASE_URL = "http://localhost:8280";
	public static final String DOWNLOAD_URL = BASE_URL + "/mss/restAp/document/signed/";
	public static final String UPLOAD_URL = BASE_URL + "/mss/restAp/document";
	public static final String WSDL_URL = BASE_URL + "/mss/serviceAp.wsdl";

	// web service qualified name
	public static final QName SERVICE_QNAME = new QName("http://esec.com.br/mss/ap", "SignatureService");

}
