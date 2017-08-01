
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de HashDocumentInfoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="HashDocumentInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="ContentType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DocumentName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Hash" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="UrlToDocument" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HashDocumentInfoType")
@XmlSeeAlso({
    BatchInfoType.class
})
public class HashDocumentInfoType {

    @XmlAttribute(name = "ContentType", required = true)
    protected String contentType;
    @XmlAttribute(name = "DocumentName", required = true)
    protected String documentName;
    @XmlAttribute(name = "Hash", required = true)
    protected String hash;
    @XmlAttribute(name = "UrlToDocument")
    protected String urlToDocument;

    /**
     * Obtém o valor da propriedade contentType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Define o valor da propriedade contentType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Obtém o valor da propriedade documentName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Define o valor da propriedade documentName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentName(String value) {
        this.documentName = value;
    }

    /**
     * Obtém o valor da propriedade hash.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHash() {
        return hash;
    }

    /**
     * Define o valor da propriedade hash.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHash(String value) {
        this.hash = value;
    }

    /**
     * Obtém o valor da propriedade urlToDocument.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlToDocument() {
        return urlToDocument;
    }

    /**
     * Define o valor da propriedade urlToDocument.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlToDocument(String value) {
        this.urlToDocument = value;
    }

}
