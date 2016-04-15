
package br.com.esec.mss.ap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchSignatureTIDsRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchSignatureTIDsRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://esec.com.br/mss/ap}StatusType"/>
 *         &lt;element name="DocumentSignatureStatus" type="{http://esec.com.br/mss/ap}DocumentSignatureStatusInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchSignatureTIDsRespType", propOrder = {
    "status",
    "documentSignatureStatus"
})
public class BatchSignatureTIDsRespType {

    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "DocumentSignatureStatus")
    protected List<DocumentSignatureStatusInfoType> documentSignatureStatus;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
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
     * {@link DocumentSignatureStatusInfoType }
     * 
     * 
     */
    public List<DocumentSignatureStatusInfoType> getDocumentSignatureStatus() {
        if (documentSignatureStatus == null) {
            documentSignatureStatus = new ArrayList<DocumentSignatureStatusInfoType>();
        }
        return this.documentSignatureStatus;
    }

}
