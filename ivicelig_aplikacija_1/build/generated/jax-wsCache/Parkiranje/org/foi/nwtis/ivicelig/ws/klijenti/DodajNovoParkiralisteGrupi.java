
package org.foi.nwtis.ivicelig.ws.klijenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dodajNovoParkiralisteGrupi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dodajNovoParkiralisteGrupi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="korisnickoIme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="korisnickaLozinka" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idParkiraliste" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nazivParkiraliste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adresaParkiraliste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kapacitetParkiraliste" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dodajNovoParkiralisteGrupi", propOrder = {
    "korisnickoIme",
    "korisnickaLozinka",
    "idParkiraliste",
    "nazivParkiraliste",
    "adresaParkiraliste",
    "kapacitetParkiraliste"
})
public class DodajNovoParkiralisteGrupi {

    protected String korisnickoIme;
    protected String korisnickaLozinka;
    protected int idParkiraliste;
    protected String nazivParkiraliste;
    protected String adresaParkiraliste;
    protected int kapacitetParkiraliste;

    /**
     * Gets the value of the korisnickoIme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Sets the value of the korisnickoIme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorisnickoIme(String value) {
        this.korisnickoIme = value;
    }

    /**
     * Gets the value of the korisnickaLozinka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorisnickaLozinka() {
        return korisnickaLozinka;
    }

    /**
     * Sets the value of the korisnickaLozinka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorisnickaLozinka(String value) {
        this.korisnickaLozinka = value;
    }

    /**
     * Gets the value of the idParkiraliste property.
     * 
     */
    public int getIdParkiraliste() {
        return idParkiraliste;
    }

    /**
     * Sets the value of the idParkiraliste property.
     * 
     */
    public void setIdParkiraliste(int value) {
        this.idParkiraliste = value;
    }

    /**
     * Gets the value of the nazivParkiraliste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivParkiraliste() {
        return nazivParkiraliste;
    }

    /**
     * Sets the value of the nazivParkiraliste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivParkiraliste(String value) {
        this.nazivParkiraliste = value;
    }

    /**
     * Gets the value of the adresaParkiraliste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdresaParkiraliste() {
        return adresaParkiraliste;
    }

    /**
     * Sets the value of the adresaParkiraliste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdresaParkiraliste(String value) {
        this.adresaParkiraliste = value;
    }

    /**
     * Gets the value of the kapacitetParkiraliste property.
     * 
     */
    public int getKapacitetParkiraliste() {
        return kapacitetParkiraliste;
    }

    /**
     * Sets the value of the kapacitetParkiraliste property.
     * 
     */
    public void setKapacitetParkiraliste(int value) {
        this.kapacitetParkiraliste = value;
    }

}
