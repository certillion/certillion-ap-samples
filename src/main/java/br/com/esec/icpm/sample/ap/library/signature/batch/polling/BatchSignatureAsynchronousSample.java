package br.com.esec.icpm.sample.ap.library.signature.batch.polling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import br.com.esec.icpm.libs.Certillion;
import br.com.esec.icpm.libs.Server;
import br.com.esec.icpm.libs.signature.BatchSignatureRequest;
import br.com.esec.icpm.libs.signature.response.handler.batch.SignatureBatchAsynchHandler;
import br.com.esec.icpm.sample.ap.library.signature.ClientIdentity;

public class BatchSignatureAsynchronousSample {

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("BatchSignatureAsynchronousSample need this params: [identifier] [message] [[inputPath] [outputPath]]...");
			System.exit(1);
		}

		String identifier = args[0];
		String message = args[1];
		List<Document> documents = new ArrayList<Document>();
		for (int i = 2; i < args.length; i++) {
			String inputPath = args[i];
			i++;
			String outPath = args[i];
			documents.add(new Document(inputPath, outPath));
		}
		
		ClientIdentity.config();
		
		final BatchSignatureRequest signatureRequest = Certillion
			.signature()
			.toUser(identifier)
			.batch()
			.adobePdf()
			.message(message);
		
		for (Document document : documents) {
			signatureRequest
				.document(FilenameUtils.getName(document.inputPath), new FileInputStream(document.inputPath));
		}
		
		SignatureBatchAsynchHandler signatureResponse = signatureRequest
				.asynchSign()
				.waitTo(120);
		
		for (Document document : documents) {
			FileOutputStream out = new FileOutputStream(document.outputPath);
			signatureResponse.saveAttached(out);
			out.close();
		}
		
		System.exit(0);
	}

	static class Document {
		String inputPath;
		String outputPath;

		public Document(String inputPath, String outputPath) {
			this.inputPath = inputPath;
			this.outputPath = outputPath;
		}
	}

}
