
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de BandwidthInfoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
     * Obtém o valor da propriedade totalBandwidth.
     * 
     */
    public long getTotalBandwidth() {
        return totalBandwidth;
    }

    /**
     * Define o valor da propriedade totalBandwidth.
     * 
     */
    public void setTotalBandwidth(long value) {
        this.totalBandwidth = value;
    }

    /**
     * Obtém o valor da propriedade usedBandwidth.
     * 
     */
    public long getUsedBandwidth() {
        return usedBandwidth;
    }

    /**
     * Define o valor da propriedade usedBandwidth.
     * 
     */
    public void setUsedBandwidth(long value) {
        this.usedBandwidth = value;
    }

    /**
     * Obtém o valor da propriedade dateToRenew.
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
     * Define o valor da propriedade dateToRenew.
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
