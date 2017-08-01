
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de BatchSignatureRespTypeV2 complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="BatchSignatureRespTypeV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://esec.com.br/mss/ap}StatusType"/>
 *         &lt;element name="Signatures" type="{http://esec.com.br/mss/ap}DocumentSignatureInfoType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "BatchSignatureRespTypeV2", propOrder = {
    "status",
    "signatures",
    "verificationCode"
})
public class BatchSignatureRespTypeV2 {

    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "Signatures")
    protected List<DocumentSignatureInfoType> signatures;
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
     * Gets the value of the signatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentSignatureInfoType }
     * 
     * 
     */
    public List<DocumentSignatureInfoType> getSignatures() {
        if (signatures == null) {
            signatures = new ArrayList<DocumentSignatureInfoType>();
        }
        return this.signatures;
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
