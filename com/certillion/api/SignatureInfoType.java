
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de SignatureInfoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Valid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="InvalidReason" type="{http://esec.com.br/mss/ap}SignatureErrorType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SigningTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SignerCertificate" type="{http://esec.com.br/mss/ap}CertificateInfoType" minOccurs="0"/>
 *         &lt;element name="PolicyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PolicyUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureInfoType", propOrder = {
    "valid",
    "invalidReason",
    "signingTime",
    "signerCertificate",
    "policyId",
    "policyUrl"
})
public class SignatureInfoType {

    @XmlElement(name = "Valid")
    protected boolean valid;
    @XmlElement(name = "InvalidReason")
    @XmlSchemaType(name = "string")
    protected List<SignatureErrorType> invalidReason;
    @XmlElement(name = "SigningTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signingTime;
    @XmlElement(name = "SignerCertificate")
    protected CertificateInfoType signerCertificate;
    @XmlElement(name = "PolicyId")
    protected String policyId;
    @XmlElement(name = "PolicyUrl")
    protected String policyUrl;

    /**
     * Obtém o valor da propriedade valid.
     * 
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Define o valor da propriedade valid.
     * 
     */
    public void setValid(boolean value) {
        this.valid = value;
    }

    /**
     * Gets the value of the invalidReason property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invalidReason property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvalidReason().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignatureErrorType }
     * 
     * 
     */
    public List<SignatureErrorType> getInvalidReason() {
        if (invalidReason == null) {
            invalidReason = new ArrayList<SignatureErrorType>();
        }
        return this.invalidReason;
    }

    /**
     * Obtém o valor da propriedade signingTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSigningTime() {
        return signingTime;
    }

    /**
     * Define o valor da propriedade signingTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSigningTime(XMLGregorianCalendar value) {
        this.signingTime = value;
    }

    /**
     * Obtém o valor da propriedade signerCertificate.
     * 
     * @return
     *     possible object is
     *     {@link CertificateInfoType }
     *     
     */
    public CertificateInfoType getSignerCertificate() {
        return signerCertificate;
    }

    /**
     * Define o valor da propriedade signerCertificate.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateInfoType }
     *     
     */
    public void setSignerCertificate(CertificateInfoType value) {
        this.signerCertificate = value;
    }

    /**
     * Obtém o valor da propriedade policyId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyId() {
        return policyId;
    }

    /**
     * Define o valor da propriedade policyId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyId(String value) {
        this.policyId = value;
    }

    /**
     * Obtém o valor da propriedade policyUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyUrl() {
        return policyUrl;
    }

    /**
     * Define o valor da propriedade policyUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyUrl(String value) {
        this.policyUrl = value;
    }

}
