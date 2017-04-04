
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchInfoType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://esec.com.br/mss/ap}HashDocumentInfoType">
 *       &lt;sequence>
 *         &lt;element name="SignatureStandardOptions" type="{http://esec.com.br/mss/ap}SignatureStandardOptionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchInfoType", propOrder = {
    "signatureStandardOptions"
})
public class BatchInfoType
    extends HashDocumentInfoType
{

    @XmlElement(name = "SignatureStandardOptions")
    protected SignatureStandardOptionsType signatureStandardOptions;

    /**
     * Gets the value of the signatureStandardOptions property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureStandardOptionsType }
     *     
     */
    public SignatureStandardOptionsType getSignatureStandardOptions() {
        return signatureStandardOptions;
    }

    /**
     * Sets the value of the signatureStandardOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureStandardOptionsType }
     *     
     */
    public void setSignatureStandardOptions(SignatureStandardOptionsType value) {
        this.signatureStandardOptions = value;
    }

}
