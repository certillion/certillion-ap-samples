package br.com.esec.icpm.samples.ap;

import java.text.MessageFormat;
import java.util.Arrays;

import br.com.esec.icpm.samples.ap.core.AttachSignatureSample;
import br.com.esec.icpm.samples.ap.core.SignDocumentsNotificationSample;
import br.com.esec.icpm.samples.ap.core.SignDocumentsSample;
import br.com.esec.icpm.samples.ap.core.SignHsmSample;
import br.com.esec.icpm.samples.ap.core.SignTextSample;
import br.com.esec.icpm.samples.ap.core.SignXmldsigSample;
import br.com.esec.icpm.samples.ap.core.ValidatePDFSignatureSample;
import br.com.esec.icpm.samples.ap.core.ValidateSignatureSample;
import br.com.esec.icpm.samples.ap.core.partial.OnlyDownloadAttachedSample;
import br.com.esec.icpm.samples.ap.core.partial.OnlyDownloadDetachedSample;
import br.com.esec.icpm.samples.ap.core.partial.OnlySignDocumentsSample;
import br.com.esec.icpm.samples.ap.core.partial.OnlyUploadDocumentsSample;

public class Main {

	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			printUsage();
			System.exit(1);
		}

		if (Constants.COMMAND_SIGN_TEXT.equals(args[0])) {
			SignTextSample.main(args);
		} else if (Constants.COMMAND_SIGN_DOCS.equals(args[0])) {
			SignDocumentsSample.main(args);
		} else if (Constants.COMMAND_SIGN_DOCS_NOTIFICATION.equals(args[0])) {
			SignDocumentsNotificationSample.main(args);
		} else if (Constants.COMMAND_SIGN_HSM.equals(args[0])) {
			SignHsmSample.main(args);	
		} else if (Constants.COMMAND_XMLDSIG.equals(args[0])) {
			SignXmldsigSample.main(args);	
		} else if (Constants.COMMAND_VALIDATE.equals(args[0])) {
			ValidateSignatureSample.main(args);
		} else if (Constants.COMMAND_VALIDATE_PDF.equals(args[0])) {
			ValidatePDFSignatureSample.main(args);
		} else if (Constants.COMMAND_ATTACH.equals(args[0])) {
			AttachSignatureSample.main(args);
		} else if (Constants.COMMAND_ONLY_UPLOAD.equals(args[0])) {
			OnlyUploadDocumentsSample.main(args);
		} else if (Constants.COMMAND_ONLY_SIGN.equals(args[0])) {
			OnlySignDocumentsSample.main(args);
		} else if (Constants.COMMAND_ONLY_DOWNLOAD_ATTACHED.equals(args[0])) {
			OnlyDownloadAttachedSample.main(args);
		} else if (Constants.COMMAND_ONLY_DOWNLOAD_DETACHED.equals(args[0])) {
			OnlyDownloadDetachedSample.main(args);
		} else {
			printUsage();
			System.exit(1);
		}
	}

	private static void printUsage() {
		System.out.println(MessageFormat.format(
				"usage: {0} <command> [<options...>] \n" +
				"\n" +
				"\t command: one of {1} \n" +
				"\t options: depends of the command \n",
				Constants.APP_NAME,
				Arrays.asList(Constants.COMMAND_SIGN_TEXT, Constants.COMMAND_SIGN_DOCS, 
						Constants.COMMAND_VALIDATE, Constants.COMMAND_VALIDATE_PDF,
						Constants.COMMAND_ATTACH)
		));
	}

}
