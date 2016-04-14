
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrustChainFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrustChainFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="value" use="required" type="{http://esec.com.br/mss/ap}TrustChain" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrustChainFilterType")
public class TrustChainFilterType {

    @XmlAttribute(required = true)
    protected TrustChain value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link TrustChain }
     *     
     */
    public TrustChain getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrustChain }
     *     
     */
    public void setValue(TrustChain value) {
        this.value = value;
    }

}
