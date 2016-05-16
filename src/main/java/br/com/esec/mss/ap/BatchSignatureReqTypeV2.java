package br.com.esec.mss.ap;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchSignatureReqTypeV2", propOrder = { "mobileUser", "dataToBeDisplayed", "fingerprint",
		"documentsToBeSigned", "timeOut", "messagingMode", "certificateFilters", "additionalServices", "testMode" })
public class BatchSignatureReqTypeV2 {

	/**
	 * Any identifier that allows the MSSP to contact enduser's mobile device.
	 */
	@XmlElement(name = "MobileUser", required = true)
	protected MobileUserType mobileUser;

	/**
	 * An informative text describing to the user the contents of this batch.
	 */
	@XmlElement(name = "DataToBeDisplayed", required = true)
	protected String dataToBeDisplayed;

	/**
	 * User identifier (ex: biometry information).
	 */
	@XmlElement(name = "Fingerprint")
	protected String fingerprint;

	/**
	 * List of documents to be signed.
	 */
	@XmlElement(name = "DocumentsToBeSigned", required = true)
	protected List<BatchInfoType> documentsToBeSigned;

	/**
	 * The timeout, in seconds, of this transaction. If the response is not
	 * received from the user before this amount of time, the transaction is
	 * considered expired.
	 * <dl>
	 * <dt>Mandatory
	 * <dd>No
	 * <dt>Default
	 * <dd>10min
	 * </dl>
	 */
	@XmlAttribute(name = "TimeOut")
	@XmlSchemaType(name = "positiveInteger")
	protected Integer timeOut;

	/**
	 * Denotes the messaging mode, either: synchronous, asynchronous
	 * client-server or asynchronous server-server.
	 */
	@XmlAttribute(name = "MessagingMode", required = true)
	protected MessagingModeType messagingMode;

	/**
	 * Set the standard of the signature. Can be CADES, ADOBEPDF or XADES
	 * <dl>
	 * <dt>Mandatory
	 * <dd>Yes
	 * <dt>Default
	 * <dd>CADES
	 * </dl>
	 */
	@XmlAttribute(name = "SignatureStandard")
	protected SignatureStandardType signatureStandard;

	/**
	 * Denotes the signature policy type.
	 * <p>
	 * Mandatory: No
	 */
	@XmlAttribute(name = "SignaturePolicy")
	protected SignaturePolicyType signaturePolicy;

	/**
	 * Denotes the signature type.
	 * <dl>
	 * <dt>Mandatory
	 * <dd>No
	 * </dl>
	 */
	@XmlElement(name = "CertificateFilters")
	protected CertificateFiltersType certificateFilters;

	/**
	 * Listing of additional services enabled in this transaction.
	 * <dl>
	 * <dt>Mandatory
	 * <dd>No
	 * </dl>
	 */
	@XmlElement(name = "AdditionalServices")
	protected List<AdditionalServiceType> additionalServices;

	/**
	 * Set test mode enable on this transaction.
	 * <dl>
	 * <dt>Mandatory
	 * <dd>No
	 * </dl>
	 */
	@XmlAttribute(name = "TestMode")
	protected boolean testMode;

	/**
	 * the specific ap for transaction
	 * <p>
	 * Mandatory: No
	 */
	@XmlAttribute(name = "ApId")
	protected long apId;

	/**
	 * <p>
	 * Getter for the field <code>mobileUser</code>.
	 * </p>
	 *
	 * @return a {@link br.com.esec.icpm.server.ws.MobileUserType} object.
	 */
	public MobileUserType getMobileUser() {
		return mobileUser;
	}

	/**
	 * <p>
	 * Setter for the field <code>mobileUser</code>.
	 * </p>
	 *
	 * @param mobileUser
	 *            a {@link br.com.esec.icpm.server.ws.MobileUserType} object.
	 */
	public void setMobileUser(MobileUserType mobileUser) {
		this.mobileUser = mobileUser;
	}

	/**
	 * <p>
	 * Getter for the field <code>dataToBeDisplayed</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDataToBeDisplayed() {
		return dataToBeDisplayed;
	}

	/**
	 * <p>
	 * Setter for the field <code>dataToBeDisplayed</code>.
	 * </p>
	 *
	 * @param dataToBeDisplayed
	 *            a {@link java.lang.String} object.
	 */
	public void setDataToBeDisplayed(String dataToBeDisplayed) {
		this.dataToBeDisplayed = dataToBeDisplayed;
	}

