package br.com.esec.icpm.sample.ap.library.signature;

import br.com.esec.icpm.libs.Certillion;

public class ClientIdentity {

	public static void config() {
		Certillion
			.config()
			.useTestHost()
//			.keystorePath(System.getProperty("certillion.keystore", "client.jks"))
//			.keystoreType(System.getProperty("certillion.storetype", "JKS"))
//			.keyAlias(System.getProperty("certillion.alias"))
//			.keystorePassword(System.getProperty("certillion.storepass"))
//			.truststorePath(System.getProperty("certillion.truststore"))
//			.truststoreType(System.getProperty("certillion.truststoretype"))
//			.truststorePassword(System.getProperty("certillion.truststorepass"))
			.done();
	}
	
}