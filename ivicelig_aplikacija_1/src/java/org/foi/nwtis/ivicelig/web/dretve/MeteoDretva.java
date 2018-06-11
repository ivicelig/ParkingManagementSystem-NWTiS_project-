package org.foi.nwtis.ivicelig.web.dretve;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.rest.klijenti.OWMKlijent;
import org.foi.nwtis.ivicelig.web.baza.TablicaMeteo;
import org.foi.nwtis.ivicelig.web.baza.TablicaParkiraliste;
import org.foi.nwtis.ivicelig.web.json.ResponseJSON;
import org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci;
import org.foi.nwtis.ivicelig.web.podaci.Parkiraliste;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
public class MeteoDretva extends Thread {

    Konfiguracija konfiguracija;
    public  boolean radi = true;
    public  boolean pasivno = false;
    @Override
    public void interrupt() {
        radi = false;
    }

    @Override
    public void run() {
        while (radi) {
            System.out.println("1 ciklus!");
            while(pasivno){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            TablicaParkiraliste tp = new TablicaParkiraliste();
            TablicaMeteo tm = new TablicaMeteo();
            
            OWMKlijent owmKlijent = new OWMKlijent(konfiguracija.dajPostavku("apikey"));
            List<Parkiraliste> parkiralista = tp.getAllRecords();
            for (Parkiraliste parkiraliste : parkiralista) {
                ResponseJSON rj = owmKlijent.getRealTimeWeather(parkiraliste.getGeoloc().getLatitude(), parkiraliste.getGeoloc().getLongitude());
                MeteoPodaci mp = new MeteoPodaci(rj,parkiraliste.getId());
                tm.insert(mp);
            }

            try {
                Thread.sleep(Integer.parseInt(konfiguracija.dajPostavku("interval.preuzimanje.podataka")) * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        konfiguracija = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
    }

}
