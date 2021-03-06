/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import org.foi.nwtis.ivicelig.ejb.sb.StatefulSB;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.listener.SlusacAplikacije;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.ivicelig.web.threads.MQTTdretva;

/**
 *
 * @author Ivica
 */
@ManagedBean(name = "posluzitelj")
@RequestScoped
public class Posluzitelj implements Serializable {

    private String stanjePosluzitelja = "";
    private String statusGrupe = "";
    private String korimeLogin = "";

    public String getKorimeLogin() {
        return korimeLogin;
    }

    public void setKorimeLogin(String korimeLogin) {
        this.korimeLogin = korimeLogin;
    }

    public String getStatusGrupe() {
        return statusGrupe;
    }

    public void setStatusGrupe(String statusGrupe) {
        this.statusGrupe = statusGrupe;
    }

    public String getStanjePosluzitelja() {
        return stanjePosluzitelja;
    }

    public void setStanjePosluzitelja(String stanjePosluzitelja) {
        this.stanjePosluzitelja = stanjePosluzitelja;
    }

    @EJB
    private StatefulSB statefulSB;

    Konfiguracija konfig;
    /**
     * Creates a new instance of Posluzitelj
     */

    ServletContext sc = SlusacAplikacije.sc;

    @PostConstruct
    public void init() {

        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        postaviStanjeGrupe();
        korimeLogin = dohvatiKorisnika();
        postaviStanjePoslužitelja();

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

    private String dohvatiLozinku() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session
                = (HttpSession) context.getExternalContext().getSession(true);
        session.getAttributeNames();

        if (session.getAttribute("lozinka") == null) {
            return "";
        } else {
            return session.getAttribute("lozinka").toString();
        }

    }

    public void naredbaPauza() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK " + dohvatiKorisnika() + "; LOZINKA " + dohvatiLozinku() + "; PAUZA;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjePoslužitelja();

        } catch (IOException ex) {

        }
    }

    public void naredbaKreni() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK " + dohvatiKorisnika() + "; LOZINKA " + dohvatiLozinku() + "; KRENI;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjePoslužitelja();

        } catch (IOException ex) {

        }
    }

    public void naredbaPasivno() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK " + dohvatiKorisnika() + "; LOZINKA " + dohvatiLozinku() + "; PASIVNO;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjePoslužitelja();

        } catch (IOException ex) {

        }
    }

    public void naredbaAktivno() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK " + dohvatiKorisnika() + "; LOZINKA " + dohvatiLozinku() + "; AKTIVNO;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjePoslužitelja();

        } catch (IOException ex) {

        }
    }

    public void naredbaStani() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK " + dohvatiKorisnika() + "; LOZINKA " + dohvatiLozinku() + "; STANI;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjePoslužitelja();

        } catch (IOException ex) {

        }
    }

    public void registrirajGrupu() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; GRUPA DODAJ;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjeGrupe();

        } catch (IOException ex) {

        }
    }

    public void deregistrirajGrupu() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; GRUPA PREKID;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjeGrupe();
        } catch (IOException ex) {

        }
    }

    public void blokirajGrupu() {
        try {
            md.interrupt();
            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; GRUPA PAUZA;");

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjeGrupe();

        } catch (IOException ex) {

        }
    }
    private static MQTTdretva md;

    public void pokreniGrupu() {
        try {
            md = new MQTTdretva(dohvatiKorisnika());
            md.start();
            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; GRUPA KRENI;");
            //md = new  MQTTdretva(dohvatiKorisnika());
            //md.start();

            System.out.println("Odgovor: " + sb.toString());
            postaviStanjeGrupe();
        } catch (IOException ex) {

        }
    }

    public void postaviStanjeGrupe() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; GRUPA STANJE;");

            System.out.println("Odgovor: " + sb.toString());
            String[] values = sb.toString().split(";");
            if (values[0].startsWith("OK")) {
                statusGrupe = values[1];
            } else {
                statusGrupe = "GRUPA NIJE REGISTRIRANA";
            }

        } catch (IOException ex) {

        }
    }

    public void postaviStanjePoslužitelja() {
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; STANJE;");

            System.out.println("Odgovor: " + sb.toString());
            String[] values = sb.toString().split(";");
            if (values[0].startsWith("OK")) {
                stanjePosluzitelja = values[1];
            } else {
                stanjePosluzitelja = "GRUPA NIJE REGISTRIRANA";
            }

        } catch (IOException ex) {

        }
    }

    private StringBuffer saljiNaredbuPrimiOdgovor(String naredba) throws IOException {

        Socket socket = new Socket(konfig.dajPostavku("posluzitelj.server"), Integer.parseInt(konfig.dajPostavku("posluzitelj.port")));
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        os.write(naredba.getBytes());
        os.flush();
        socket.shutdownOutput();
        StringBuffer sb = new StringBuffer();
        int znak;
        while ((znak = is.read()) != -1) {
            sb.append((char) znak);
        }

        return sb;
    }

}
