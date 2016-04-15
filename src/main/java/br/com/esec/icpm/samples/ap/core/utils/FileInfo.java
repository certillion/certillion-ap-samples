package br.com.esec.icpm.samples.ap.core.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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

	/**
	 * @param name      Name of the file to be found.
	 * @param fileInfos List to search in.
	 * @return The element of the list with the given or {@code null}.
	 */
	public static FileInfo findByName(String name, List<FileInfo> fileInfos) {
		for (FileInfo info : fileInfos) {
			if (info.getName().equals(name)) {
				return info;
			}
		}
		return null;
	}

	/**
	 * Merge elements of two lists of {@code FileInfo}s by name.
	 * <p/>
	 * The result of the merge will be in the {@code targetInfos} list.
	 * Elements that exists in both lists will have it's properties copied from {@code sourceInfos} to {@code targetInfos},
	 * but elements that are null in source but non-null in target will not be overwritten.
	 * Elements that exists only in {@code sourceInfos} will be appended to {@code targetInfos}.
	 *
	 * @param sourceInfos List to copy from.
	 * @param targetInfos List to copy to.
	 */
	public static void mergeByName(List<FileInfo> sourceInfos, List<FileInfo> targetInfos) {

		// for each element in source, find the one with same name in target
		for (FileInfo sourceInfo : sourceInfos) {
			FileInfo targetInfo = findByName(sourceInfo.getName(), targetInfos);

			// if the target element doesn't exists, create it, because the merge must contains everything from both
			if (targetInfo == null) {
				targetInfo = new FileInfo();
				targetInfos.add(targetInfo);
			}

			// copy all attributes from source to target (if but don't overwrite existing values with null)
			targetInfo.setName(defaultIfNull(sourceInfo.getName(), targetInfo.getName()));
			targetInfo.setStream(defaultIfNull(sourceInfo.getStream(), targetInfo.getStream()));
			targetInfo.setHash(defaultIfNull(sourceInfo.getHash(), targetInfo.getHash()));
			targetInfo.setTransactionId(defaultIfNull(sourceInfo.getTransactionId(), targetInfo.getTransactionId()));
			targetInfo.setSignatureStatus(defaultIfNull(sourceInfo.getSignatureStatus(), targetInfo.getSignatureStatus()));
			targetInfo.setAttachedSignatureStream(defaultIfNull(sourceInfo.getAttachedSignatureStream(), targetInfo.getAttachedSignatureStream()));
			targetInfo.setDetachedSignatureStream(defaultIfNull(sourceInfo.getDetachedSignatureStream(), targetInfo.getDetachedSignatureStream()));
		}
	}

	private static <T> T defaultIfNull(T object, T defaultValue) {
		return (object != null) ? object : defaultValue;
	}

}
