
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DigestMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DigestMethod">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SHA1"/>
 *     &lt;enumeration value="SHA256"/>
 *     &lt;enumeration value="SHA512"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DigestMethod")
@XmlEnum
public enum DigestMethod {

    @XmlEnumValue("SHA1")
    SHA_1("SHA1"),
    @XmlEnumValue("SHA256")
    SHA_256("SHA256"),
    @XmlEnumValue("SHA512")
    SHA_512("SHA512");
    private final String value;

    DigestMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DigestMethod fromValue(String v) {
        for (DigestMethod c: DigestMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
