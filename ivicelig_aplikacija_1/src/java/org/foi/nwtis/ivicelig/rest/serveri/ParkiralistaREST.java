/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.rest.serveri;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste;

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
     * Retrieves representation of an instance of org.foi.nwtis.ivicelig.rest.serveri.ParkiralistaREST
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        
            

        
        java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> park;
        park = dajSvaParkiralistaGrupe("ivicelig", "656989");
        
         return "{\"odgovor\": [{\"adresa\": "+park.get(0).getAdresa()+"}], "
                    + "\"status\": \"ERROR: 2" + "\"}";
         
    }

    /**
     * PUT method for updating or creating an instance of ParkiralistaREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    private static java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste> dajSvaParkiralistaGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.dajSvaParkiralistaGrupe(korisnickoIme, korisnickaLozinka);
    }
}
