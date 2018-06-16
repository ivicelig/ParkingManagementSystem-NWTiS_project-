/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import org.foi.nwtis.ivicelig.ejb.eb.KorisnikParkiraliste;
import org.foi.nwtis.ivicelig.ejb.sb.KorisnikParkiralisteFacade;
import org.foi.nwtis.ivicelig.rest.klijent.ParkiralistaREST;

/**
 *
 * @author Ivica
 */
@Named(value = "parkiralista")
@SessionScoped
public class Parkiralista implements Serializable {

    @EJB
    private KorisnikParkiralisteFacade korisnikParkiralisteFacade;
    
    /**
     * Creates a new instance of Parkiralista
     */
    
    public Parkiralista() {
    }
    
    
     public String dodajKorisnikParkiraliste() {
        try {
            KorisnikParkiraliste kp = new KorisnikParkiraliste();
            kp.setKorisnickoIme("ivicelig");
            
            korisnikParkiralisteFacade.create(kp);
            
            
        } catch (Exception e) {
            
        }
        return "";
    }
    
    public void test(){
      ParkiralistaREST pr = new ParkiralistaREST();
       pr.getJson(String.class);
//         MeteoRESTKlijent mrsk = new MeteoRESTKlijent();
//        String zahtjev = "{\"naziv\": \""+naziv+"\","
//                + " \"adresa\": \""+adresa+"\"}";
        String odgovor = pr.getJson(String.class);

        System.out.println("REST odgovor: "+odgovor);
//        
    }
    
}
