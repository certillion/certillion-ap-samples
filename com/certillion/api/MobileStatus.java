
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de mobileStatus.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="mobileStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNREAD"/>
 *     &lt;enumeration value="READ"/>
 *     &lt;enumeration value="DONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "mobileStatus")
@XmlEnum
public enum MobileStatus {

    UNREAD,
    READ,
    DONE;

    public String value() {
        return name();
    }

    public static MobileStatus fromValue(String v) {
        return valueOf(v);
    }

}
