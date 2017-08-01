
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de OwnerType.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="OwnerType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PERSON"/>
 *     &lt;enumeration value="COMPANY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OwnerType")
@XmlEnum
public enum OwnerType {

    PERSON,
    COMPANY;

    public String value() {
        return name();
    }

    public static OwnerType fromValue(String v) {
        return valueOf(v);
    }

}
