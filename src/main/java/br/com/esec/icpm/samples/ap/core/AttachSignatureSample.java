package br.com.esec.icpm.samples.ap.core;

import br.com.esec.icpm.samples.ap.Constants;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class AttachSignatureSample {

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			System.out.println("usage: certillion-ap-samples attach-signature [transaction-id] [file-url] [base64-signature]");
			System.exit(1);
		}

		// Get arguments from request
		String transactionId = args[1];
		String fileName = args[2];
		String signatureB64 = args[3];

		for (int i = 1; i < args.length; i++) {
			if (i == 1) {
				fileName = args[i];
			} else if (i == 2) {
				signatureB64 = args[i];
			} else if (i == 3) {
				transactionId = args[i];
			}
		}

		// Get the signaturePort
		String endpointAddr = Constants.BASE_URL + "/pdfs/signeds";
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
