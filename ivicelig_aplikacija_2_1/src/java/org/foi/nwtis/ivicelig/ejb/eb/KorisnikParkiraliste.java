/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ivica
 */
@Entity
@Table(name = "KORISNIK_PARKIRALISTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KorisnikParkiraliste.findAll", query = "SELECT k FROM KorisnikParkiraliste k")
    , @NamedQuery(name = "KorisnikParkiraliste.findByParkiralisteId", query = "SELECT k FROM KorisnikParkiraliste k WHERE k.parkiralisteId = :parkiralisteId")
    , @NamedQuery(name = "KorisnikParkiraliste.findByKorisnickoIme", query = "SELECT k FROM KorisnikParkiraliste k WHERE k.korisnickoIme = :korisnickoIme")})
public class KorisnikParkiraliste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARKIRALISTE_ID")
    private Integer parkiralisteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "KORISNICKO_IME")
    private String korisnickoIme;

    public KorisnikParkiraliste() {
    }

    public KorisnikParkiraliste(Integer parkiralisteId) {
        this.parkiralisteId = parkiralisteId;
    }

    public KorisnikParkiraliste(Integer parkiralisteId, String korisnickoIme) {
        this.parkiralisteId = parkiralisteId;
        this.korisnickoIme = korisnickoIme;
    }

    public Integer getParkiralisteId() {
        return parkiralisteId;
    }

    public void setParkiralisteId(Integer parkiralisteId) {
        this.parkiralisteId = parkiralisteId;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parkiralisteId != null ? parkiralisteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KorisnikParkiraliste)) {
            return false;
        }
        KorisnikParkiraliste other = (KorisnikParkiraliste) object;
        if ((this.parkiralisteId == null && other.parkiralisteId != null) || (this.parkiralisteId != null && !this.parkiralisteId.equals(other.parkiralisteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.ivicelig.ejb.eb.KorisnikParkiraliste[ parkiralisteId=" + parkiralisteId + " ]";
    }
    
}
