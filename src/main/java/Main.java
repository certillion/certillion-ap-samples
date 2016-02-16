import java.util.Arrays;

import br.com.esec.icpm.samples.ap.core.SignTextSample;
import br.com.esec.icpm.samples.ap.core.SignDocumentsSample;
import br.com.esec.icpm.samples.ap.core.AttachSignatureSample;
import br.com.esec.icpm.samples.ap.core.ValidateSignatureSample;

public class Main {

	private static final String COMMAND_SIGN_TEXT = "sign-text";
	private static final String COMMAND_SIGN_DOCS = "sign-documents";
	private static final String COMMAND_VALIDATE = "validate-signature";
	private static final String COMMAND_ATTACH = "attach-signature";

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Need this params: [command] [options...]");
			System.out.println("\tcommand: ["
					+ COMMAND_SIGN_TEXT + ", "
					+ COMMAND_SIGN_DOCS + ", "
					+ COMMAND_ATTACH + ", "
					+ COMMAND_VALIDATE + "] ");
			System.exit(1);
		}

		String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
		if (COMMAND_SIGN_TEXT.equals(args[0])) {
			SignTextSample.main(newArgs);
		} else if (COMMAND_SIGN_DOCS.equals(args[0])) {
			SignDocumentsSample.main(newArgs);
		} else if (COMMAND_VALIDATE.equals(args[0])) {
			ValidateSignatureSample.main(newArgs);
		} else if (COMMAND_ATTACH.equals(args[0])) {
			AttachSignatureSample.main(newArgs);
		}
	}

}
