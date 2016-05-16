package br.com.esec.icpm.samples.ap;

import javax.xml.namespace.QName;

/**
 * Constants shared by more than one class.
 */
public final class Constants {

	// app name
	public static final String APP_NAME = "certillion-ap-samples";

	// commands
	public static final String COMMAND_SIGN_TEXT = "sign-text";
	public static final String COMMAND_SIGN_DOCS = "sign-documents";
	public static final String COMMAND_SIGN_HSM = "sign-hsm";
	public static final String COMMAND_VALIDATE = "validate-signature";
	public static final String COMMAND_ATTACH = "attach-signature";

	// hidden commands
	public static final String COMMAND_ONLY_UPLOAD = "only-upload-documents";
	public static final String COMMAND_ONLY_SIGN = "only-sign-documents";
	public static final String COMMAND_ONLY_DOWNLOAD_ATTACHED = "only-download-attached";
	public static final String COMMAND_ONLY_DOWNLOAD_DETACHED = "only-download-detached";

	// web service url strings
	public static final String BASE_URL = "http://labs.certillion.com";
	public static final String REST_URL = BASE_URL + "/mss/restful/applicationProvider";
	public static final String WSDL_URL = BASE_URL + "/mss/SignatureService/SignatureEndpointBean.wsdl";

	// web service qualified name
	public static final QName SERVICE_QNAME = new QName("http://esec.com.br/mss/ap", "SignatureService");

}
