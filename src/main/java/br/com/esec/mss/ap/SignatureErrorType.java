
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureErrorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureErrorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALID_CERTIFICATE_ERROR"/>
 *     &lt;enumeration value="SIGNING_POLICY_ERROR"/>
 *     &lt;enumeration value="CERTIFICATE_REVOKED_ERROR"/>
 *     &lt;enumeration value="INVALID_TIMESTAMP_ERROR"/>
 *     &lt;enumeration value="WRONG_NUMBER_OF_ATTRIBUTES_ERROR"/>
 *     &lt;enumeration value="UNKNOWN_ERROR"/>
 *     &lt;enumeration value="NOT_BEFORE_ERROR"/>
 *     &lt;enumeration value="NOT_AFTER_ERROR"/>
 *     &lt;enumeration value="NOT_TRUSTED_CERTIFICATE_ERROR"/>
 *     &lt;enumeration value="DIGEST_VERIFICATION_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureErrorType")
@XmlEnum
public enum SignatureErrorType {

    INVALID_CERTIFICATE_ERROR,
    SIGNING_POLICY_ERROR,
    CERTIFICATE_REVOKED_ERROR,
    INVALID_TIMESTAMP_ERROR,
    WRONG_NUMBER_OF_ATTRIBUTES_ERROR,
    UNKNOWN_ERROR,
    NOT_BEFORE_ERROR,
    NOT_AFTER_ERROR,
    NOT_TRUSTED_CERTIFICATE_ERROR,
    DIGEST_VERIFICATION_ERROR;

    public String value() {
        return name();
    }

    public static SignatureErrorType fromValue(String v) {
        return valueOf(v);
    }

}
