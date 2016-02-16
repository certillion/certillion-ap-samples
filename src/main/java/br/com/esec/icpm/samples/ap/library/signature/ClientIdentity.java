package br.com.esec.icpm.samples.ap.library.signature;

import br.com.esec.icpm.libs.Certillion;

public class ClientIdentity {

	public static void config() {
		Certillion
			.config()
//			.useTestServer()
			.useProductionServer()
			.keystorePath("C:/Users/Tales Porto/Downloads/soluti.CERTILLION.COM.2015.pfx")
			.keystoreType("PKCS12")
			.keyAlias("e-sec tecnologia em seguranca de dados s/a:03242841000101")
			.keystorePassword("@dm03574(40D(u3R014")
//			.truststorePath("C:/Users/Tales Porto/Downloads/empty-truststore")
			.done();
	}
	
}