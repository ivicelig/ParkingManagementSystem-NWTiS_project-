/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.foi.nwtis.ivicelig.ejb.eb.MqttPoruke;
import org.foi.nwtis.ivicelig.ejb.sb.MqttPorukeFacade;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.listener.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
@Named(value = "mQTT")
@SessionScoped
public class MQTT implements Serializable {
private Konfiguracija konfig;

    @EJB
    private MqttPorukeFacade mqttPorukeFacade;
    private int brojStranica;

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }
    

    @PostConstruct
    public void init() {

        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
            brojStranica = Integer.parseInt(konfig.dajPostavku("stranicenje"));
            dohvatiPoruke();
    }
    private List<MqttPoruke> poruke;

    public List<MqttPoruke> getPoruke() {
        return poruke;
    }

    public void setPoruke(List<MqttPoruke> poruke) {
        this.poruke = poruke;
    }

    public MQTT() {
    }

    public String dohvatiPoruke() {
        poruke = mqttPorukeFacade.findAll();
        return "";
    }

}
