
package br.com.esec.mss.ap;

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
        @XmlElement(name = "AddKeyVal", type = AddKeyValXmldsigOptionType.class),
        @XmlElement(name = "AddSubjectName", type = AddSubjectNameXmldsigOptionType.class),
        @XmlElement(name = "ElementsName", type = ElementsNameXmldsigOptionType.class),
        @XmlElement(name = "ElementsId", type = ElementsIdXmldsigOptionType.class),
        @XmlElement(name = "MultipleSignatures", type = MultipleSignaturesXmldsigOptionType.class),
        @XmlElement(name = "AttributeIdName", type = AttributeIdNameXmldsigOptionType.class)
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
     * {@link AddKeyValXmldsigOptionType }
     * {@link AddSubjectNameXmldsigOptionType }
     * {@link ElementsNameXmldsigOptionType }
     * {@link ElementsIdXmldsigOptionType }
     * {@link MultipleSignaturesXmldsigOptionType }
     * {@link AttributeIdNameXmldsigOptionType }
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
