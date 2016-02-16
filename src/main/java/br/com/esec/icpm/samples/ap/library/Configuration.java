package br.com.esec.icpm.samples.ap.library;

import br.com.esec.icpm.libs.Certillion;

public class Configuration {

	public static void config() {
		Certillion
			.config()
			.useProductionServer()
			.keystorePath("")
			.keystoreType("")
			.keyAlias("")
			.keystorePassword("")
			.done();
	}
	
}