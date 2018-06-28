/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.rest.serveri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.ivicelig.web.baza.TablicaDnevnik;
import org.foi.nwtis.ivicelig.web.baza.TablicaParkiraliste;
import org.foi.nwtis.ivicelig.web.json.Odgovor;
import org.foi.nwtis.ivicelig.web.json.ParkiralistaForJson;
import org.foi.nwtis.ivicelig.web.json.Vehicle;
import org.foi.nwtis.ivicelig.web.json.VehicleForJson;
import org.foi.nwtis.ivicelig.web.podaci.Dnevnik;
import org.foi.nwtis.ivicelig.web.podaci.Lokacija;
import org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste;
import org.foi.nwtis.ivicelig.ws.klijenti.Vozilo;

/**
 * REST Web Service
 *
 * @author Ivica
 */
@Path("parkiralista")
public class ParkiralistaREST {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ParkiralistaREST
     */
    public ParkiralistaREST() {

    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.ivicelig.rest.serveri.ParkiralistaREST
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
       
        ParkiralistaForJson pfj = new ParkiralistaForJson();
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> parkiralistaGrupe = dajSvaParkiralistaGrupe("ivicelig", "656989");
        if (parkiralistaGrupe.isEmpty()) {
            pfj.setStatus("ERR");
            pfj.setPoruka("NEMA PARKIRALISTA U BAZI;");
        } else {
            List<Odgovor> odgovori = new ArrayList<Odgovor>();
            for (org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste : parkiralistaGrupe) {
                Odgovor odgovor = napraviOdgovor(parkiraliste);
                odgovori.add(odgovor);

            }
             TablicaDnevnik td = new TablicaDnevnik();
                Dnevnik d = new Dnevnik();
               d.setNaziv("REST");
               d.setOpis("getJson");
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sCertDate = sdf.format(new Date());
               d.setVrijeme(sCertDate);
            td.insert(d);
            pfj.setOdgovor(odgovori);
            pfj.setStatus("OK");
            

        }
        Gson gson = new Gson();
        String json = gson.toJson(pfj, ParkiralistaForJson.class);
        return json;

    }

    private Odgovor napraviOdgovor(org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste) throws NumberFormatException {
        Odgovor odgovor = new Odgovor();
        odgovor.setId(parkiraliste.getId());
        odgovor.setAdresa(parkiraliste.getAdresa());
        odgovor.setNaziv(parkiraliste.getNaziv());
        odgovor.setLatitude(Double.parseDouble(parkiraliste.getGeoloc().getLatitude()));
        odgovor.setLongitude(Double.parseDouble(parkiraliste.getGeoloc().getLongitude()));
        odgovor.setKapacitet(parkiraliste.getKapacitet());
        odgovor.setStatus(parkiraliste.getStatus().value());
        return odgovor;
    }

