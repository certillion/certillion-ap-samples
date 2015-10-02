package br.com.esec.icpm.sample.ap.core;

import java.net.URL;

public class WsSignerAddress {

	private static final String ADDRESS = "http://localhost:8280";
	private static final String WSDL_AP = ADDRESS + "/mss/serviceAp.wsdl";

	public static String get() {
		check();
		return ADDRESS;
	}

	protected static void check() {
		try {
			new URL(WSDL_AP).openStream();
		} catch (Exception e) {
			System.err.println("Could not connect to '" + WSDL_AP + "'. Check your WS-Signer configuration.");
			System.exit(1);
		}
	}
}
