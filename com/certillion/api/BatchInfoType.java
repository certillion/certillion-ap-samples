
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de BatchInfoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
     * Obtém o valor da propriedade signatureStandardOptions.
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
     * Define o valor da propriedade signatureStandardOptions.
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
