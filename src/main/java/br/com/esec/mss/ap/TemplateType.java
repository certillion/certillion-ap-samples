
package br.com.esec.mss.ap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TemplateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TemplateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{http://esec.com.br/mss/ap}DateType"/>
 *           &lt;element ref="{http://esec.com.br/mss/ap}DoubleType"/>
 *           &lt;element ref="{http://esec.com.br/mss/ap}IntegerType"/>
 *           &lt;element ref="{http://esec.com.br/mss/ap}StringType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="TemplateId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TemplateType", propOrder = {
    "dateTypeOrDoubleTypeOrIntegerType"
})
public class TemplateType {

    @XmlElements({
        @XmlElement(name = "DateType", namespace = "http://esec.com.br/mss/ap", type = DateType.class),
        @XmlElement(name = "DoubleType", namespace = "http://esec.com.br/mss/ap", type = DoubleType.class),
        @XmlElement(name = "IntegerType", namespace = "http://esec.com.br/mss/ap", type = IntegerType.class),
        @XmlElement(name = "StringType", namespace = "http://esec.com.br/mss/ap", type = StringType.class)
    })
    protected List<TemplateDataType> dateTypeOrDoubleTypeOrIntegerType;
    @XmlAttribute(name = "TemplateId", required = true)
    protected long templateId;

    /**
     * Gets the value of the dateTypeOrDoubleTypeOrIntegerType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dateTypeOrDoubleTypeOrIntegerType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDateTypeOrDoubleTypeOrIntegerType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateType }
     * {@link DoubleType }
     * {@link IntegerType }
     * {@link StringType }
     * 
     * 
     */
    public List<TemplateDataType> getDateTypeOrDoubleTypeOrIntegerType() {
        if (dateTypeOrDoubleTypeOrIntegerType == null) {
            dateTypeOrDoubleTypeOrIntegerType = new ArrayList<TemplateDataType>();
        }
        return this.dateTypeOrDoubleTypeOrIntegerType;
    }

    /**
     * Gets the value of the templateId property.
     * 
     */
    public long getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     */
    public void setTemplateId(long value) {
        this.templateId = value;
    }

}
