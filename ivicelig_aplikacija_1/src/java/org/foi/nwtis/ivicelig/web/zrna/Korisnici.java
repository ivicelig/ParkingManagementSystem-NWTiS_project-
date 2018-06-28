/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.zrna;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import org.foi.nwtis.ivicelig.web.baza.TablicaKorisnici;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;

import javax.faces.bean.ManagedBean;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
@ManagedBean(name = "korisnici")
@SessionScoped
public class Korisnici implements Serializable {

    private Konfiguracija konfig;
    private List<Korisnik> korisnici;
    private TablicaKorisnici tk = new TablicaKorisnici();

    public List<Korisnik> getKorisnici() {

        return korisnici;
    }

    public Korisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {

        this.korisnici = korisnici;
    }

    /**
     * Creates a new instance of Korisnici
     */
    public Korisnici() {

        korisnici = tk.getAllRecords();
    }

    @PostConstruct
    public void init() {

        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        korisnici = tk.getAllRecords();
    }

}
