
package com.certillion.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TokenCmdStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TokenCmdStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TokenCmdStatus")
@XmlEnum
public enum TokenCmdStatus {

    OK,
    ERROR;

    public String value() {
        return name();
    }

    public static TokenCmdStatus fromValue(String v) {
        return valueOf(v);
    }

}
