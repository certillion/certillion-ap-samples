
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindUserInfoRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindUserInfoRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Devices" type="{http://esec.com.br/mss/ap}MobileUserInfoTypeV2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Users" type="{http://esec.com.br/mss/ap}PersonInfoTypeV2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindUserInfoRespType", propOrder = {
    "devices",
    "users"
})
public class FindUserInfoRespType {

    @XmlElement(name = "Devices")
    protected List<MobileUserInfoTypeV2> devices;
    @XmlElement(name = "Users")
    protected List<PersonInfoTypeV2> users;

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

    /**
     * Gets the value of the users property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the users property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PersonInfoTypeV2 }
     * 
     * 
     */
    public List<PersonInfoTypeV2> getUsers() {
        if (users == null) {
            users = new ArrayList<PersonInfoTypeV2>();
        }
        return this.users;
    }

}
