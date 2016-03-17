
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DateType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://esec.com.br/mss/ap}templateDataType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateType")
public class DateType
    extends TemplateDataType
{

    @XmlAttribute(name = "Value", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValue(XMLGregorianCalendar value) {
        this.value = value;
    }

}
