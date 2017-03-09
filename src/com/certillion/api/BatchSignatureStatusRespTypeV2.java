
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchSignatureStatusRespTypeV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchSignatureStatusRespTypeV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://esec.com.br/mss/ap}StatusTypeV2"/>
 *         &lt;element name="DocumentSignatureStatus" type="{http://esec.com.br/mss/ap}DocumentSignatureStatusInfoTypeV3" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchSignatureStatusRespTypeV2", propOrder = {
    "status",
    "documentSignatureStatus"
})
public class BatchSignatureStatusRespTypeV2 {

    @XmlElement(name = "Status", required = true)
    protected StatusTypeV2 status;
    @XmlElement(name = "DocumentSignatureStatus")
    protected List<DocumentSignatureStatusInfoTypeV3> documentSignatureStatus;

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
     * Gets the value of the documentSignatureStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentSignatureStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentSignatureStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentSignatureStatusInfoTypeV3 }
     * 
     * 
     */
    public List<DocumentSignatureStatusInfoTypeV3> getDocumentSignatureStatus() {
        if (documentSignatureStatus == null) {
            documentSignatureStatus = new ArrayList<DocumentSignatureStatusInfoTypeV3>();
        }
        return this.documentSignatureStatus;
    }

}
