
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonInfoTypeV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonInfoTypeV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://esec.com.br/mss/ap}StatusTypeV2"/>
 *         &lt;element name="Devices" type="{http://esec.com.br/mss/ap}MobileUserInfoTypeV2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonInfoTypeV2", propOrder = {
    "status",
    "devices"
})
public class PersonInfoTypeV2 {

    @XmlElement(name = "Status", required = true)
    protected StatusTypeV2 status;
    @XmlElement(name = "Devices")
    protected List<MobileUserInfoTypeV2> devices;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusTypeV2 }
     *     
     */
    public StatusTypeV2 getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusTypeV2 }
     *     
     */
    public void setStatus(StatusTypeV2 value) {
        this.status = value;
    }

    /**
     * Gets the value of the devices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the devices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDevices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MobileUserInfoTypeV2 }
     * 
     * 
     */
    public List<MobileUserInfoTypeV2> getDevices() {
        if (devices == null) {
            devices = new ArrayList<MobileUserInfoTypeV2>();
        }
        return this.devices;
    }

}
