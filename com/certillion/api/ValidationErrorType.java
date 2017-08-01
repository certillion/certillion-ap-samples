
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ValidationErrorType.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ValidationErrorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TRANSPORT_ERROR"/>
 *     &lt;enumeration value="ENCODING_ERROR"/>
 *     &lt;enumeration value="INCOMPLETE_CERTIFICATE_CHAIN_ERROR"/>
 *     &lt;enumeration value="INVALID_CERTIFICATE_CHAIN_ERROR"/>
 *     &lt;enumeration value="UNSUPORTED_ALGORITHM_ERROR"/>
 *     &lt;enumeration value="SIGNING_POLICY_VERIFICATION_ERROR"/>
 *     &lt;enumeration value="CRYPTOGRAPHIC_ERROR"/>
 *     &lt;enumeration value="UNEXPECTED_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ValidationErrorType")
@XmlEnum
public enum ValidationErrorType {

    TRANSPORT_ERROR,
    ENCODING_ERROR,
    INCOMPLETE_CERTIFICATE_CHAIN_ERROR,
    INVALID_CERTIFICATE_CHAIN_ERROR,
    UNSUPORTED_ALGORITHM_ERROR,
    SIGNING_POLICY_VERIFICATION_ERROR,
    CRYPTOGRAPHIC_ERROR,
    UNEXPECTED_ERROR;

    public String value() {
        return name();
    }

    public static ValidationErrorType fromValue(String v) {
        return valueOf(v);
    }

}
