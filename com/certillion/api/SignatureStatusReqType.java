
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SignatureStatusReqType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureStatusReqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "SignatureStatusReqType")
public class SignatureStatusReqType {

    @XmlAttribute(name = "TransactionId", required = true)
    protected long transactionId;

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
