import java.util.Arrays;

import br.com.esec.icpm.sample.ap.BatchSignatureAsynchronousApUtilsPollSample;
import br.com.esec.icpm.sample.ap.BatchSignatureAsynchronousApUtilsSample;
import br.com.esec.icpm.sample.ap.core.signature.batch.polling.BatchSignatureAsynchronousSample;
import br.com.esec.icpm.sample.ap.core.signature.simple.attach.AttachSignatureSample;
import br.com.esec.icpm.sample.ap.core.signature.simple.polling.SignatureSimpleDocumentAsynchronousSample;
import br.com.esec.icpm.sample.ap.core.signature.simple.synch.SignatureSimpleDocumentSynchronousSample;
import br.com.esec.icpm.sample.ap.core.signature.template.polling.SignatureByTemplateAsynchronousSample;
import br.com.esec.icpm.sample.ap.core.signature.template.synch.SignatureByTemplateSynchronousSample;
import br.com.esec.icpm.sample.ap.core.signature.validate.ValidateSignatureSynchronousSample;

public class Main {

	private static final String COMMAND_SIGN_SYNC = "signature-sync";
	private static final String COMMAND_SIGN_ASYNC = "signature-async";
	private static final String COMMAND_SIGN_TEMPLATE_SYNC = "signature-by-template-sync";
	private static final String COMMAND_SIGN_TEMPLATE_ASYNC = "signature-by-template-async";
	private static final String COMMAND_BATCH_SIGN_ASYNC = "batch-signature-async";
	private static final String COMMAND_VALIDATE = "signature-validate";
	private static final String COMMAND_ATTACH_SIGNATURE = "attach-signature";
	private static final String COMMAND_BATCH_SING_ASYNC_APUTILS = "batch-signature-async-aputils";
	private static final String COMMAND_BATCH_SING_ASYNC_APUTILS_POLL = "batch-signature-async-aputils-poll";

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Need this params: [command] [options...]");
			System.out
					.println("\tcommand: [" + COMMAND_SIGN_SYNC + ", "
							+ COMMAND_SIGN_ASYNC + ", "
							+ COMMAND_SIGN_TEMPLATE_ASYNC + ", "
							+ COMMAND_BATCH_SIGN_ASYNC + ", "
							+ COMMAND_BATCH_SING_ASYNC_APUTILS + ", "
							+ COMMAND_BATCH_SING_ASYNC_APUTILS_POLL + ", "
							+ COMMAND_ATTACH_SIGNATURE + ", "
							+ COMMAND_VALIDATE + "] ");
			System.exit(1);
		}

		String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
		if (COMMAND_SIGN_SYNC.equals(args[0])) {
			SignatureSimpleDocumentSynchronousSample.main(newArgs);
		} else if (COMMAND_SIGN_ASYNC.equals(args[0])) {
			SignatureSimpleDocumentAsynchronousSample.main(newArgs);
		} else if (COMMAND_SIGN_TEMPLATE_SYNC.equals(args[0])) {
			SignatureByTemplateSynchronousSample.main(newArgs);
		} else if (COMMAND_SIGN_TEMPLATE_ASYNC.equals(args[0])) {
			SignatureByTemplateAsynchronousSample.main(newArgs);
		} else if (COMMAND_BATCH_SIGN_ASYNC.equals(args[0])) {
			BatchSignatureAsynchronousSample.main(newArgs);
		}else if (COMMAND_BATCH_SING_ASYNC_APUTILS.equals(args[0])) {
			BatchSignatureAsynchronousApUtilsSample.main(newArgs);
		} else if (COMMAND_BATCH_SING_ASYNC_APUTILS_POLL.equals(args[0])) {
			BatchSignatureAsynchronousApUtilsPollSample.main(newArgs);
		} else if (COMMAND_VALIDATE.equals(args[0])) {
			ValidateSignatureSynchronousSample.main(newArgs);
		} else if (COMMAND_ATTACH_SIGNATURE.equals(args[0])) {
			AttachSignatureSample.main(newArgs);
		} 

	}

}
