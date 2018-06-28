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
@Named(value = "jSFmqtt")
@SessionScoped
public class JSFmqtt implements Serializable {
    private List<MQTTjson> listaPoruka = new ArrayList<>();
    @EJB
    private SingletonSessionBean singletonSessionBean;

    
    public JSFmqtt() {
        
        
    }
     @PostConstruct
    void init() {
        dohvatiPorukeIzSingleton();
        
    }
    
    public void dohvatiPorukeIzSingleton(){
        listaPoruka.clear();
         List<MQTTkorisnik> temp = singletonSessionBean.getMqttPoruke();
        for (MQTTkorisnik mQTTkorisnik : temp) {
            if(mQTTkorisnik.getKorisnik().equals(dohvatiKorisnika())){
                listaPoruka.add(mQTTkorisnik.getMj());
            }
            
            }
    }
    

    public List<MQTTjson> getListaPoruka() {
        return listaPoruka;
    }

    public void setListaPoruka(List<MQTTjson> listaPoruka) {
        this.listaPoruka = listaPoruka;
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
}
