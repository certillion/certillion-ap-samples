
package br.com.esec.mss.ap;

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
 * <p>Java class for BatchSignatureComplexDocumentReqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchSignatureComplexDocumentReqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MobileUser" type="{http://esec.com.br/mss/ap}MobileUserType"/>
 *         &lt;element name="DataToBeDisplayed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DocumentsToBeSigned" type="{http://esec.com.br/mss/ap}HashDocumentInfoType" maxOccurs="unbounded"/>
 *         &lt;element name="CertificateFilters" type="{http://esec.com.br/mss/ap}CertificateFiltersType" minOccurs="0"/>
 *         &lt;element name="AdditionalServices" type="{http://esec.com.br/mss/ap}AdditionalServiceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TimeOut" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="MessagingMode" use="required" type="{http://esec.com.br/mss/ap}MessagingModeType" />
 *       &lt;attribute name="TestMode" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SignatureStandard" type="{http://esec.com.br/mss/ap}SignatureStandardType" />
 *       &lt;attribute name="SignaturePolicy" type="{http://esec.com.br/mss/ap}SignaturePolicyType" />
 *       &lt;attribute name="ApId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchSignatureComplexDocumentReqType", propOrder = {
    "mobileUser",
    "dataToBeDisplayed",
    "documentsToBeSigned",
    "certificateFilters",
    "additionalServices"
})
public class BatchSignatureComplexDocumentReqType {

    @XmlElement(name = "MobileUser", required = true)
    protected MobileUserType mobileUser;
    @XmlElement(name = "DataToBeDisplayed", required = true)
    protected String dataToBeDisplayed;
    @XmlElement(name = "DocumentsToBeSigned", required = true)
    protected List<HashDocumentInfoType> documentsToBeSigned;
    @XmlElement(name = "CertificateFilters")
    protected CertificateFiltersType certificateFilters;
    @XmlElement(name = "AdditionalServices")
    @XmlSchemaType(name = "string")
    protected List<AdditionalServiceType> additionalServices;
    @XmlAttribute(name = "TimeOut")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger timeOut;
    @XmlAttribute(name = "MessagingMode", required = true)
    protected MessagingModeType messagingMode;
    @XmlAttribute(name = "TestMode", required = true)
    protected boolean testMode;
    @XmlAttribute(name = "SignatureStandard")
    protected SignatureStandardType signatureStandard;
    @XmlAttribute(name = "SignaturePolicy")
    protected SignaturePolicyType signaturePolicy;
    @XmlAttribute(name = "ApId", required = true)
    protected long apId;

    /**
     * Gets the value of the mobileUser property.
     * 
     * @return
     *     possible object is
     *     {@link MobileUserType }
     *     
     */
    public MobileUserType getMobileUser() {
        return mobileUser;
    }

    /**
     * Sets the value of the mobileUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link MobileUserType }
     *     
     */
    public void setMobileUser(MobileUserType value) {
        this.mobileUser = value;
    }

    /**
     * Gets the value of the dataToBeDisplayed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataToBeDisplayed() {
        return dataToBeDisplayed;
    }

    /**
     * Sets the value of the dataToBeDisplayed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataToBeDisplayed(String value) {
        this.dataToBeDisplayed = value;
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
     * {@link HashDocumentInfoType }
     * 
     * 
     */
    public List<HashDocumentInfoType> getDocumentsToBeSigned() {
        if (documentsToBeSigned == null) {
            documentsToBeSigned = new ArrayList<HashDocumentInfoType>();
        }
        return this.documentsToBeSigned;
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
     * Gets the value of the timeOut property.
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
     * Sets the value of the timeOut property.
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
     * Gets the value of the messagingMode property.
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
     * Sets the value of the messagingMode property.
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
     * Gets the value of the testMode property.
     * 
     */
    public boolean isTestMode() {
        return testMode;
    }

    /**
     * Sets the value of the testMode property.
     * 
     */
    public void setTestMode(boolean value) {
        this.testMode = value;
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
     * Gets the value of the apId property.
     * 
     */
    public long getApId() {
        return apId;
    }

    /**
     * Sets the value of the apId property.
     * 
     */
    public void setApId(long value) {
        this.apId = value;
    }

}
