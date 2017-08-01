
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SimpleSignatureRespTypeV4 complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SimpleSignatureRespTypeV4">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://esec.com.br/mss/ap}StatusType"/>
 *         &lt;element name="VerificationCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "SimpleSignatureRespTypeV4", propOrder = {
    "status",
    "verificationCode"
})
public class SimpleSignatureRespTypeV4 {

    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "VerificationCode")
    protected Integer verificationCode;
    @XmlAttribute(name = "TransactionId", required = true)
    protected long transactionId;

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
     * Obtém o valor da propriedade verificationCode.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVerificationCode() {
        return verificationCode;
    }

    /**
     * Define o valor da propriedade verificationCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVerificationCode(Integer value) {
        this.verificationCode = value;
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