    /**
     * PUT method for updating or creating an instance of ParkiralistaREST
     *
     * @param content representation for the resource
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getJson(@PathParam("id") String id) {

        ParkiralistaForJson pfj = new ParkiralistaForJson();
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> parkiralistaGrupe = dajSvaParkiralistaGrupe("ivicelig", "656989");
        if (parkiralistaGrupe.isEmpty()) {
            pfj.setStatus("ERR;");
            pfj.setPoruka("NEMA PARKIRALISTA;");
        } else {
            List<Odgovor> odgovori = new ArrayList<Odgovor>();
            for (org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste : parkiralistaGrupe) {
                if (parkiraliste.getId() == Integer.parseInt(id)) {
                    Odgovor odgovor = napraviOdgovor(parkiraliste);
                    odgovori.add(odgovor);
                    break;
                }
            }
            if (odgovori.size() == 1) {
                pfj.setOdgovor(odgovori);
                pfj.setStatus("OK");
                TablicaDnevnik td = new TablicaDnevnik();
                Dnevnik d = new Dnevnik();
               d.setNaziv("REST");
               d.setOpis("getJson/id");
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sCertDate = sdf.format(new Date());
               d.setVrijeme(sCertDate);
            td.insert(d);
            } else {

                pfj.setStatus("ERR;");
                pfj.setPoruka("NEMA PARKIRALISTA S TIM ID-em;");
            }

        }
        Gson gson = new Gson();
        String json = gson.toJson(pfj, ParkiralistaForJson.class);
        return json;

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/vozila/")
    public String getJsonVozila(@PathParam("id") String id) {

        List<Vehicle> vehicles = new ArrayList<>();
        VehicleForJson vfj = new VehicleForJson();
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Vozilo> vozilaGrupeID = dajSvaVozilaParkiralistaGrupe("ivicelig", "656989", Integer.parseInt(id));
        if (vozilaGrupeID.isEmpty()) {
            vfj.setStatus("ERR;");
            vfj.setPoruka("NEMA VOZILA;");

        } else {
            for (Vozilo vozilo : vozilaGrupeID) {
                Vehicle vehicle = new Vehicle();
                vehicle.setID(vozilo.getParkiraliste());
                vehicle.setRegistracija(vozilo.getRegistracija());
                vehicle.setStatus(vozilo.getAkcija().value());
                vehicles.add(vehicle);
            }
            vfj.setStatus("OK;");
            vfj.setOdgovor(vehicles);
        }
        Gson gson = new Gson();
        String json = gson.toJson(vfj, VehicleForJson.class);
        return json;

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String postJson(@PathParam("id") String id, String podaci) {

        Gson gson = new Gson();

        JsonObject jo = gson.fromJson(podaci, JsonObject.class);
        String korIme = jo.get("korime").getAsString();
        String lozinka = jo.get("lozinka").getAsString();
        String naziv = jo.get("naziv").getAsString();
        String adresa = jo.get("adresa").getAsString();
        int kapacitet = jo.get("kapacitet").getAsInt();
        ParkiralistaForJson pfj = new ParkiralistaForJson();
        List<Odgovor> odgovori = new ArrayList<>();
        String json;

        if (!dodajNovoParkiralisteGrupi(korIme, lozinka, Integer.parseInt(id), naziv, adresa, kapacitet)) {
            pfj.setOdgovor(odgovori);
            pfj.setStatus("ERR");
            pfj.setPoruka("ID ZAUZET");
            json = gson.toJson(pfj, ParkiralistaForJson.class);
            return json;
        }
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> parkiralistaGrupe = dajSvaParkiralistaGrupe("ivicelig", "656989");
        for (Parkiraliste parkiraliste : parkiralistaGrupe) {
            if (parkiraliste.getId() == Integer.parseInt(id)) {
                TablicaParkiraliste tp = new TablicaParkiraliste();
                napraviObjektIUpišiUBazu(parkiraliste, tp);
                break;

            }
        }
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> temp = dajSvaParkiralistaGrupe("ivicelig", "656989");
        
        aktivirajParkiralisteGrupe("ivicelig", "656989", Integer.parseInt(id));
        pfj.setOdgovor(odgovori);
        pfj.setStatus("OK");
        json = gson.toJson(pfj, ParkiralistaForJson.class);

        return json;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String putJson(@PathParam("id") String id, String podaci) {
        boolean postoji = false;
        ParkiralistaForJson pfj = new ParkiralistaForJson();
        List<Odgovor> odgovori = new ArrayList<>();
        String json;
        Gson gson = new Gson();
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> parkiralistaGrupe = dajSvaParkiralistaGrupe("ivicelig", "656989");
        for (org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste : parkiralistaGrupe) {
            if (parkiraliste.getId() == Integer.parseInt(id)) {
                postoji = true;
                TablicaParkiraliste tp  = new TablicaParkiraliste();
            
        
                napraviObjektIAzuirajUBazi(parkiraliste, tp);
                break;
            }
        }

        if (postoji) {
            obrisiParkiralisteGrupe("ivicelig", "656989", Integer.parseInt(id));

            JsonObject jo = gson.fromJson(podaci, JsonObject.class);
            String korIme = jo.get("korime").getAsString();
            String lozinka = jo.get("lozinka").getAsString();
            String naziv = jo.get("naziv").getAsString();
            String adresa = jo.get("adresa").getAsString();
            int kapacitet = jo.get("kapacitet").getAsInt();
            dodajNovoParkiralisteGrupi(korIme, lozinka, Integer.parseInt(id), naziv, adresa, kapacitet);
            //AZUIRAJ U BAZI
            
            
            pfj.setOdgovor(odgovori);
            pfj.setStatus("OK");

            json = gson.toJson(pfj, ParkiralistaForJson.class);
        } else {
            pfj.setOdgovor(odgovori);
            pfj.setStatus("ERR");
            pfj.setPoruka("ID NE POSTOJI");
            json = gson.toJson(pfj, ParkiralistaForJson.class);
        }
        return json;
    }

    @DELETE
    @Path("{id}")
    public String deleteById(@PathParam("id") String id) {
        boolean postoji = false;
        ParkiralistaForJson pfj = new ParkiralistaForJson();
        List<Odgovor> odgovori = new ArrayList<>();
        String json;
        Gson gson = new Gson();
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> parkiralistaGrupe = dajSvaParkiralistaGrupe("ivicelig", "656989");
        for (org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste parkiraliste : parkiralistaGrupe) {
            if (parkiraliste.getId() == Integer.parseInt(id)) {
                postoji = true;
                TablicaParkiraliste tp = new TablicaParkiraliste();
                tp.deleteByID(Integer.parseInt(id));
                break;
            }
        }

        if (postoji) {
            obrisiParkiralisteGrupe("ivicelig", "656989", Integer.parseInt(id));
            pfj.setOdgovor(odgovori);
            pfj.setStatus("OK");

            json = gson.toJson(pfj, ParkiralistaForJson.class);
        } else {
            pfj.setOdgovor(odgovori);
            pfj.setStatus("ERR");
            pfj.setPoruka("ID NE POSTOJI");
            json = gson.toJson(pfj, ParkiralistaForJson.class);
        }
        return json;
    }

    private static java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> dajSvaParkiralistaGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.dajSvaParkiralistaGrupe(korisnickoIme, korisnickaLozinka);
    }

    private static Boolean dodajNovoParkiralisteGrupi(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idParkiraliste, java.lang.String nazivParkiraliste, java.lang.String adresaParkiraliste, int kapacitetParkiraliste) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.dodajNovoParkiralisteGrupi(korisnickoIme, korisnickaLozinka, idParkiraliste, nazivParkiraliste, adresaParkiraliste, kapacitetParkiraliste);
    }

    
    private static boolean obrisiParkiralisteGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idParkiraliste) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.obrisiParkiralisteGrupe(korisnickoIme, korisnickaLozinka, idParkiraliste);
    }

    private static java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Vozilo> dajSvaVozilaParkiralistaGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idParkiraliste) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.dajSvaVozilaParkiralistaGrupe(korisnickoIme, korisnickaLozinka, idParkiraliste);
    }

    private void napraviObjektIUpišiUBazu(Parkiraliste parkiraliste, TablicaParkiraliste tp) {
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
    private void napraviObjektIAzuirajUBazi(Parkiraliste parkiraliste, TablicaParkiraliste tp) {
        
       
        tp.update(parkiraliste);
    }

    private static boolean aktivirajParkiralisteGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idParkiraliste) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.aktivirajParkiralisteGrupe(korisnickoIme, korisnickaLozinka, idParkiraliste);
    }

}
