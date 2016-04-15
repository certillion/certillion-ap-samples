
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindSignatureReqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindSignatureReqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="TransactionId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="Attached" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindSignatureReqType")
public class FindSignatureReqType {

    @XmlAttribute(name = "TransactionId", required = true)
    protected long transactionId;
    @XmlAttribute(name = "Attached", required = true)
    protected boolean attached;

    /**
     * Gets the value of the transactionId property.
     * 
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     */
    public void setTransactionId(long value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the attached property.
     * 
     */
    public boolean isAttached() {
        return attached;
    }

    /**
     * Sets the value of the attached property.
     * 
     */
    public void setAttached(boolean value) {
        this.attached = value;
    }

}
