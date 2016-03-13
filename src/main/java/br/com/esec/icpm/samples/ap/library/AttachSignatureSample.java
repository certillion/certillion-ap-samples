package br.com.esec.icpm.samples.ap.library;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import br.com.esec.icpm.libs.Certillion;

public class AttachSignatureSample {

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("AttachSignatureSample need this params: [transactionId] [inputPath] [outputPath]");
			System.exit(1);
		}

		Long transactionId = Long.valueOf(args[0]);
		String inputPath = args[1];
		String outputPath = args[2];
		
		Configuration.config();
		
		Certillion
			.signature()
			.simple()
			.waitFor(transactionId)
			.saveAttached(new FileInputStream(inputPath), new FileOutputStream(outputPath));
		
	}
	
}
