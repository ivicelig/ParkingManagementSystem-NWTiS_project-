/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.rest.json.CreateUserClass;
import org.foi.nwtis.ivicelig.rest.json.KorisnikClass;
import org.foi.nwtis.ivicelig.rest.json.KorisnikLozinkaClass;
import org.foi.nwtis.ivicelig.rest.json.OdgovorClass;
import org.foi.nwtis.ivicelig.rest.klijent.KorisniciKorisnickoREST;
import org.foi.nwtis.ivicelig.rest.klijent.KorisniciREST;
import org.foi.nwtis.ivicelig.web.listener.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
@Named(value = "registracija")
@SessionScoped
public class Registracija implements Serializable {

    private String korime = "";
    private String lozinka = "";
    private String ime = "";
    private String prezime = "";
    private String ponovljenaLozinka = "";
    private String poruka = "";
    private String poruka1 = "";
    private String korimeAzuiranje = "";
    private String lozinkaAzuiranje = "";
    private String imeAzuiranje = "";
    private String prezimeAzuiranje = "";
    private List<KorisnikClass> korisnici;
    private int brojStranica;
    private Konfiguracija konfig;
    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }
    public List<KorisnikClass> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<KorisnikClass> korisnici) {
        this.korisnici = korisnici;
    }

    
    public String getKorimeAzuiranje() {
        return korimeAzuiranje;
    }

    public void setKorimeAzuiranje(String korimeAzuiranje) {
        this.korimeAzuiranje = korimeAzuiranje;
    }

    public String getLozinkaAzuiranje() {
        return lozinkaAzuiranje;
    }

    public void setLozinkaAzuiranje(String lozinkaAzuiranje) {
        this.lozinkaAzuiranje = lozinkaAzuiranje;
    }

    public String getImeAzuiranje() {
        return imeAzuiranje;
    }

    public void setImeAzuiranje(String imeAzuiranje) {
        this.imeAzuiranje = imeAzuiranje;
    }

    public String getPrezimeAzuiranje() {
        return prezimeAzuiranje;
    }

    public void setPrezimeAzuiranje(String prezimeAzuiranje) {
        this.prezimeAzuiranje = prezimeAzuiranje;
    }

    public String getPoruka1() {
        return poruka1;
    }

    public void setPoruka1(String poruka1) {
        this.poruka1 = poruka1;
    }
    private static final String EMPTY_STRING = "";

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

   @PostConstruct
    public void init(){
        postavljanjeKorisnika();
        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        brojStranica = Integer.parseInt(konfig.dajPostavku("stranicenje"));
    }

    public String getIme() {
        return ime;
    }

    public String getPonovljenaLozinka() {
        return ponovljenaLozinka;
    }

    public void setPonovljenaLozinka(String ponovljenaLozinka) {
        this.ponovljenaLozinka = ponovljenaLozinka;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    private boolean provjera() {
        boolean provjera = false;
        if ((korime.trim().equals(EMPTY_STRING)) || (lozinka.trim().equals(EMPTY_STRING)) || (ponovljenaLozinka.trim().equals(EMPTY_STRING)) || (ime.trim().equals(EMPTY_STRING)) || (prezime.trim().equals(EMPTY_STRING))) {
            setPoruka("Sva polja moraju biti popunjena");

        } else {
            if (!(ponovljenaLozinka.equals(lozinka))) {
                setPoruka("Lozinke nisu jednake");
            } else {
                provjera = true;
            }
        }

        return provjera;
    }

    public String registracija() {
        Gson gson = new Gson();

        if (provjera()) {

            KorisniciREST kr = new KorisniciREST();
            CreateUserClass cuc = new CreateUserClass();
            cuc.setIme(ime);
            cuc.setPrezime(prezime);
            cuc.setKime(korime);
            cuc.setLozinka(lozinka);
            String json = gson.toJson(cuc, CreateUserClass.class);
            String odgovor = kr.postJson(json, String.class);
            OdgovorClass oc = gson.fromJson(odgovor, OdgovorClass.class);
            if (oc.getStatus().equals("OK")) {
                System.out.println("Registracija uspješna");
                setPoruka("Registracija uspješna");
                postavljanjeKorisnika();
            } else {
                System.out.println("Korisnik već postoji!");
                setPoruka("Korisnik već postoji!");
            }

        }
        return "";
    }

    public String azuiranje() {
        Gson gson = new Gson();

        if (provjeraAzuiranje()) {

            KorisniciKorisnickoREST kr = new KorisniciKorisnickoREST(korimeAzuiranje);
            KorisnikLozinkaClass kc = new KorisnikLozinkaClass();
            kc.setIme(imeAzuiranje);
            kc.setPrezime(prezimeAzuiranje);
            kc.setLozinka(lozinkaAzuiranje);
            String json = gson.toJson(kc, KorisnikLozinkaClass.class);
            String odgovor = kr.putJson(json, String.class);
            OdgovorClass oc = gson.fromJson(odgovor, OdgovorClass.class);
            if (oc.getStatus().equals("OK")) {
                System.out.println("Azuiranje uspješno");
                setPoruka1("Azuiranje uspješno");
                postavljanjeKorisnika();
            } else {
                System.out.println("Korisnik ne postoji!");
                setPoruka1("Korisnik ne postoji!");
            }

        }
        return "";
    }

    private boolean provjeraAzuiranje() {
        boolean provjera = false;
        if ((korimeAzuiranje.trim().equals(EMPTY_STRING)) || (lozinkaAzuiranje.trim().equals(EMPTY_STRING)) || (imeAzuiranje.trim().equals(EMPTY_STRING)) || (prezimeAzuiranje.trim().equals(EMPTY_STRING))) {
            setPoruka1("Sva polja moraju biti popunjena");

        } else {

            provjera = true;

        }

        return provjera;
    }
    private String postavljanjeKorisnika(){
        Gson gson = new Gson();
        KorisniciREST kr = new KorisniciREST();
        String odgovor = kr.getJson(String.class);
        
        
        OdgovorClass oc = gson.fromJson(odgovor, OdgovorClass.class);
        korisnici = oc.getOdgovor();
        
        
        
        return "";
    }
    
    

}
