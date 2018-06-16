package org.foi.nwtis.ivicelig.web.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.ivicelig.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ivicelig.konfiguracije.KonfiguracijaXML;
import org.foi.nwtis.ivicelig.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.ivicelig.konfiguracije.NemaKonfiguracije;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Web application lifecycle listener.
 *
 * @author Ivica
 */
public class SlusacAplikacije implements ServletContextListener {

    public static ServletContext sc;
    

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sc = sce.getServletContext();
        try {
            dohvatiIpostaviKonfiguraciju();
            
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       
    }

    private void dohvatiIpostaviKonfiguraciju() throws NemaKonfiguracije, NeispravnaKonfiguracija {
        String datoteka = sc.getInitParameter("konfiguracija");
        String putanja = sc.getRealPath("/WEB-INF") + java.io.File.separator;
        String puniNazivDatoteke = putanja + datoteka;

        KonfiguracijaApstraktna konfiguracija = new KonfiguracijaXML(puniNazivDatoteke);
        konfiguracija.ucitajKonfiguraciju();

        sc.setAttribute("Konfiguracija", konfiguracija);
    }
}
