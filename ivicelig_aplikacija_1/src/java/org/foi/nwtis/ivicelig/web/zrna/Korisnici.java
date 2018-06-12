/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.zrna;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;

import org.foi.nwtis.ivicelig.web.baza.TablicaKorisnici;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ivica
 */
@ManagedBean(name = "korisnici")
    @SessionScoped
public class Korisnici implements Serializable{
   
    private List<Korisnik> korisnici;

    public List<Korisnik> getKorisnici() {
                

        return korisnici;
    }

    public Korisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
    
    public void setKorisnici(List<Korisnik> korisnici) {
                

        this.korisnici = korisnici;
    }
    /**
     * Creates a new instance of Korisnici
     */
    public Korisnici() {
        TablicaKorisnici tk = new TablicaKorisnici();
        korisnici = tk.getAllRecords();
    }
    
}
