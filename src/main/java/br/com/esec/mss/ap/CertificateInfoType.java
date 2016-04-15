
package br.com.esec.mss.ap;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CertificateInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CertificateInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="subjectDn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="issuerDn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notBefore" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="notAfter" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IssuerCertificate" type="{http://esec.com.br/mss/ap}CertificateInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CertificateInfoType", propOrder = {
    "serialNumber",
    "subjectDn",
    "issuerDn",
    "notBefore",
    "notAfter",
    "issuerCertificate"
})
public class CertificateInfoType {

    @XmlElement(required = true)
    protected BigInteger serialNumber;
    @XmlElement(required = true)
    protected String subjectDn;
    @XmlElement(required = true)
    protected String issuerDn;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notBefore;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notAfter;
    @XmlElement(name = "IssuerCertificate")
    protected CertificateInfoType issuerCertificate;

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSerialNumber(BigInteger value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the subjectDn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectDn() {
        return subjectDn;
    }

    /**
     * Sets the value of the subjectDn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectDn(String value) {
        this.subjectDn = value;
    }

    /**
     * Gets the value of the issuerDn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuerDn() {
        return issuerDn;
    }

    /**
     * Sets the value of the issuerDn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuerDn(String value) {
        this.issuerDn = value;
    }

    /**
     * Gets the value of the notBefore property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotBefore() {
        return notBefore;
    }

    /**
     * Sets the value of the notBefore property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotBefore(XMLGregorianCalendar value) {
        this.notBefore = value;
    }

    /**
     * Gets the value of the notAfter property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotAfter() {
        return notAfter;
    }

    /**
     * Sets the value of the notAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotAfter(XMLGregorianCalendar value) {
        this.notAfter = value;
    }

    /**
     * Gets the value of the issuerCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link CertificateInfoType }
     *     
     */
    public CertificateInfoType getIssuerCertificate() {
        return issuerCertificate;
    }

    /**
     * Sets the value of the issuerCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateInfoType }
     *     
     */
    public void setIssuerCertificate(CertificateInfoType value) {
        this.issuerCertificate = value;
    }

}
