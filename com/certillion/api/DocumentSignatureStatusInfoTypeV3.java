
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de DocumentSignatureStatusInfoTypeV3 complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentSignatureStatusInfoTypeV3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://esec.com.br/mss/ap}StatusType"/>
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="SignatureInfo" type="{http://esec.com.br/mss/ap}SignatureInfoTypeV2" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TransactionId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentSignatureStatusInfoTypeV3", propOrder = {
    "documentName",
    "status",
    "signature",
    "signatureInfo"
})
public class DocumentSignatureStatusInfoTypeV3 {

    @XmlElement(name = "DocumentName", required = true)
    protected String documentName;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "Signature")
    protected byte[] signature;
    @XmlElement(name = "SignatureInfo")
    protected SignatureInfoTypeV2 signatureInfo;
    @XmlAttribute(name = "TransactionId", required = true)
    protected long transactionId;

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
     * Obtém o valor da propriedade status.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Obtém o valor da propriedade signature.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * Define o valor da propriedade signature.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignature(byte[] value) {
        this.signature = value;
    }

    /**
     * Obtém o valor da propriedade signatureInfo.
     * 
     * @return
     *     possible object is
     *     {@link SignatureInfoTypeV2 }
     *     
     */
    public SignatureInfoTypeV2 getSignatureInfo() {
        return signatureInfo;
    }

    /**
     * Define o valor da propriedade signatureInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureInfoTypeV2 }
     *     
     */
    public void setSignatureInfo(SignatureInfoTypeV2 value) {
        this.signatureInfo = value;
    }

    /**
     * Obtém o valor da propriedade transactionId.
     * 
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * Define o valor da propriedade transactionId.
     * 
     */
    public void setTransactionId(long value) {
        this.transactionId = value;
    }

}
