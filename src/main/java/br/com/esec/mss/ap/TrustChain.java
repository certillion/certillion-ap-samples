
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrustChain.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TrustChain">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ICPBR"/>
 *     &lt;enumeration value="JUS"/>
 *     &lt;enumeration value="RFB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TrustChain")
@XmlEnum
public enum TrustChain {

    ICPBR,
    JUS,
    RFB;

    public String value() {
        return name();
    }

    public static TrustChain fromValue(String v) {
        return valueOf(v);
    }

}
