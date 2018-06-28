/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import com.google.gson.Gson;
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
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.ivicelig.ejb.eb.KorisnikParkiraliste;
import org.foi.nwtis.ivicelig.ejb.sb.KorisnikParkiralisteFacade;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.rest.json.OdgovorParkiralista;
import org.foi.nwtis.ivicelig.rest.json.ParkiralistaForJson;
import org.foi.nwtis.ivicelig.rest.json.ParkiralisteClass;
import org.foi.nwtis.ivicelig.rest.json.Vehicle;
import org.foi.nwtis.ivicelig.rest.json.VehicleForJson;
import org.foi.nwtis.ivicelig.rest.klijent.ParkiralistaIDREST;
import org.foi.nwtis.ivicelig.rest.klijent.ParkiralistaVozilaREST;

import org.foi.nwtis.ivicelig.web.listener.SlusacAplikacije;
import org.foi.nwtis.ivicleig.ws.client.MeteoPodaci_Type;

/**
 *
 * @author Ivica
 */
@Named(value = "parkiralista")
@SessionScoped
public class Parkiralista implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8084/ivicelig_aplikacija_1/MeteoPodaci.wsdl")
    private org.foi.nwtis.ivicleig.ws.client.MeteoPodaci_Service service;

    private String nazivParkiralista;
    private String adresa;
    private Konfiguracija konfig;
    private String kapacitet;
    private String id;
    private String poruka = "";
    private List<String> odabranaParkiralista = new ArrayList<>();
    private List<String> popisParkiralista = new ArrayList<>();
    private String korisnik;
    private List<Vehicle> vozila = new ArrayList<>();
    private  List<org.foi.nwtis.ivicleig.ws.client.MeteoPodaci_Type> meteoPodaci = new ArrayList<MeteoPodaci_Type>();

    public List<MeteoPodaci_Type> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci_Type> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

   
    
    public List<Vehicle> getVozila() {
        return vozila;
    }

    public void setVozila(List<Vehicle> vozila) {
        this.vozila = vozila;
    }

    Gson gson = new Gson();

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public List<String> getOdabranaParkiralista() {
        return odabranaParkiralista;
    }

    public void setOdabranaParkiralista(List<String> odabranaParkiralista) {
        this.odabranaParkiralista = odabranaParkiralista;
    }

    public List<String> getPopisParkiralista() {
        popisParkiralista.clear();
        dodajParkiralisteUPopisParkiralista();
        return popisParkiralista;
    }

    public void setPopisParkiralista(List<String> popisParkiralista) {
        this.popisParkiralista = popisParkiralista;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNazivParkiralista() {
        return nazivParkiralista;
    }

    public void setNazivParkiralista(String nazivParkiralista) {
        this.nazivParkiralista = nazivParkiralista;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(String kapacitet) {
        this.kapacitet = kapacitet;
    }

    public KorisnikParkiralisteFacade getKorisnikParkiralisteFacade() {
        return korisnikParkiralisteFacade;
    }

    public void setKorisnikParkiralisteFacade(KorisnikParkiralisteFacade korisnikParkiralisteFacade) {
        this.korisnikParkiralisteFacade = korisnikParkiralisteFacade;
    }

    @EJB
    private KorisnikParkiralisteFacade korisnikParkiralisteFacade;

    /**
     * Creates a new instance of Parkiralista
     */
    public Parkiralista() {

    }

    @PostConstruct
    public void init() {

        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        dohvatiKorisnika();
        dodajParkiralisteUPopisParkiralista();
        //  <entry key="korIme">ivicelig</entry>
//<entry key="lozinka">656989</entry>

    }

    public String dodajNovoParkiraliste() {
        Gson gson = new Gson();

        if (provjera()) {
            ParkiralistaIDREST pID = new ParkiralistaIDREST(id);
            ParkiralisteClass pc = new ParkiralisteClass();
            pc.setAdresa(adresa);
            pc.setKapacitet(Integer.parseInt(kapacitet));
            pc.setKorime(konfig.dajPostavku("korIme"));
            pc.setLozinka(konfig.dajPostavku("lozinka"));
            pc.setNaziv(nazivParkiralista);
            String json = gson.toJson(pc, ParkiralisteClass.class);
            System.out.println(json);
            String odgovor = pID.postJson(json, String.class);
            OdgovorParkiralista oc = gson.fromJson(odgovor, OdgovorParkiralista.class);
            if (oc.getStatus().equals("OK")) {
                dodajParkiralisteUPopisParkiralista();

                System.out.println("Dodavanje je uspijelo");
                poruka = "Dodavanje je uspiješno!";
                //TODO dodaj u tablicu parkiraliste i korisnika spoj
                KorisnikParkiraliste kp = new KorisnikParkiraliste();
                kp.setKorisnickoIme(dohvatiKorisnika());

                kp.setParkiralisteId(Integer.parseInt(id));
                korisnikParkiralisteFacade.create(kp);

            } else {
                System.out.println("Id već postoji!");
                poruka = "Dodavanje nije uspiješno!";

            }

        }
        return "";
    }

    private void dodajParkiralisteUPopisParkiralista() {

        List<KorisnikParkiraliste> kp = korisnikParkiralisteFacade.getDataByKorisnik(dohvatiKorisnika());
        for (KorisnikParkiraliste korisnikParkiraliste : kp) {
            ParkiralistaIDREST pID = new ParkiralistaIDREST(Integer.toString(korisnikParkiraliste.getParkiralisteId()));
            String odgovor = pID.getJson(String.class);

            ParkiralistaForJson oc = gson.fromJson(odgovor, ParkiralistaForJson.class);

            popisParkiralista.add(Integer.toString(oc.getOdgovor().get(0).getId()).concat(";").concat(oc.getOdgovor().get(0).getAdresa()).concat(";"));

        }
    }

    private boolean provjera() {
        //TODO implementirati
        return true;
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

    public void postaviParkiralistaZaAzuiranje() {
        if (odabranaParkiralista.size() == 1) {
            String[] values = odabranaParkiralista.get(0).split(";");
            ParkiralistaIDREST pID = new ParkiralistaIDREST(values[0]);
            String odgovor = pID.getJson(String.class);

            ParkiralistaForJson oc = gson.fromJson(odgovor, ParkiralistaForJson.class);
            this.id = Integer.toString(oc.getOdgovor().get(0).getId());
            this.adresa = oc.getOdgovor().get(0).getAdresa();
            this.nazivParkiralista = oc.getOdgovor().get(0).getNaziv();
            this.kapacitet = Integer.toString(oc.getOdgovor().get(0).getKapacitet());
        }
    }

    public String azuirajParkiraliste() {
        Gson gson = new Gson();

        if (provjera()) {
            ParkiralistaIDREST pID = new ParkiralistaIDREST(id);
            ParkiralisteClass pc = new ParkiralisteClass();
            pc.setAdresa(adresa);
            pc.setKapacitet(Integer.parseInt(kapacitet));
            pc.setKorime(konfig.dajPostavku("korIme"));
            pc.setLozinka(konfig.dajPostavku("lozinka"));
            pc.setNaziv(nazivParkiralista);
            String json = gson.toJson(pc, ParkiralisteClass.class);
            System.out.println(json);
            String odgovor = pID.putJson(json, String.class);
            OdgovorParkiralista oc = gson.fromJson(odgovor, OdgovorParkiralista.class);
            if (oc.getStatus().equals("OK")) {
                dodajParkiralisteUPopisParkiralista();

                System.out.println("Azuiranje");
                poruka = "Azuiranje je uspiješno!";
                //TODO dodaj u tablicu parkiraliste i korisnika spoj

            } else {
                System.out.println("Id ne postoiji!");
                poruka = "Azuiranje nije uspiješno!";

            }

        }
        return "";
    }

    public String obrisiParkiraliste() {
        if (odabranaParkiralista.size() == 1) {
            String[] values = odabranaParkiralista.get(0).split(";");
            ParkiralistaIDREST pID = new ParkiralistaIDREST(values[0]);
            String odgovor = pID.deleteById(String.class);
            KorisnikParkiraliste kp = new KorisnikParkiraliste();
            kp.setKorisnickoIme(dohvatiKorisnika());

            kp.setParkiralisteId(Integer.parseInt(values[0]));
            korisnikParkiralisteFacade.remove(kp);
            dodajParkiralisteUPopisParkiralista();
        }

        return "";

    }

    public String dohvatiVozila() {
        if (odabranaParkiralista.size() == 1) {
            String[] values = odabranaParkiralista.get(0).split(";");
            ParkiralistaVozilaREST pvr = new ParkiralistaVozilaREST(values[0]);
            String odgovor = pvr.getJsonVozila(String.class);
            VehicleForJson vfj = gson.fromJson(odgovor, VehicleForJson.class);
            vozila = vfj.getOdgovor();
        }

        return "";

    }

    public String dohvatiVazeceMeteoPodatke() {
         if (odabranaParkiralista.size() == 1) {
             String[] values = odabranaParkiralista.get(0).split(";");
             meteoPodaci.add( vazeciMeteoPodaci("ivicelig", "123456", values[0]));
         }
        
        return "";
    }
    public String dohvatiZadnjePreuzeteMeteoPodatke() {
         if (odabranaParkiralista.size() == 1) {
             String[] values = odabranaParkiralista.get(0).split(";");
             meteoPodaci.add(zadnjePreuzetiMeteoPodaci("ivicelig", "123456", values[0]));
         }
        
        return "";
    }

    private org.foi.nwtis.ivicleig.ws.client.MeteoPodaci_Type vazeciMeteoPodaci(java.lang.String korime, java.lang.String lozinka, java.lang.String id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.ivicleig.ws.client.MeteoPodaci port = service.getMeteoPodaciPort();
        return port.vazeciMeteoPodaci(korime, lozinka, id);
    }

    private org.foi.nwtis.ivicleig.ws.client.MeteoPodaci_Type zadnjePreuzetiMeteoPodaci(java.lang.String korime, java.lang.String lozinka, java.lang.String id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.ivicleig.ws.client.MeteoPodaci port = service.getMeteoPodaciPort();
        return port.zadnjePreuzetiMeteoPodaci(korime, lozinka, id);
    }

}
