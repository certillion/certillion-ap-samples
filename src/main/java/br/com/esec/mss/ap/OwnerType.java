
package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OwnerType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
