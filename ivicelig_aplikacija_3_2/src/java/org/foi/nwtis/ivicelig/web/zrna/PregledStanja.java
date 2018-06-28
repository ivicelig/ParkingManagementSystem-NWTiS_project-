/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.ivicelig.ejb.sb.SingletonSessionBean;
import org.foi.nwtis.ivicelig.json.MQTTjson;
import org.foi.nwtis.ivicelig.json.MQTTkorisnik;

/**
 *
 * @author Ivica
 */
@Named(value = "pregledStanja")
@SessionScoped
public class PregledStanja implements Serializable {

    private List<MQTTjson> listaPoruka = new ArrayList<>();
    private List<Stanje> listaStanja = new ArrayList<>();

    public List<Stanje> getListaStanja() {
        return listaStanja;
    }

    public void setListaStanja(List<Stanje> listaStanja) {
        this.listaStanja = listaStanja;
    }

    public List<MQTTjson> getListaPoruka() {
        return listaPoruka;
    }

    public void setListaPoruka(List<MQTTjson> listaPoruka) {
        this.listaPoruka = listaPoruka;

    }

    @EJB
    private SingletonSessionBean singletonSessionBean;

    /**
     * Creates a new instance of PregledStanja
     */
    public PregledStanja() {
        
    }

    private String dohvatiKorisnika() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session
                = (HttpSession) context.getExternalContext().getSession(true);
        session.getAttributeNames();

        if (session.getAttribute("user") == null) {
            return "";
        } else {
            return session.getAttribute("user").toString();
        }

    }

    public void dohvatiPorukeIzSingleton() {
        listaPoruka.clear();
        listaStanja.clear();
        List<MQTTkorisnik> temp = singletonSessionBean.getMqttPoruke();
        
        for (MQTTkorisnik mQTTkorisnik : temp) {
            listaPoruka.add(mQTTkorisnik.getMj());
        }
        for (MQTTkorisnik mQTTkorisnik : temp) {
            if (mQTTkorisnik.getKorisnik().equals(dohvatiKorisnika())) {

                
                if (!daliPostojiParkiraliste(mQTTkorisnik.getMj().getParkiraliste())) {
                    listaStanja.add(new Stanje(mQTTkorisnik.getMj().getParkiraliste(), 0));
                } else {
                    for (Stanje stanje : listaStanja) {
                        if (stanje.getId() == mQTTkorisnik.getMj().getParkiraliste()) {
                            if (mQTTkorisnik.getMj().getAkcija() == 0) {
                                stanje.setStanje(stanje.getStanje() + 1);
                            } else {
                                stanje.setStanje(stanje.getStanje() - 1);
                            }
                        }
                    }
                }

            }

        }
    }

    private boolean daliPostojiParkiraliste(int id) {
        boolean postoji = false;
        for (Stanje stanje : listaStanja) {
            if (stanje.getId() == id) {
                postoji = true;
                break;
            }
        }
        return postoji;
    }
    @PostConstruct
    void init() {
        dohvatiPorukeIzSingleton();
        
    }

}
