
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureStandardOptionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
 *           &lt;element name="DigestMethod" type="{http://esec.com.br/mss/ap}DigestMethodXmldsigOptionType"/>
 *           &lt;element name="RemoveSignatureId" type="{http://esec.com.br/mss/ap}RemoveSignatureIdXmldsigOptionType"/>
 *           &lt;element name="TextOfPadesSignature" type="{http://esec.com.br/mss/ap}SignatureTextPadesOptionType"/>
 *           &lt;element name="PageOfPadesSignature" type="{http://esec.com.br/mss/ap}SignaturePagePadesOptionType"/>
 *           &lt;element name="PosXOfPadesSignature" type="{http://esec.com.br/mss/ap}SignaturePosXPadesOptionType"/>
 *           &lt;element name="PosYOfPadesSignature" type="{http://esec.com.br/mss/ap}SignaturePosYPadesOptionType"/>
 *           &lt;element name="HeightOfPadesSignature" type="{http://esec.com.br/mss/ap}HeightPadesOptionType"/>
 *           &lt;element name="WidthOfPadesSignature" type="{http://esec.com.br/mss/ap}WidthPadesOptionType"/>
 *           &lt;element name="FontSizeOfPadesSignature" type="{http://esec.com.br/mss/ap}FontSizePadesOptionType"/>
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
        @XmlElement(name = "DigestMethod", type = DigestMethodXmldsigOptionType.class),
        @XmlElement(name = "RemoveSignatureId", type = RemoveSignatureIdXmldsigOptionType.class),
        @XmlElement(name = "TextOfPadesSignature", type = SignatureTextPadesOptionType.class),
        @XmlElement(name = "PageOfPadesSignature", type = SignaturePagePadesOptionType.class),
        @XmlElement(name = "PosXOfPadesSignature", type = SignaturePosXPadesOptionType.class),
        @XmlElement(name = "PosYOfPadesSignature", type = SignaturePosYPadesOptionType.class),
        @XmlElement(name = "HeightOfPadesSignature", type = HeightPadesOptionType.class),
        @XmlElement(name = "WidthOfPadesSignature", type = WidthPadesOptionType.class),
        @XmlElement(name = "FontSizeOfPadesSignature", type = FontSizePadesOptionType.class)
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
     * {@link DigestMethodXmldsigOptionType }
     * {@link RemoveSignatureIdXmldsigOptionType }
     * {@link SignatureTextPadesOptionType }
     * {@link SignaturePagePadesOptionType }
     * {@link SignaturePosXPadesOptionType }
     * {@link SignaturePosYPadesOptionType }
     * {@link HeightPadesOptionType }
     * {@link WidthPadesOptionType }
     * {@link FontSizePadesOptionType }
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
