
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SignaturePagePadesOptionType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SignaturePagePadesOptionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignaturePagePadesOptionType")
public class SignaturePagePadesOptionType {

    @XmlAttribute(name = "value", required = true)
    protected int value;

    /**
     * Obt�m o valor da propriedade value.
     * 
     */
    public int getValue() {
        return value;
    }

    /**
     * Define o valor da propriedade value.
     * 
     */
    public void setValue(int value) {
        this.value = value;
    }

}
