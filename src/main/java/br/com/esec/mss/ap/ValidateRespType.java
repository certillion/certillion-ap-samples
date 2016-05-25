
package br.com.esec.mss.ap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Error" type="{http://esec.com.br/mss/ap}ValidationErrorType" minOccurs="0"/>
 *         &lt;element name="Signatures" type="{http://esec.com.br/mss/ap}SignatureInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateRespType", propOrder = {
    "error",
    "signatures"
})
public class ValidateRespType {

    @XmlElement(name = "Error")
    @XmlSchemaType(name = "string")
    protected ValidationErrorType error;
    @XmlElement(name = "Signatures")
    protected List<SignatureInfoType> signatures;

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationErrorType }
     *     
     */
    public ValidationErrorType getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationErrorType }
     *     
     */
    public void setError(ValidationErrorType value) {
        this.error = value;
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
     * {@link SignatureInfoType }
     * 
     * 
     */
    public List<SignatureInfoType> getSignatures() {
        if (signatures == null) {
            signatures = new ArrayList<SignatureInfoType>();
        }
        return this.signatures;
    }

}
