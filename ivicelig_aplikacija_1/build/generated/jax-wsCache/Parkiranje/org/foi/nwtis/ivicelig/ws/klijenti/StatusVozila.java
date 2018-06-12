
package org.foi.nwtis.ivicelig.ws.klijenti;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statusVozila.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statusVozila">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ULAZ"/>
 *     &lt;enumeration value="IZLAZ"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statusVozila")
@XmlEnum
public enum StatusVozila {

    ULAZ,
    IZLAZ;

    public String value() {
        return name();
    }

    public static StatusVozila fromValue(String v) {
        return valueOf(v);
    }

}
