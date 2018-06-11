/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.rest.serveri;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

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
         return "{\"odgovor\": [], "
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
}
