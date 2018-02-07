
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HsmSyncSignatureReqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HsmSyncSignatureReqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="User" type="{http://esec.com.br/mss/ap}UserType"/>
 *         &lt;element name="Fingerprint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DocumentsToBeSigned" type="{http://esec.com.br/mss/ap}BatchInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DataToBeSigned" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CertificateFilters" type="{http://esec.com.br/mss/ap}CertificateFiltersType" minOccurs="0"/>
 *         &lt;element name="AdditionalServices" type="{http://esec.com.br/mss/ap}AdditionalServiceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ApId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="SignatureStandard" type="{http://esec.com.br/mss/ap}SignatureStandardType" />
 *       &lt;attribute name="SignaturePolicy" type="{http://esec.com.br/mss/ap}SignaturePolicyType" />
 *       &lt;attribute name="TestMode" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HsmSyncSignatureReqType", propOrder = {
    "user",
    "fingerprint",
    "documentsToBeSigned",
    "dataToBeSigned",
    "certificateFilters",
    "additionalServices"
})
public class HsmSyncSignatureReqType {

    @XmlElement(name = "User", required = true)
    protected UserType user;
    @XmlElement(name = "Fingerprint", required = true)
    protected String fingerprint;
    @XmlElement(name = "DocumentsToBeSigned")
    protected List<BatchInfoType> documentsToBeSigned;
    @XmlElement(name = "DataToBeSigned")
    protected String dataToBeSigned;
    @XmlElement(name = "CertificateFilters")
    protected CertificateFiltersType certificateFilters;
    @XmlElement(name = "AdditionalServices")
    @XmlSchemaType(name = "string")
    protected List<AdditionalServiceType> additionalServices;
    @XmlAttribute(name = "ApId")
    protected Long apId;
    @XmlAttribute(name = "SignatureStandard")
    protected SignatureStandardType signatureStandard;
    @XmlAttribute(name = "SignaturePolicy")
    protected SignaturePolicyType signaturePolicy;
    @XmlAttribute(name = "TestMode")
    protected Boolean testMode;

    /**
     * Gets the value of the user property.
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
     * Sets the value of the user property.
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
     * Gets the value of the fingerprint property.
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
     * Sets the value of the fingerprint property.
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
     * Gets the value of the documentsToBeSigned property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentsToBeSigned property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentsToBeSigned().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BatchInfoType }
     * 
     * 
     */
    public List<BatchInfoType> getDocumentsToBeSigned() {
        if (documentsToBeSigned == null) {
            documentsToBeSigned = new ArrayList<BatchInfoType>();
        }
        return this.documentsToBeSigned;
    }

    /**
     * Gets the value of the dataToBeSigned property.
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
     * Sets the value of the dataToBeSigned property.
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
     * Gets the value of the certificateFilters property.
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
     * Sets the value of the certificateFilters property.
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
     * Gets the value of the apId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getApId() {
        return apId;
    }

    /**
     * Sets the value of the apId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setApId(Long value) {
        this.apId = value;
    }

    /**
     * Gets the value of the signatureStandard property.
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
     * Sets the value of the signatureStandard property.
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
     * Gets the value of the signaturePolicy property.
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
     * Sets the value of the signaturePolicy property.
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
     * Gets the value of the testMode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTestMode() {
        return testMode;
    }

    /**
     * Sets the value of the testMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTestMode(Boolean value) {
        this.testMode = value;
    }

}
