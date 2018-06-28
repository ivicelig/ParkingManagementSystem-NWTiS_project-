/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.foi.nwtis.ivicelig.ejb.sb.DnevnikFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.listener.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
@ManagedBean(name = "dnevnik")
@RequestScoped
public class Dnevnik {

    private Konfiguracija konfig;
    private int brojZapisa;
    private List<org.foi.nwtis.ivicelig.ejb.eb.Dnevnik> zapisiDnevnika;

    public int getBrojZapisa() {
        return brojZapisa;
    }

    public void setBrojZapisa(int brojZapisa) {
        this.brojZapisa = brojZapisa;
    }

    public List<org.foi.nwtis.ivicelig.ejb.eb.Dnevnik> getZapisiDnevnika() {
        return zapisiDnevnika;
    }

    public void setZapisiDnevnika(List<org.foi.nwtis.ivicelig.ejb.eb.Dnevnik> zapisiDnevnika) {
        this.zapisiDnevnika = zapisiDnevnika;
    }
    @EJB
    private DnevnikFacade dnevnikFacade;

    public Dnevnik() {
    }

    @PostConstruct
    public void init() {

        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        brojZapisa = Integer.parseInt(konfig.dajPostavku("stranicenje"));
        zapisiDnevnika = dnevnikFacade.findAll();

    }

}
