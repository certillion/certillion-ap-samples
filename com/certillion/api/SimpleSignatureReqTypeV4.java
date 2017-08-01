
package com.certillion.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SimpleSignatureReqTypeV4 complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SimpleSignatureReqTypeV4">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="User" type="{http://esec.com.br/mss/ap}UserType"/>
 *         &lt;element name="DataToBeSigned" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Fingerprint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CertificateFilters" type="{http://esec.com.br/mss/ap}CertificateFiltersType" minOccurs="0"/>
 *         &lt;element name="AdditionalServices" type="{http://esec.com.br/mss/ap}AdditionalServiceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SignaturePolicy" type="{http://esec.com.br/mss/ap}SignaturePolicyType" />
 *       &lt;attribute name="SignatureStandard" type="{http://esec.com.br/mss/ap}SignatureStandardType" />
 *       &lt;attribute name="TimeOut" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="MessagingMode" use="required" type="{http://esec.com.br/mss/ap}MessagingModeType" />
 *       &lt;attribute name="TestMode" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="ApId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleSignatureReqTypeV4", propOrder = {
    "user",
    "dataToBeSigned",
    "fingerprint",
    "certificateFilters",
    "additionalServices"
})
public class SimpleSignatureReqTypeV4 {

    @XmlElement(name = "User", required = true)
    protected UserType user;
    @XmlElement(name = "DataToBeSigned", required = true)
    protected String dataToBeSigned;
    @XmlElement(name = "Fingerprint")
    protected String fingerprint;
    @XmlElement(name = "CertificateFilters")
    protected CertificateFiltersType certificateFilters;
    @XmlElement(name = "AdditionalServices")
    @XmlSchemaType(name = "string")
    protected List<AdditionalServiceType> additionalServices;
    @XmlAttribute(name = "SignaturePolicy")
    protected SignaturePolicyType signaturePolicy;
    @XmlAttribute(name = "SignatureStandard")
    protected SignatureStandardType signatureStandard;
    @XmlAttribute(name = "TimeOut")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger timeOut;
    @XmlAttribute(name = "MessagingMode", required = true)
    protected MessagingModeType messagingMode;
    @XmlAttribute(name = "TestMode", required = true)
    protected boolean testMode;
    @XmlAttribute(name = "ApId", required = true)
    protected long apId;

    /**
     * Obtém o valor da propriedade user.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getUser() {
        return user;
    }

    /**
     * Define o valor da propriedade user.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setUser(UserType value) {
        this.user = value;
    }

    /**
     * Obtém o valor da propriedade dataToBeSigned.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataToBeSigned() {
        return dataToBeSigned;
    }

    /**
     * Define o valor da propriedade dataToBeSigned.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataToBeSigned(String value) {
        this.dataToBeSigned = value;
    }

    /**
     * Obtém o valor da propriedade fingerprint.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * Define o valor da propriedade fingerprint.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFingerprint(String value) {
        this.fingerprint = value;
    }

    /**
     * Obtém o valor da propriedade certificateFilters.
     * 
     * @return
     *     possible object is
     *     {@link CertificateFiltersType }
     *     
     */
    public CertificateFiltersType getCertificateFilters() {
        return certificateFilters;
    }

    /**
     * Define o valor da propriedade certificateFilters.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateFiltersType }
     *     
     */
    public void setCertificateFilters(CertificateFiltersType value) {
        this.certificateFilters = value;
    }

    /**
     * Gets the value of the additionalServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalServiceType }
     * 
     * 
     */
    public List<AdditionalServiceType> getAdditionalServices() {
        if (additionalServices == null) {
            additionalServices = new ArrayList<AdditionalServiceType>();
        }
        return this.additionalServices;
    }

    /**
     * Obtém o valor da propriedade signaturePolicy.
     * 
     * @return
     *     possible object is
     *     {@link SignaturePolicyType }
     *     
     */
    public SignaturePolicyType getSignaturePolicy() {
        return signaturePolicy;
    }

    /**
     * Define o valor da propriedade signaturePolicy.
     * 
     * @param value
     *     allowed object is
     *     {@link SignaturePolicyType }
     *     
     */
    public void setSignaturePolicy(SignaturePolicyType value) {
        this.signaturePolicy = value;
    }

    /**
     * Obtém o valor da propriedade signatureStandard.
     * 
     * @return
     *     possible object is
     *     {@link SignatureStandardType }
     *     
     */
    public SignatureStandardType getSignatureStandard() {
        return signatureStandard;
    }

    /**
     * Define o valor da propriedade signatureStandard.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureStandardType }
     *     
     */
    public void setSignatureStandard(SignatureStandardType value) {
        this.signatureStandard = value;
    }

    /**
     * Obtém o valor da propriedade timeOut.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimeOut() {
        return timeOut;
    }

    /**
     * Define o valor da propriedade timeOut.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimeOut(BigInteger value) {
        this.timeOut = value;
    }

    /**
     * Obtém o valor da propriedade messagingMode.
     * 
     * @return
     *     possible object is
     *     {@link MessagingModeType }
     *     
     */
    public MessagingModeType getMessagingMode() {
        return messagingMode;
    }

    /**
     * Define o valor da propriedade messagingMode.
     * 
     * @param value
     *     allowed object is
     *     {@link MessagingModeType }
     *     
     */
    public void setMessagingMode(MessagingModeType value) {
        this.messagingMode = value;
    }

    /**
     * Obtém o valor da propriedade testMode.
     * 
     */
    public boolean isTestMode() {
        return testMode;
    }

    /**
     * Define o valor da propriedade testMode.
     * 
     */
    public void setTestMode(boolean value) {
        this.testMode = value;
    }

    /**
     * Obtém o valor da propriedade apId.
     * 
     */
    public long getApId() {
        return apId;
    }

    /**
     * Define o valor da propriedade apId.
     * 
     */
    public void setApId(long value) {
        this.apId = value;
    }

}
