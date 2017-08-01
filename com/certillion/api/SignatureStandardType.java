
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SignatureStandardType.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureStandardType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cades"/>
 *     &lt;enumeration value="adobepdf"/>
 *     &lt;enumeration value="xades"/>
 *     &lt;enumeration value="xmldsig_enveloped"/>
 *     &lt;enumeration value="xmldsig_enveloping"/>
 *     &lt;enumeration value="pades"/>
 *     &lt;enumeration value="raw"/>
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
    XADES("xades"),
    @XmlEnumValue("xmldsig_enveloped")
    XMLDSIG_ENVELOPED("xmldsig_enveloped"),
    @XmlEnumValue("xmldsig_enveloping")
    XMLDSIG_ENVELOPING("xmldsig_enveloping"),
    @XmlEnumValue("pades")
    PADES("pades"),
    @XmlEnumValue("raw")
    RAW("raw");
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
