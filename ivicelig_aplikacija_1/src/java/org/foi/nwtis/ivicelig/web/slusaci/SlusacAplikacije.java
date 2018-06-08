package org.foi.nwtis.ivicelig.web.slusaci;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.ivicelig.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.ivicelig.konfiguracije.KonfiguracijaXML;
import org.foi.nwtis.ivicelig.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.ivicelig.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.ivicelig.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ivicelig.web.dretve.KomunikacijskaDretva;
import org.foi.nwtis.ivicelig.web.dretve.MeteoDretva;

@WebListener
public class SlusacAplikacije implements ServletContextListener {

    public static ServletContext sc;
    private KomunikacijskaDretva kd;
    private MeteoDretva md;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sc = sce.getServletContext();
        try {
            dohvatiIpostaviKonfiguraciju();
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }

        kd = new KomunikacijskaDretva();
        kd.start();
        md = new MeteoDretva();
        md.start();

    }

    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        kd.interrupt();
        md.interrupt();
       

    }
    private void dohvatiIpostaviKonfiguraciju() throws NemaKonfiguracije, NeispravnaKonfiguracija {
        String datoteka = sc.getInitParameter("konfiguracija");
        String putanja = sc.getRealPath("/WEB-INF") + java.io.File.separator;
        String puniNazivDatoteke = putanja + datoteka;

        BP_Konfiguracija bpk = new BP_Konfiguracija(puniNazivDatoteke);
        KonfiguracijaApstraktna konfiguracija = new KonfiguracijaXML(puniNazivDatoteke);
        konfiguracija.ucitajKonfiguraciju();
        sc.setAttribute("BP_Konfig", bpk);
        sc.setAttribute("Konfiguracija", konfiguracija);
    }

}