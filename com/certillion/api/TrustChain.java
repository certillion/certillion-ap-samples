
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de TrustChain.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
