package org.foi.nwtis.ivicelig.web.slusaci;

import java.util.List;
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
import org.foi.nwtis.ivicelig.web.baza.TablicaParkiraliste;
import org.foi.nwtis.ivicelig.web.dretve.KomunikacijskaDretva;
import org.foi.nwtis.ivicelig.web.dretve.MeteoDretva;
import org.foi.nwtis.ivicelig.web.podaci.Lokacija;
import org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste;

@WebListener
public class SlusacAplikacije implements ServletContextListener {

    public static ServletContext sc;
    public static KomunikacijskaDretva kd;
    public static MeteoDretva md;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sc = sce.getServletContext();
        try {
            dohvatiIpostaviKonfiguraciju();
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        sinkronizirajPodatkeUtabliciSWebServisom();
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
