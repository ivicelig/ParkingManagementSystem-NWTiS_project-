/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.dretve;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.web.baza.TablicaParkiraliste;
import org.foi.nwtis.ivicelig.web.podaci.Lokacija;
import org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste;

/**
 *
 * @author Ivica
 */
public class SinkronizacijskaDretva extends Thread{
    private boolean radi = true;
    @Override
    public void interrupt() {
        radi = false;
    }

    @Override
    public void run() {
        while(radi){
            sinkronizirajPodatkeUtabliciSWebServisom();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(SinkronizacijskaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    private void sinkronizirajPodatkeUtabliciSWebServisom() {
        TablicaParkiraliste tp = new TablicaParkiraliste();
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> parkiralista = dajSvaParkiralistaGrupe("ivicelig", "656989");
        List<org.foi.nwtis.ivicelig.web.podaci.Parkiraliste> parkiralistaBaze = tp.getAllRecords();
        if (parkiralistaBaze.isEmpty()) {
            for (Parkiraliste parkiraliste : parkiralista) {
                npraviObjektIUpišiUBazu(parkiraliste, tp);
            }
        } else {
            //Update for
            for (org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste : parkiralista) {
                boolean postoji = false;
                for (org.foi.nwtis.ivicelig.web.podaci.Parkiraliste parkiralisteBaze : parkiralistaBaze) {
                    if (parkiralisteBaze.getId() == parkiraliste.getId()) {
                        //TODO if postoji onda provjeri dali su isti ako nisu onda ga ažuiraj
                        if((parkiralisteBaze.getAdresa().equals(parkiraliste.getAdresa())) &&
                                (parkiralisteBaze.getAdresa().equals(parkiraliste.getAdresa()))&&
                                (parkiralisteBaze.getNaziv().equals(parkiraliste.getNaziv()))&&
                                (parkiralisteBaze.getAdresa().equals(parkiraliste.getAdresa()))&&
                                (parkiralisteBaze.getGeoloc().getLatitude().equals(parkiraliste.getGeoloc().getLatitude()))&&
                                (parkiralisteBaze.getGeoloc().getLongitude().equals(parkiraliste.getGeoloc().getLongitude()))){
                            
                            
                        }else{
                            tp.update(parkiraliste);
                        }
                        postoji = true;
                        break;
                    }
                }
                if (!postoji) {
                    npraviObjektIUpišiUBazu(parkiraliste, tp);
                }
            }
            //Delete for

        }
        for (org.foi.nwtis.ivicelig.web.podaci.Parkiraliste parkiralisteBaze : parkiralistaBaze) {
            boolean postoji = false;
            for (org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste : parkiralista) {
                if (parkiralisteBaze.getId() == parkiraliste.getId()) {
                    postoji = true;
                    break;
                }
            }
            if (!postoji) {
                tp.deleteByID(parkiralisteBaze.getId());
            }
        }

    }

    private void npraviObjektIUpišiUBazu(Parkiraliste parkiraliste, TablicaParkiraliste tp) {
        org.foi.nwtis.ivicelig.web.podaci.Parkiraliste bazaParkiraliste = new org.foi.nwtis.ivicelig.web.podaci.Parkiraliste();
        bazaParkiraliste.setId(parkiraliste.getId());
        bazaParkiraliste.setAdresa(parkiraliste.getAdresa());
        bazaParkiraliste.setNaziv(parkiraliste.getNaziv());
        Lokacija l = new Lokacija();
        l.setLatitude(parkiraliste.getGeoloc().getLatitude());
        l.setLongitude(parkiraliste.getGeoloc().getLongitude());
        bazaParkiraliste.setGeoloc(l);
        tp.insert(bazaParkiraliste);
    }
    private static java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> dajSvaParkiralistaGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.dajSvaParkiralistaGrupe(korisnickoIme, korisnickaLozinka);
    }

    
}
