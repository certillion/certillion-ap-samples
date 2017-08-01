
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SignatureModeType.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureModeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RSA"/>
 *     &lt;enumeration value="ECC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureModeType")
@XmlEnum
public enum SignatureModeType {

    RSA,
    ECC;

    public String value() {
        return name();
    }

    public static SignatureModeType fromValue(String v) {
        return valueOf(v);
    }

}
