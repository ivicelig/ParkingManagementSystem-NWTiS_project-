/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.rest.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.rest.json.AutentikacijaClass;
import org.foi.nwtis.ivicelig.rest.json.CreateUserClass;
import org.foi.nwtis.ivicelig.rest.json.KorisnikClass;
import org.foi.nwtis.ivicelig.rest.json.KorisnikLozinkaClass;
import org.foi.nwtis.ivicelig.rest.json.OdgovorClass;
import org.foi.nwtis.ivicelig.web.listeners.SlusacAplikacije;

/**
 * REST Web Service
 *
 * @author Ivica
 */
@Path("korisnici")
public class KorisniciREST {

    Konfiguracija konfig;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of KorisniciREST
     */
    public KorisniciREST() {
        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Gson gson = new Gson();
        List<KorisnikClass> kcList = new ArrayList<>();
        OdgovorClass oc = new OdgovorClass();
        String odgovor = naredbaListaj();

        String[] values = odgovor.split(";");
        if (values[0].equals("OK 10")) {
            kcList = gson.fromJson(values[1], new TypeToken<List<KorisnikClass>>() {
            }.getType());
            oc.setOdgovor(kcList);
            oc.setStatus("OK");
            return gson.toJson(oc, OdgovorClass.class);
        } else {
            oc.setStatus("ERR");
            oc.setPoruka("Nema korisnika!");
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{korisnickoIme}")
    public String getJson(@PathParam("korisnickoIme") String korisnickoIme) {
        Gson gson = new Gson();
        List<KorisnikClass> kcList;
        List<KorisnikClass> kcList1 = new ArrayList<>();
        OdgovorClass oc = new OdgovorClass();
        String odgovor = naredbaListaj();

        String[] values = odgovor.split(";");
        if (values[0].equals("OK 10")) {
            kcList = gson.fromJson(values[1], new TypeToken<List<KorisnikClass>>() {
            }.getType());
            boolean postoji = false;
            for (KorisnikClass kc : kcList) {
                if (kc.getKime().equals(korisnickoIme)) {
                    postoji = true;
                    kcList1.add(kc);
                    break;
                }
            }
            if (!postoji) {
                oc.setStatus("ERR");
                oc.setPoruka("Nema korisnika!");
                String json = gson.toJson(oc, OdgovorClass.class);
                return json;
            }
            oc.setOdgovor(kcList1);
            oc.setStatus("OK");
            return gson.toJson(oc, OdgovorClass.class);
        } else {
            oc.setStatus("ERR");
            oc.setPoruka("Nema korisnika!");
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
        }
    }

    /**
     * PUT method for updating or creating an instance of KorisniciREST
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{korisnickoIme}")
    public String putJson(@PathParam("korisnickoIme") String korisnickoIme,String content) {
        Gson gson = new Gson();
        KorisnikLozinkaClass klc = gson.fromJson(content, KorisnikLozinkaClass.class);
        String odgovor = naredbaAzuiraj(korisnickoIme, klc.getLozinka(), klc.getPrezime(), klc.getIme());
        String[] values = odgovor.split(";");
        if(values[0].equals("OK 10")){
              OdgovorClass oc = new OdgovorClass();
             oc.setStatus("OK");
            
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
         }else{
             OdgovorClass oc = new OdgovorClass();
             oc.setStatus("ERR");
            oc.setPoruka("Korisnik ne postoji u bazi!");
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
         }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postJson(String podaci) {
        Gson gson = new Gson();
        CreateUserClass klc = gson.fromJson(podaci, CreateUserClass.class);
        String odgovor = naredbaDodaj(klc.getKime(), klc.getLozinka(), klc.getPrezime(), klc.getIme());
        String[] values = odgovor.split(";");
        if(values[0].equals("OK 10")){
              OdgovorClass oc = new OdgovorClass();
             oc.setStatus("OK");
            
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
         }else{
             OdgovorClass oc = new OdgovorClass();
             oc.setStatus("ERR");
            oc.setPoruka("Korisnik već postoji u bazi!");
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
         }
        
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{korisnickoIme}")
    public String postJson(@PathParam("korisnickoIme") String korisnickoIme, String podaci) {
        Gson gson = new Gson();
         AutentikacijaClass klc = gson.fromJson(podaci, AutentikacijaClass.class);
         String odgovor = naredbaAutentikacija(korisnickoIme,klc.getLozinka());
         String[] values = odgovor.split(";");
         if(values[0].equals("OK 10")){
              OdgovorClass oc = new OdgovorClass();
             oc.setStatus("OK");
            
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
         }else{
             OdgovorClass oc = new OdgovorClass();
             oc.setStatus("ERR");
            oc.setPoruka("Nema korisnika ili nije dobra lozinka!");
            String json = gson.toJson(oc, OdgovorClass.class);
            return json;
         }
        
    }

    private String naredbaListaj() {
        String odgovor = "";
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK ivicelig; LOZINKA 123456; LISTAJ;");

            System.out.println("Odgovor: " + sb.toString());
            odgovor = sb.toString();
        } catch (IOException ex) {

        }
        return odgovor;
    }
   

    private String naredbaAutentikacija(String korime, String lozinka) {
        String odgovor = "";
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK "+korime+"; LOZINKA "+lozinka+";");

            System.out.println("Odgovor: " + sb.toString());
            odgovor = sb.toString();
        } catch (IOException ex) {

        }
        return odgovor;
    }
    private String naredbaDodaj(String korime, String lozinka, String prezime, String ime) {
        String odgovor = "";
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK "+korime+"; LOZINKA "+lozinka+"; DODAJ \""+prezime+"\" \""+ime+"\";");

            System.out.println("Odgovor: " + sb.toString());
            odgovor = sb.toString();
        } catch (IOException ex) {

        }
        return odgovor;
    }
    private String naredbaAzuiraj(String korime, String lozinka, String prezime, String ime) {
        String odgovor = "";
        try {

            StringBuffer sb = saljiNaredbuPrimiOdgovor("KORISNIK "+korime+"; LOZINKA "+lozinka+"; AZURIRAJ \""+prezime+"\" \""+ime+"\";");

            System.out.println("Odgovor: " + sb.toString());
            odgovor = sb.toString();
        } catch (IOException ex) {

        }
        return odgovor;
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
