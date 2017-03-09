
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BandwidthInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BandwidthInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalBandwidth" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="UsedBandwidth" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="DateToRenew" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BandwidthInfoType", propOrder = {
    "totalBandwidth",
    "usedBandwidth",
    "dateToRenew"
})
public class BandwidthInfoType {

    @XmlElement(name = "TotalBandwidth")
    protected long totalBandwidth;
    @XmlElement(name = "UsedBandwidth")
    protected long usedBandwidth;
    @XmlElement(name = "DateToRenew", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateToRenew;

    /**
     * Gets the value of the totalBandwidth property.
     * 
     */
    public long getTotalBandwidth() {
        return totalBandwidth;
    }

    /**
     * Sets the value of the totalBandwidth property.
     * 
     */
    public void setTotalBandwidth(long value) {
        this.totalBandwidth = value;
    }

    /**
     * Gets the value of the usedBandwidth property.
     * 
     */
    public long getUsedBandwidth() {
        return usedBandwidth;
    }

    /**
     * Sets the value of the usedBandwidth property.
     * 
     */
    public void setUsedBandwidth(long value) {
        this.usedBandwidth = value;
    }

    /**
     * Gets the value of the dateToRenew property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateToRenew() {
        return dateToRenew;
    }

    /**
     * Sets the value of the dateToRenew property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateToRenew(XMLGregorianCalendar value) {
        this.dateToRenew = value;
    }

}
