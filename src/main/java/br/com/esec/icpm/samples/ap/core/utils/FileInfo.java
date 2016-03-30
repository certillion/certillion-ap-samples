package br.com.esec.icpm.samples.ap.core.utils;

import java.io.*;

/**
 * Informations about a file to be signed.
 * <br/>
 * <h2>How to use this class</h2>
 * <ol>
 * <li>To upload the file, the {@code name} and {@code stream} properties must be set.
 * At the end, the {@code hash} properties is written for you.</li>
 * <li>To request the signature, the {@code hash} properties must be set.
 * At the end, the {@code transactionId} and {@code signatureStatus} properties are written for you.</li>
 * <li>To download the signature, the {@code transactionId} properties and the corresponding output stream
 * ({@code detachedSignatureStream} or {@code attachedSignatureStream}) must be set.
 * At the end, the signature is written to the output stream.</li>
 * </ol>
 */
public class FileInfo {

	// set by the user, when he wants to upload a file
	private String name;
	private InputStream stream;

	// set when the file is uploaded
    private String hash;

	// set when the signature is requested
    private long transactionId;

	// set when the signature is finalized
	private CertillionStatus signatureStatus;

	// set by the user, to received the downloaded signature
    private OutputStream detachedSignatureStream;
	private OutputStream attachedSignatureStream;

	/**
	 * Default constructor. All properties must be provided via setter.
	 *
	 * @see FileInfo How to use this class.
	 */
    public FileInfo() {
	}

	/**
	 * @return The name of the file, as seen by the user on the device.
     */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name of the file, as seen by the user on the device.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The file input stream, used to upload the file.
     */
	public InputStream getStream() {
		return stream;
	}

	/**
	 * @param stream The file input stream, used to upload the file.
     */
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	/**
	 * @return The hash of the file (in Base32), as returned by the server on upload.
     */
	public String getHash() {
        return hash;
    }

	/**
	 * @param hash The hash of the file (in Base32), as returned by the server on upload.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

	/**
	 * @return The ID of this file's signature transaction, as returned by the server.
     */
    public long getTransactionId() {
        return transactionId;
    }

	/**
	 * @param transactionId The ID of this file's signature transaction, as returned by the server.
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

	/**
	 * @return The status of this file's signature transaction, as returned by the server.
     */
	public CertillionStatus getSignatureStatus() {
		return signatureStatus;
	}

	/**
	 * @param signatureStatus The status of this file's signature transaction, as returned by the server.
     */
	public void setSignatureStatus(CertillionStatus signatureStatus) {
		this.signatureStatus = signatureStatus;
	}

	/**
	 * @return The output stream to write the download of the detached signature.
     */
	public OutputStream getDetachedSignatureStream() {
		return detachedSignatureStream;
	}

	/**
	 * @param detachedSignatureStream The output stream to write the download of the detached signature.
     */
	public void setDetachedSignatureStream(OutputStream detachedSignatureStream) {
		this.detachedSignatureStream = detachedSignatureStream;
	}

	/**
	 * @return The output stream to write the download of the attached signature.
     */
	public OutputStream getAttachedSignatureStream() {
		return attachedSignatureStream;
	}

	/**
	 * @param attachedSignatureStream The output stream to write the download of the attached signature.
     */
	public void setAttachedSignatureStream(OutputStream attachedSignatureStream) {
		this.attachedSignatureStream = attachedSignatureStream;
	}

}
