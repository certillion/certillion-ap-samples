package br.com.esec.icpm.samples.ap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
	public static final String COMMAND_SIGN_DOCS_NOTIFICATION = "sign-documents-notification";
	public static final String COMMAND_SIGN_HSM = "sign-hsm";
	public static final String COMMAND_XMLDSIG = "sign-xmldsig";
	public static final String COMMAND_VALIDATE = "validate-signature";
	public static final String COMMAND_VALIDATE_PDF = "validate-signature-pdf";
	public static final String COMMAND_ATTACH = "attach-signature";

	// hidden commands
	public static final String COMMAND_ONLY_UPLOAD = "only-upload-documents";
	public static final String COMMAND_ONLY_SIGN = "only-sign-documents";
	public static final String COMMAND_ONLY_DOWNLOAD_ATTACHED = "only-download-attached";
	public static final String COMMAND_ONLY_DOWNLOAD_DETACHED = "only-download-detached";

	// web service url strings
	//public static final String BASE_URL = "http://192.168.2.22:8280";
	public static final String BASE_URL;
	public static final String REST_URL;
	public static final String WSDL_URL;

	// web service qualified name
	public static final QName SERVICE_QNAME = new QName("http://esec.com.br/mss/ap", "SignatureService");
	
	static {
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir, "urls.txt");
		Properties properties = new Properties();
		
		if (!file.exists())
			System.out.println("File \"" + file.getAbsolutePath() + "\" not found. No alternate urls loaded.");
		else {
			System.out.println("File \"" + file.getAbsolutePath() + "\" found. Loading alternate urls.");
			try {
				FileInputStream is = new FileInputStream(file);
				
				properties.load(is);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		BASE_URL = getValue(properties, "BASE_URL", "http://192.168.2.22:8280");
		System.out.println("BASE_URL=" + BASE_URL);
		REST_URL = BASE_URL + getValue(properties, "REST_URL", "/mss/restAp_dev");
		System.out.println("REST_URL=" + REST_URL);
		WSDL_URL = BASE_URL + getValue(properties, "WSDL_URL", "/mss/serviceAp_dev.wsdl");
		System.out.println("WSDL_URL=" + WSDL_URL);
	}

	private static String getValue(Properties properties, String key, String defaultValue) {
		String value = properties.getProperty(key);
		
		if (value != null && value.trim().length() > 0)
			return value.trim();
		return defaultValue;
	}
}
