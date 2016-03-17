
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureStandardType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureStandardType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cades"/>
 *     &lt;enumeration value="adobepdf"/>
 *     &lt;enumeration value="xades"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureStandardType")
@XmlEnum
public enum SignatureStandardType {

    @XmlEnumValue("cades")
    CADES("cades"),
    @XmlEnumValue("adobepdf")
    ADOBEPDF("adobepdf"),
    @XmlEnumValue("xades")
    XADES("xades");
    private final String value;

    SignatureStandardType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SignatureStandardType fromValue(String v) {
        for (SignatureStandardType c: SignatureStandardType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
