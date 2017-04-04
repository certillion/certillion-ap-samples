package com.certillion.utils;

public class WSUtils {

	public static void dumpToConsole(boolean dump) {
		String dumpStr = new Boolean(dump).toString();
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", dumpStr);
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", dumpStr);
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", dumpStr);
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", dumpStr);
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "" + (100 * 1024));
	}
}
