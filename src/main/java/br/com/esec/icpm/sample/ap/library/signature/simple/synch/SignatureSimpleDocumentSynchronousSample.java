package br.com.esec.icpm.sample.ap.library.signature.simple.synch;

import java.io.FileOutputStream;

import br.com.esec.icpm.libs.Certillion;
import br.com.esec.icpm.sample.ap.library.signature.ClientIdentity;

public class SignatureSimpleDocumentSynchronousSample {

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("SignatureSimpleDocumentSynchronousSample need this params: [identifier] [message] [outputPath]");
			System.exit(1);
		}

		String identifier = args[0];
		String message = args[1];
		String outputPath = args[2];
		
		ClientIdentity.config();
		
		// Synch
		Certillion
			.signature()
			.toUser(identifier)
			.simple()
			.message(message)
			.sign()
			.save(new FileOutputStream(outputPath));
	}
	
}