	/**
	 * <p>
	 * Getter for the field <code>fingerprint</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * <p>
	 * Setter for the field <code>fingerprint</code>.
	 * </p>
	 *
	 * @param fingerprint
	 *            a {@link java.lang.String} object.
	 */
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	/**
	 * <p>
	 * Getter for the field <code>documentsToBeSigned</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<BatchInfoType> getDocumentsToBeSigned() {
		return documentsToBeSigned;
	}

	/**
	 * <p>
	 * Setter for the field <code>documentsToBeSigned</code>.
	 * </p>
	 *
	 * @param documentsToBeSigned
	 *            a {@link java.util.List} object.
	 */
	public void setDocumentsToBeSigned(List<BatchInfoType> documentsToBeSigned) {
		this.documentsToBeSigned = documentsToBeSigned;
	}

	/**
	 * <p>
	 * Getter for the field <code>timeOut</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getTimeOut() {
		return timeOut;
	}

	/**
	 * <p>
	 * Setter for the field <code>timeOut</code>.
	 * </p>
	 *
	 * @param timeOut
	 *            a {@link java.lang.Integer} object.
	 */
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * <p>
	 * Getter for the field <code>messagingMode</code>.
	 * </p>
	 *
	 * @return a {@link br.com.esec.icpm.mss.ws.MessagingModeType} object.
	 */
	public MessagingModeType getMessagingMode() {
		return messagingMode;
	}

	/**
	 * <p>
	 * Setter for the field <code>messagingMode</code>.
	 * </p>
	 *
	 * @param messagingMode
	 *            a {@link br.com.esec.icpm.mss.ws.MessagingModeType} object.
	 */
	public void setMessagingMode(MessagingModeType messagingMode) {
		this.messagingMode = messagingMode;
	}

	/**
	 * <p>
	 * Getter for the field <code>certificateFilters</code>.
	 * </p>
	 *
	 * @return a {@link br.com.esec.icpm.server.ws.CertificateFiltersType}
	 *         object.
	 */
	public CertificateFiltersType getCertificateFilters() {
		return certificateFilters;
	}

	/**
	 * <p>
	 * Setter for the field <code>certificateFilters</code>.
	 * </p>
	 *
	 * @param certificateFilters
	 *            a {@link br.com.esec.icpm.server.ws.CertificateFiltersType}
	 *            object.
	 */
	public void setCertificateFilters(CertificateFiltersType certificateFilters) {
		this.certificateFilters = certificateFilters;
	}

	/**
	 * <p>
	 * Getter for the field <code>additionalServices</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<AdditionalServiceType> getAdditionalServices() {
		return additionalServices;
	}

	/**
	 * <p>
	 * Setter for the field <code>additionalServices</code>.
	 * </p>
	 *
	 * @param additionalServices
	 *            a {@link java.util.List} object.
	 */
	public void setAdditionalServices(List<AdditionalServiceType> additionalServices) {
		this.additionalServices = additionalServices;
	}

	/**
	 * <p>
	 * isTestMode.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean isTestMode() {
		return testMode;
	}

	/**
	 * <p>
	 * Setter for the field <code>testMode</code>.
	 * </p>
	 *
	 * @param testMode
	 *            a boolean.
	 */
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	/**
	 * <p>
	 * Getter for the field <code>signatureStandard</code>.
	 * </p>
	 *
	 * @return a {@link br.com.esec.icpm.mss.ws.SignatureStandardType} object.
	 */
	public SignatureStandardType getSignatureStandard() {
		return signatureStandard;
	}

	/**
	 * <p>
	 * Setter for the field <code>signatureStandard</code>.
	 * </p>
	 *
	 * @param signatureStandard
	 *            a {@link br.com.esec.icpm.mss.ws.SignatureStandardType}
	 *            object.
	 */
	public void setSignatureStandard(SignatureStandardType signatureStandard) {
		this.signatureStandard = signatureStandard;
	}

	/**
	 * <p>
	 * Getter for the field <code>signaturePolicy</code>.
	 * </p>
	 *
	 * @return a {@link br.com.esec.icpm.server.ws.SignaturePolicyType} object.
	 */
	public SignaturePolicyType getSignaturePolicy() {
		return signaturePolicy;
	}

	/**
	 * <p>
	 * Setter for the field <code>signaturePolicy</code>.
	 * </p>
	 *
	 * @param signaturePolicy
	 *            a {@link br.com.esec.icpm.server.ws.SignaturePolicyType}
	 *            object.
	 */
	public void setSignaturePolicy(SignaturePolicyType signaturePolicy) {
		this.signaturePolicy = signaturePolicy;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "BatchSignatureComplexDocumentReqType [mobileUser=" + mobileUser + ", dataToBeDisplayed="
				+ dataToBeDisplayed + ", documentsToBeSigned=" + documentsToBeSigned + ", timeOut=" + timeOut
				+ ", messagingMode=" + messagingMode + ", signatureStandard=" + signatureStandard
				+ ", certificateFilters=" + certificateFilters + ", additionalServices=" + additionalServices
				+ ", testMode=" + testMode + "]";
	}

	public long isApId() {
		return apId;
	}

	public void setApId(long apId) {
		this.apId = apId;
	}

}
