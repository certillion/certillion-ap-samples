
package br.com.esec.mss.ap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CertificateFiltersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CertificateFiltersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="TrustChain" type="{http://esec.com.br/mss/ap}TrustChainFilterType"/>
 *           &lt;element name="OwnerCertificate" type="{http://esec.com.br/mss/ap}OwnerFilterType"/>
 *           &lt;element name="Algorithm" type="{http://esec.com.br/mss/ap}AlgorithmFilterType"/>
 *           &lt;element name="ForceHardware" type="{http://esec.com.br/mss/ap}ForceHardwareFilterType"/>
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
@XmlType(name = "CertificateFiltersType", propOrder = {
    "trustChainOrOwnerCertificateOrAlgorithm"
})
public class CertificateFiltersType {

    @XmlElements({
        @XmlElement(name = "TrustChain", type = TrustChainFilterType.class),
        @XmlElement(name = "OwnerCertificate", type = OwnerFilterType.class),
        @XmlElement(name = "Algorithm", type = AlgorithmFilterType.class),
        @XmlElement(name = "ForceHardware", type = ForceHardwareFilterType.class)
    })
    protected List<Object> trustChainOrOwnerCertificateOrAlgorithm;

    /**
     * Gets the value of the trustChainOrOwnerCertificateOrAlgorithm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trustChainOrOwnerCertificateOrAlgorithm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrustChainOrOwnerCertificateOrAlgorithm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrustChainFilterType }
     * {@link OwnerFilterType }
     * {@link AlgorithmFilterType }
     * {@link ForceHardwareFilterType }
     * 
     * 
     */
    public List<Object> getTrustChainOrOwnerCertificateOrAlgorithm() {
        if (trustChainOrOwnerCertificateOrAlgorithm == null) {
            trustChainOrOwnerCertificateOrAlgorithm = new ArrayList<Object>();
        }
        return this.trustChainOrOwnerCertificateOrAlgorithm;
    }

}
