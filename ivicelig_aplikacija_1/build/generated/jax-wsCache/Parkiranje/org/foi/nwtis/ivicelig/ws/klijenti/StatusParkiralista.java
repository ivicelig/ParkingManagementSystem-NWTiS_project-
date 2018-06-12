
package org.foi.nwtis.ivicelig.ws.klijenti;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statusParkiralista.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statusParkiralista">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PASIVAN"/>
 *     &lt;enumeration value="AKTIVAN"/>
 *     &lt;enumeration value="BLOKIRAN"/>
 *     &lt;enumeration value="NEPOSTOJI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statusParkiralista")
@XmlEnum
public enum StatusParkiralista {

    PASIVAN,
    AKTIVAN,
    BLOKIRAN,
    NEPOSTOJI;

    public String value() {
        return name();
    }

    public static StatusParkiralista fromValue(String v) {
        return valueOf(v);
    }

}
