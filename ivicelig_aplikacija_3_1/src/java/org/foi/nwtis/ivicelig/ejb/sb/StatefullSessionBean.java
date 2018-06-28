/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;

import com.google.gson.Gson;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.foi.nwtis.ivicelig.json.AutentikacijaClass;
import org.foi.nwtis.ivicelig.json.OdgovorClass;


/**
 *
 * @author Ivica
 */
@Stateful
@LocalBean
public class StatefullSessionBean {

    private String korime;
    private String lozinka;
    private boolean login = false;
    private String status;
    
    @PostConstruct
    void init() {
        status = "Ready Stateful";
        System.out.println(status);
    }

    public boolean provjeriLogin(String korime, String lozinka) {
        Gson gson = new Gson();
        KorisnikWEBKlijent kwk = new KorisnikWEBKlijent(korime);
        AutentikacijaClass ac = new AutentikacijaClass();
        ac.setLozinka(lozinka);
        String json = gson.toJson(ac, AutentikacijaClass.class);

        String odgovor = kwk.postJson(json, String.class);

        OdgovorClass oc = gson.fromJson(odgovor, OdgovorClass.class);

        if (oc.getStatus().equals("OK")) {
            remove();
            
            
            return true;
        }
        return false;

    }

    @Remove(retainIfException = false)
    public void remove() {
        korime = null;
        lozinka = null;
        System.out.println("User logout!");
    }

    public String getKorime() {
        return korime;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
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

    static class KorisnikWEBKlijent {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/ivicelig_aplikacija_3_2/webresources/";

        public KorisnikWEBKlijent(String korisnickoIme) {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            String resourcePath = java.text.MessageFormat.format("korisnici/{0}", new Object[]{korisnickoIme});
            webTarget = client.target(BASE_URI).path(resourcePath);
        }

        public void setResourcePath(String korisnickoIme) {
            String resourcePath = java.text.MessageFormat.format("korisnici/{0}", new Object[]{korisnickoIme});
            webTarget = client.target(BASE_URI).path(resourcePath);
        }

        /**
         * @param responseType Class representing the response
         * @return response object (instance of responseType class)
         */
        public <T> T getJson(Class<T> responseType) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        }

        /**
         * @param responseType Class representing the response
         * @param requestEntity request data@return response object (instance of
         * responseType class)
         */
        public <T> T postJson(Object requestEntity, Class<T> responseType) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
        }

        /**
         * @param responseType Class representing the response
         * @param requestEntity request data@return response object (instance of
         * responseType class)
         */
        public <T> T putJson(Object requestEntity, Class<T> responseType) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
        }

        public void close() {
            client.close();
        }
    }

}
