/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ws.serveri;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.rest.klijenti.OWMKlijent;
import org.foi.nwtis.ivicelig.web.baza.TablicaDnevnik;
import org.foi.nwtis.ivicelig.web.baza.TablicaKorisnici;
import org.foi.nwtis.ivicelig.web.baza.TablicaMeteo;
import org.foi.nwtis.ivicelig.web.baza.TablicaParkiraliste;
import org.foi.nwtis.ivicelig.web.json.ResponseJSON;
import org.foi.nwtis.ivicelig.web.podaci.Dnevnik;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;
import org.foi.nwtis.ivicelig.web.podaci.Parkiraliste;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
@WebService(serviceName = "MeteoPodaci")
public class MeteoPodaci {

    public MeteoPodaci() {

    }

    /**
     * This is a sample web service operation
     *
     * @param id
     * @return
     */
    @WebMethod(operationName = "zadnjePreuzetiMeteoPodaci")
    public org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci zadnjePreuzetiMeteoPodaci(@WebParam(name = "korime") String korime, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "id") String id) {
        TablicaMeteo tm = new TablicaMeteo();
        if (provjeriDaliKorisnikPostoji(korime)) {
            if (provjeriKorisnickoImeIlozinku(korime, lozinka)) {
                 TablicaDnevnik td = new TablicaDnevnik();
                Dnevnik d = new Dnevnik();
               d.setNaziv("SOAP");
               d.setOpis("zadnjePreuzetiMeteoPodaci");
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sCertDate = sdf.format(new Date());
               d.setVrijeme(sCertDate);
            td.insert(d);

                return tm.getLastRecordByID(id);
            }
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "posljednjaNmeteoPodatakaZaParkiraliste")
    public List<org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci> posljednjaNmeteoPodatakaZaParkiraliste(@WebParam(name = "korime") String korime, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "id") String id, @WebParam(name = "n") int n) {
        TablicaMeteo tm = new TablicaMeteo();
        if (provjeriDaliKorisnikPostoji(korime)) {
            if (provjeriKorisnickoImeIlozinku(korime, lozinka)) {
                List<org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci> listMP = tm.getN_LastRecordsByID(id, n);
                return listMP;
            }
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dohvatiMeteoPodatkeUintervalu")
    public List<org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci> dohvatiMeteoPodatkeUintervalu(@WebParam(name = "korime") String korime, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "id") String id,
            @WebParam(name = "od") long od,
            @WebParam(name = "Do") long Do
    ) {
        TablicaMeteo tm = new TablicaMeteo();
        if (provjeriDaliKorisnikPostoji(korime)) {
            if (provjeriKorisnickoImeIlozinku(korime, lozinka)) {
                Timestamp odTimeStamp = new Timestamp(od);
                Timestamp doTimeStamp = new Timestamp(Do);
                List<org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci> listMP = tm.vratiMeteoUINtervalu(id, odTimeStamp, doTimeStamp);
                return listMP;
            }
        }
        return null;
    }

    public class TimestampAdapter extends XmlAdapter<String, Timestamp> {

        @Override
        public Timestamp unmarshal(String v) throws Exception {
            return new Timestamp(Long.parseLong(v));
        }

        @Override
        public String marshal(Timestamp v) throws Exception {
            return Long.toString(v.getTime());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "vazeciMeteoPodaci")
    public org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci vazeciMeteoPodaci(@WebParam(name = "korime") String korime, @WebParam(name = "lozinka") String lozinka, @WebParam(name = "id") String id) {

        TablicaParkiraliste tp = new TablicaParkiraliste();
        if (provjeriDaliKorisnikPostoji(korime)) {
            if (provjeriKorisnickoImeIlozinku(korime, lozinka)) {
                Konfiguracija konfiguracija = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
                OWMKlijent owmKlijent = new OWMKlijent(konfiguracija.dajPostavku("apikey"));
                Parkiraliste parkiraliste = tp.getByID(Integer.parseInt(id));

                ResponseJSON rj = owmKlijent.getRealTimeWeather(parkiraliste.getGeoloc().getLatitude(), parkiraliste.getGeoloc().getLongitude());
                org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci mp = new org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci(rj, parkiraliste.getId());
                return mp;
            }
        }
        return null;

    }

    private boolean provjeriKorisnickoImeIlozinku(String korime, String lozinka) {
        TablicaKorisnici tk = new TablicaKorisnici();
        Korisnik k = tk.getByID(korime);

        return k.getKorIme().equals(korime) && k.getLozinka().equals(lozinka);
    }

    private boolean provjeriDaliKorisnikPostoji(String korime) {
        TablicaKorisnici tk = new TablicaKorisnici();
        Korisnik k = tk.getByID(korime);
        return k != null;
    }

}
