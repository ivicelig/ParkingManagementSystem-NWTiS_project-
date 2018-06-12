
package org.foi.nwtis.ivicelig.ws.klijenti;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Parkiranje", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Parkiranje {


    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajBrojPoruka", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajBrojPoruka")
    @ResponseWrapper(localName = "dajBrojPorukaResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajBrojPorukaResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajBrojPorukaRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajBrojPorukaResponse")
    public int dajBrojPoruka(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param brojPoruka
     * @param korisnickaLozinka
     * @param korisnickoIme
     */
    @WebMethod
    @RequestWrapper(localName = "promjeniBrojPoruka", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.PromjeniBrojPoruka")
    @ResponseWrapper(localName = "promjeniBrojPorukaResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.PromjeniBrojPorukaResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/promjeniBrojPorukaRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/promjeniBrojPorukaResponse")
    public void promjeniBrojPoruka(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "brojPoruka", targetNamespace = "")
        int brojPoruka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajTrajanjeCiklusa", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajTrajanjeCiklusa")
    @ResponseWrapper(localName = "dajTrajanjeCiklusaResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajTrajanjeCiklusaResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajTrajanjeCiklusaRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajTrajanjeCiklusaResponse")
    public int dajTrajanjeCiklusa(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param trajanjeCiklusa
     * @param korisnickoIme
     */
    @WebMethod
    @RequestWrapper(localName = "promjeniTrajanjeCiklusa", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.PromjeniTrajanjeCiklusa")
    @ResponseWrapper(localName = "promjeniTrajanjeCiklusaResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.PromjeniTrajanjeCiklusaResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/promjeniTrajanjeCiklusaRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/promjeniTrajanjeCiklusaResponse")
    public void promjeniTrajanjeCiklusa(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "trajanjeCiklusa", targetNamespace = "")
        int trajanjeCiklusa);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deregistrirajGrupu", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DeregistrirajGrupu")
    @ResponseWrapper(localName = "deregistrirajGrupuResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DeregistrirajGrupuResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/deregistrirajGrupuRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/deregistrirajGrupuResponse")
    public Boolean deregistrirajGrupu(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "blokirajGrupu", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.BlokirajGrupu")
    @ResponseWrapper(localName = "blokirajGrupuResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.BlokirajGrupuResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/blokirajGrupuRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/blokirajGrupuResponse")
    public Boolean blokirajGrupu(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "aktivirajGrupu", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AktivirajGrupu")
    @ResponseWrapper(localName = "aktivirajGrupuResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AktivirajGrupuResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/aktivirajGrupuRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/aktivirajGrupuResponse")
    public Boolean aktivirajGrupu(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns org.foi.nwtis.ivicelig.ws.klijenti.StatusKorisnika
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajStatusGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajStatusGrupe")
    @ResponseWrapper(localName = "dajStatusGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajStatusGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajStatusGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajStatusGrupeResponse")
    public StatusKorisnika dajStatusGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @param idParkiraliste
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dodajParkiralisteGrupi", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DodajParkiralisteGrupi")
    @ResponseWrapper(localName = "dodajParkiralisteGrupiResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DodajParkiralisteGrupiResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dodajParkiralisteGrupiRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dodajParkiralisteGrupiResponse")
    public Boolean dodajParkiralisteGrupi(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        Parkiraliste idParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "autenticirajGrupu", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AutenticirajGrupu")
    @ResponseWrapper(localName = "autenticirajGrupuResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AutenticirajGrupuResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/autenticirajGrupuRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/autenticirajGrupuResponse")
    public Boolean autenticirajGrupu(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obrisiSvaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.ObrisiSvaParkiralistaGrupe")
    @ResponseWrapper(localName = "obrisiSvaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.ObrisiSvaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/obrisiSvaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/obrisiSvaParkiralistaGrupeResponse")
    public Boolean obrisiSvaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "ucitajSvaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.UcitajSvaParkiralistaGrupe")
    @ResponseWrapper(localName = "ucitajSvaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.UcitajSvaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/ucitajSvaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/ucitajSvaParkiralistaGrupeResponse")
    public boolean ucitajSvaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @param idParkiraliste
     * @return
     *     returns org.foi.nwtis.ivicelig.ws.klijenti.StatusParkiralista
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajStatusParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajStatusParkiralistaGrupe")
    @ResponseWrapper(localName = "dajStatusParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajStatusParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajStatusParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajStatusParkiralistaGrupeResponse")
    public StatusParkiralista dajStatusParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        int idParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @param idParkiraliste
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "blokirajParkiralisteGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.BlokirajParkiralisteGrupe")
    @ResponseWrapper(localName = "blokirajParkiralisteGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.BlokirajParkiralisteGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/blokirajParkiralisteGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/blokirajParkiralisteGrupeResponse")
    public boolean blokirajParkiralisteGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        int idParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @param idParkiraliste
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "aktivirajParkiralisteGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AktivirajParkiralisteGrupe")
    @ResponseWrapper(localName = "aktivirajParkiralisteGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AktivirajParkiralisteGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/aktivirajParkiralisteGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/aktivirajParkiralisteGrupeResponse")
    public boolean aktivirajParkiralisteGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        int idParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @param idParkiraliste
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obrisiParkiralisteGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.ObrisiParkiralisteGrupe")
    @ResponseWrapper(localName = "obrisiParkiralisteGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.ObrisiParkiralisteGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/obrisiParkiralisteGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/obrisiParkiralisteGrupeResponse")
    public boolean obrisiParkiralisteGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        int idParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param odabranaParkiralista
     * @param korisnickoIme
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "aktivirajOdabranaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AktivirajOdabranaParkiralistaGrupe")
    @ResponseWrapper(localName = "aktivirajOdabranaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.AktivirajOdabranaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/aktivirajOdabranaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/aktivirajOdabranaParkiralistaGrupeResponse")
    public boolean aktivirajOdabranaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "odabranaParkiralista", targetNamespace = "")
        List<Integer> odabranaParkiralista);

    /**
     * 
     * @param korisnickaLozinka
     * @param odabranaParkiralista
     * @param korisnickoIme
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "blokirajOdabranaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.BlokirajOdabranaParkiralistaGrupe")
    @ResponseWrapper(localName = "blokirajOdabranaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.BlokirajOdabranaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/blokirajOdabranaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/blokirajOdabranaParkiralistaGrupeResponse")
    public boolean blokirajOdabranaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "odabranaParkiralista", targetNamespace = "")
        List<Integer> odabranaParkiralista);

    /**
     * 
     * @param korisnickaLozinka
     * @param kapacitetParkiraliste
     * @param nazivParkiraliste
     * @param korisnickoIme
     * @param adresaParkiraliste
     * @param idParkiraliste
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dodajNovoParkiralisteGrupi", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DodajNovoParkiralisteGrupi")
    @ResponseWrapper(localName = "dodajNovoParkiralisteGrupiResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DodajNovoParkiralisteGrupiResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dodajNovoParkiralisteGrupiRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dodajNovoParkiralisteGrupiResponse")
    public Boolean dodajNovoParkiralisteGrupi(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        int idParkiraliste,
        @WebParam(name = "nazivParkiraliste", targetNamespace = "")
        String nazivParkiraliste,
        @WebParam(name = "adresaParkiraliste", targetNamespace = "")
        String adresaParkiraliste,
        @WebParam(name = "kapacitetParkiraliste", targetNamespace = "")
        int kapacitetParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Parkiraliste>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajSvaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajSvaParkiralistaGrupe")
    @ResponseWrapper(localName = "dajSvaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajSvaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajSvaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajSvaParkiralistaGrupeResponse")
    public List<Parkiraliste> dajSvaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @param idParkiraliste
     * @return
     *     returns java.util.List<org.foi.nwtis.ivicelig.ws.klijenti.Vozilo>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "dajSvaVozilaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajSvaVozilaParkiralistaGrupe")
    @ResponseWrapper(localName = "dajSvaVozilaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.DajSvaVozilaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajSvaVozilaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/dajSvaVozilaParkiralistaGrupeResponse")
    public List<Vozilo> dajSvaVozilaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "idParkiraliste", targetNamespace = "")
        int idParkiraliste);

    /**
     * 
     * @param korisnickaLozinka
     * @param odabranaParkiralista
     * @param korisnickoIme
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obrisiOdabranaParkiralistaGrupe", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.ObrisiOdabranaParkiralistaGrupe")
    @ResponseWrapper(localName = "obrisiOdabranaParkiralistaGrupeResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.ObrisiOdabranaParkiralistaGrupeResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/obrisiOdabranaParkiralistaGrupeRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/obrisiOdabranaParkiralistaGrupeResponse")
    public boolean obrisiOdabranaParkiralistaGrupe(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka,
        @WebParam(name = "odabranaParkiralista", targetNamespace = "")
        List<Integer> odabranaParkiralista);

    /**
     * 
     * @param korisnickaLozinka
     * @param korisnickoIme
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registrirajGrupu", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.RegistrirajGrupu")
    @ResponseWrapper(localName = "registrirajGrupuResponse", targetNamespace = "http://serveri.ws.dkermek.nwtis.foi.org/", className = "org.foi.nwtis.ivicelig.ws.klijenti.RegistrirajGrupuResponse")
    @Action(input = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/registrirajGrupuRequest", output = "http://serveri.ws.dkermek.nwtis.foi.org/Parkiranje/registrirajGrupuResponse")
    public Boolean registrirajGrupu(
        @WebParam(name = "korisnickoIme", targetNamespace = "")
        String korisnickoIme,
        @WebParam(name = "korisnickaLozinka", targetNamespace = "")
        String korisnickaLozinka);

}