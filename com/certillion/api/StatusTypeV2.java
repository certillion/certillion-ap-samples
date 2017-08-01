
package com.certillion.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de StatusTypeV2 complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="StatusTypeV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StatusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobileStatus" type="{http://esec.com.br/mss/ap}mobileStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusTypeV2", propOrder = {
    "statusCode",
    "statusMessage",
    "statusDetail",
    "mobileStatus"
})
public class StatusTypeV2 {

    @XmlElement(name = "StatusCode")
    protected int statusCode;
    @XmlElement(name = "StatusMessage")
    protected String statusMessage;
    @XmlElement(name = "StatusDetail")
    protected String statusDetail;
    @XmlElement(name = "MobileStatus")
    @XmlSchemaType(name = "string")
    protected MobileStatus mobileStatus;

    /**
     * Obtém o valor da propriedade statusCode.
     * 
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Define o valor da propriedade statusCode.
     * 
     */
    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    /**
     * Obtém o valor da propriedade statusMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Define o valor da propriedade statusMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusMessage(String value) {
        this.statusMessage = value;
    }

    /**
     * Obtém o valor da propriedade statusDetail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDetail() {
        return statusDetail;
    }

    /**
     * Define o valor da propriedade statusDetail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDetail(String value) {
        this.statusDetail = value;
    }

    /**
     * Obtém o valor da propriedade mobileStatus.
     * 
     * @return
     *     possible object is
     *     {@link MobileStatus }
     *     
     */
    public MobileStatus getMobileStatus() {
        return mobileStatus;
    }

    /**
     * Define o valor da propriedade mobileStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link MobileStatus }
     *     
     */
    public void setMobileStatus(MobileStatus value) {
        this.mobileStatus = value;
    }

}
