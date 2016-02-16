package br.com.esec.icpm.samples.ap.library.signature.simple.polling;

import java.io.FileOutputStream;

import br.com.esec.icpm.libs.Certillion;
import br.com.esec.icpm.samples.ap.library.signature.ClientIdentity;

public class SignatureSimpleDocumentAsynchronousSample {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("SignTextAsynchronousSample need this params: [identifier] [message] [outputPath]");
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
			.asynchSign()
			.waitTo()
			.save(new FileOutputStream(outputPath));
		
	}
	
}
