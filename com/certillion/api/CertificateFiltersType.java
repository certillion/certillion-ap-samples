
package com.certillion.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CertificateFiltersType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
 *           &lt;element name="HsmCertificate" type="{http://esec.com.br/mss/ap}HsmCertificateFilterType"/>
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
        @XmlElement(name = "ForceHardware", type = ForceHardwareFilterType.class),
        @XmlElement(name = "HsmCertificate", type = HsmCertificateFilterType.class)
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
     * {@link HsmCertificateFilterType }
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
