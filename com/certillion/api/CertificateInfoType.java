
package com.certillion.api;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de CertificateInfoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
     * Obtém o valor da propriedade serialNumber.
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
     * Define o valor da propriedade serialNumber.
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
     * Obtém o valor da propriedade subjectDn.
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
     * Define o valor da propriedade subjectDn.
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
     * Obtém o valor da propriedade issuerDn.
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
     * Define o valor da propriedade issuerDn.
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
     * Obtém o valor da propriedade notBefore.
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
     * Define o valor da propriedade notBefore.
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
     * Obtém o valor da propriedade notAfter.
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
     * Define o valor da propriedade notAfter.
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
     * Obtém o valor da propriedade issuerCertificate.
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
     * Define o valor da propriedade issuerCertificate.
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
