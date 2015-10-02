package br.com.esec.icpm.sample.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import br.com.esec.icpm.mss.ws.BatchSignatureTIDsRespType;
import br.com.esec.icpm.mss.ws.DocumentSignatureStatusInfoType;

public class DocumentLinks {
	/**
	 * Create a .properties file with the document transactionID and the path
	 * where it is stored
	 * 
	 * @param bSignTID
	 * @param pathToStore
	 * @throws IOException
	 */

	private File file;
	private String path;
	private long tId;
	private static DocumentLinks instance = null;
	private OutputStream outputProp = null;
	private Properties prop = null;

	private DocumentLinks(String path, long tId) {
		this.path = path;
		this.tId = tId;
	};

	public static DocumentLinks getInstance(String path, long tId) {
		if (instance == null) {
			instance = new DocumentLinks(path, tId);
		}
		return instance;
	}

	// Cria arquivo .properties com o caminho construido atraves dos parametros
	// (path) e (tId)
	private void openFile() {
		try {
			this.prop = new Properties();
			this.file = new File(this.path + "/" + this.tId + ".properties");
			this.outputProp = new FileOutputStream(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Armazena no arquivo .properties criado, o transactionId do documento
	// seguido do nome do documento
	public void store(BatchSignatureTIDsRespType response) throws IOException {
		openFile();
		List<DocumentSignatureStatusInfoType> signaturDocs = response
				.getDocumentSignatureStatus();

		for (DocumentSignatureStatusInfoType signaturDoc : signaturDocs) {
			this.prop.setProperty(
					String.valueOf(signaturDoc.getTransactionId()),
					signaturDoc.getDocumentName());
		}

		this.prop.store(this.outputProp, "");

		this.outputProp.close();
	}

}
