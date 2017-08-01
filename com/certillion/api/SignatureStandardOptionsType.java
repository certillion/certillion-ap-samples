
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SignatureStandardOptionsType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureStandardOptionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="ElementsId" type="{http://esec.com.br/mss/ap}ElementsIdXmldsigOptionType"/>
 *           &lt;element name="AttributeIdName" type="{http://esec.com.br/mss/ap}AttributeIdNameXmldsigOptionType"/>
 *           &lt;element name="ElementsName" type="{http://esec.com.br/mss/ap}ElementsNameXmldsigOptionType"/>
 *           &lt;element name="AddSubjectName" type="{http://esec.com.br/mss/ap}AddSubjectNameXmldsigOptionType"/>
 *           &lt;element name="AddKeyVal" type="{http://esec.com.br/mss/ap}AddKeyValXmldsigOptionType"/>
 *           &lt;element name="MultipleSignatures" type="{http://esec.com.br/mss/ap}MultipleSignaturesXmldsigOptionType"/>
 *           &lt;element name="TextOfPadesSignature" type="{http://esec.com.br/mss/ap}SignatureTextPadesOptionType"/>
 *           &lt;element name="PageOfPadesSignature" type="{http://esec.com.br/mss/ap}SignaturePagePadesOptionType"/>
 *           &lt;element name="PosXOfPadesSignature" type="{http://esec.com.br/mss/ap}SignaturePosXPadesOptionType"/>
 *           &lt;element name="PosYOfPadesSignature" type="{http://esec.com.br/mss/ap}SignaturePosYPadesOptionType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureStandardOptionsType", propOrder = {
    "elementsIdOrAttributeIdNameOrElementsName"
})
public class SignatureStandardOptionsType {

    @XmlElements({
        @XmlElement(name = "ElementsId", type = ElementsIdXmldsigOptionType.class),
        @XmlElement(name = "AttributeIdName", type = AttributeIdNameXmldsigOptionType.class),
        @XmlElement(name = "ElementsName", type = ElementsNameXmldsigOptionType.class),
        @XmlElement(name = "AddSubjectName", type = AddSubjectNameXmldsigOptionType.class),
        @XmlElement(name = "AddKeyVal", type = AddKeyValXmldsigOptionType.class),
        @XmlElement(name = "MultipleSignatures", type = MultipleSignaturesXmldsigOptionType.class),
        @XmlElement(name = "TextOfPadesSignature", type = SignatureTextPadesOptionType.class),
        @XmlElement(name = "PageOfPadesSignature", type = SignaturePagePadesOptionType.class),
        @XmlElement(name = "PosXOfPadesSignature", type = SignaturePosXPadesOptionType.class),
        @XmlElement(name = "PosYOfPadesSignature", type = SignaturePosYPadesOptionType.class)
    })
    protected List<Object> elementsIdOrAttributeIdNameOrElementsName;

    /**
     * Gets the value of the elementsIdOrAttributeIdNameOrElementsName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elementsIdOrAttributeIdNameOrElementsName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElementsIdOrAttributeIdNameOrElementsName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElementsIdXmldsigOptionType }
     * {@link AttributeIdNameXmldsigOptionType }
     * {@link ElementsNameXmldsigOptionType }
     * {@link AddSubjectNameXmldsigOptionType }
     * {@link AddKeyValXmldsigOptionType }
     * {@link MultipleSignaturesXmldsigOptionType }
     * {@link SignatureTextPadesOptionType }
     * {@link SignaturePagePadesOptionType }
     * {@link SignaturePosXPadesOptionType }
     * {@link SignaturePosYPadesOptionType }
     * 
     * 
     */
    public List<Object> getElementsIdOrAttributeIdNameOrElementsName() {
        if (elementsIdOrAttributeIdNameOrElementsName == null) {
            elementsIdOrAttributeIdNameOrElementsName = new ArrayList<Object>();
        }
        return this.elementsIdOrAttributeIdNameOrElementsName;
    }

}
