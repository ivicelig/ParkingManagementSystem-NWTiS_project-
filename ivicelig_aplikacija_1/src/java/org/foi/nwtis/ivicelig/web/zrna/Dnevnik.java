/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.baza.TablicaDnevnik;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
@ManagedBean(name = "dnevnik")
@RequestScoped
public class Dnevnik implements Serializable {

    private Konfiguracija konfig;
    private List<org.foi.nwtis.ivicelig.web.podaci.Dnevnik> dnevnik = new ArrayList<>();
    private TablicaDnevnik td = new TablicaDnevnik();

    public Dnevnik() {
        dnevnik = td.getAllRecords();
    }

    public List<org.foi.nwtis.ivicelig.web.podaci.Dnevnik> getDnevnik() {
        return dnevnik;
    }

    public void setDnevnik(List<org.foi.nwtis.ivicelig.web.podaci.Dnevnik> dnevnik) {
        this.dnevnik = dnevnik;
    }

    public TablicaDnevnik getTd() {
        return td;
    }

    public void setTd(TablicaDnevnik td) {
        this.td = td;
    }

    @PostConstruct
    public void init() {

        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        dnevnik = td.getAllRecords();
    }

}
