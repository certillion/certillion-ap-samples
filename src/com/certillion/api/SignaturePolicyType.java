
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignaturePolicyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SignaturePolicyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AD_RB"/>
 *     &lt;enumeration value="AD_RT"/>
 *     &lt;enumeration value="AD_RC"/>
 *     &lt;enumeration value="AD_RV"/>
 *     &lt;enumeration value="AD_RA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignaturePolicyType")
@XmlEnum
public enum SignaturePolicyType {

    AD_RB,
    AD_RT,
    AD_RC,
    AD_RV,
    AD_RA;

    public String value() {
        return name();
    }

    public static SignaturePolicyType fromValue(String v) {
        return valueOf(v);
    }

}
