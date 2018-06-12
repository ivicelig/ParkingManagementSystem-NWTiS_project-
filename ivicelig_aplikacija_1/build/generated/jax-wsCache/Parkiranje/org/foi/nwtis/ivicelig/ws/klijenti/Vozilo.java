
package org.foi.nwtis.ivicelig.ws.klijenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vozilo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vozilo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="akcija" type="{http://serveri.ws.dkermek.nwtis.foi.org/}statusVozila" minOccurs="0"/>
 *         &lt;element name="parkiraliste" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="registracija" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vozilo", propOrder = {
    "akcija",
    "parkiraliste",
    "registracija"
})
public class Vozilo {

    protected StatusVozila akcija;
    protected int parkiraliste;
    protected String registracija;

    /**
     * Gets the value of the akcija property.
     * 
     * @return
     *     possible object is
     *     {@link StatusVozila }
     *     
     */
    public StatusVozila getAkcija() {
        return akcija;
    }

    /**
     * Sets the value of the akcija property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusVozila }
     *     
     */
    public void setAkcija(StatusVozila value) {
        this.akcija = value;
    }

    /**
     * Gets the value of the parkiraliste property.
     * 
     */
    public int getParkiraliste() {
        return parkiraliste;
    }

    /**
     * Sets the value of the parkiraliste property.
     * 
     */
    public void setParkiraliste(int value) {
        this.parkiraliste = value;
    }

    /**
     * Gets the value of the registracija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistracija() {
        return registracija;
    }

    /**
     * Sets the value of the registracija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistracija(String value) {
        this.registracija = value;
    }

}
