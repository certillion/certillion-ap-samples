package br.com.esec.icpm.samples.ap.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Informations about a file to be signed.
 */
public class FileInfo {

    private final String path;
	private final InputStream fileStream;
    private String hash;
    private long transactionId;
    private byte[] detachedSignature;
	private InputStream attachedSignature;

    public FileInfo(File file) throws FileNotFoundException {
		this.path = file.getPath();
		this.fileStream = new FileInputStream(file);
	}

	public FileInfo(String path, InputStream fileStream) {
		this.path = path;
		this.fileStream = fileStream;
	}

	public String getPath() {
        return path;
    }

	public InputStream getFileStream() {
		return fileStream;
	}

	public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

	public byte[] getDetachedSignature() {
		return detachedSignature;
	}

	public void setDetachedSignature(byte[] detachedSignature) {
		this.detachedSignature = detachedSignature;
	}

	public InputStream getAttachedSignature() {
		return attachedSignature;
	}

	public void setAttachedSignature(InputStream attachedSignature) {
		this.attachedSignature = attachedSignature;
	}
}
