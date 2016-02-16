package br.com.esec.icpm.samples.ap.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import br.com.esec.icpm.samples.ap.core.WebServiceInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class AttachSignatureSample {

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("AttachSignatureSample need this params: [fileName or fileUrl] [Base64-encoded-signature] [transactionId] ");
			System.exit(1);
		}

		String fileName = "";

		String signatureB64 = "";

		String transactionId = "";

		// Get arguments from request
		for (int i = 0; i < args.length; i++) {
			if (i == 0) {
				fileName = args[i];
			} else if (i == 1) {
				signatureB64 = args[i];
			} else if (i == 2) {
				transactionId = args[i];
			}
		}

		// Get the signaturePort
		String endpointAddr = WebServiceInfo.getBaseUrl() + "/pdfs/signeds";
		URL url = new URL(endpointAddr);

		// build http client
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url.toString());

		try {
			// create post body with attributes
			StringBody id = new StringBody(transactionId);
			MultipartEntity reqEntity = new MultipartEntity();
			// if file is in cloud
			if (isValidURL(fileName)) {
				StringBody fileURL = new StringBody(fileName);
				reqEntity.addPart("file-url", fileURL);

			} else {
				FileBody bin = new FileBody(new File(fileName));
				reqEntity.addPart("file", bin);
			}
			reqEntity.addPart("transactionId", id);
			reqEntity.addPart("signature-base64-encoded", new StringBody(
					signatureB64));

			httppost.setEntity(reqEntity);
			System.out.println("Requesting : " + httppost.getRequestLine());

			// execute post
			HttpResponse httpResponse = httpClient.execute(httppost);
			System.out.println("response status: "
					+ httpResponse.getStatusLine());

			// if request is ok, get the response
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream response = httpResponse.getEntity().getContent();
				File signedPdf = new File(transactionId + "-signed.pdf");
				System.out.println("saving file in: "
						+ signedPdf.getAbsolutePath());
				FileOutputStream signedpdfStream = new FileOutputStream(
						signedPdf);
				IOUtils.copy(response, signedpdfStream);
				signedpdfStream.close();
			}
			// System.out.println("responseBody : " + responseBody);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}

	}

	private static boolean isValidURL(String URL) {
		UrlValidator defaultValidator = new UrlValidator(); // default schemes
		return defaultValidator.isValid(URL) || URL.startsWith("file:/");
	}
}
