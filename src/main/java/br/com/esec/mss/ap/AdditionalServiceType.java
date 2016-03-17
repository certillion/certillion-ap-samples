
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdditionalServiceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdditionalServiceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="dummy"/>
 *     &lt;enumeration value="offlineSignature"/>
 *     &lt;enumeration value="aetDriver"/>
 *     &lt;enumeration value="kaspersky"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdditionalServiceType")
@XmlEnum
public enum AdditionalServiceType {

    @XmlEnumValue("dummy")
    DUMMY("dummy"),
    @XmlEnumValue("offlineSignature")
    OFFLINE_SIGNATURE("offlineSignature"),
    @XmlEnumValue("aetDriver")
    AET_DRIVER("aetDriver"),
    @XmlEnumValue("kaspersky")
    KASPERSKY("kaspersky");
    private final String value;

    AdditionalServiceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdditionalServiceType fromValue(String v) {
        for (AdditionalServiceType c: AdditionalServiceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
