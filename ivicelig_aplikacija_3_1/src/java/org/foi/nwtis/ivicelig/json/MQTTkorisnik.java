/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.json;

import java.io.Serializable;

/**
 *
 * @author Ivica
 */
public class MQTTkorisnik implements Serializable{
    private MQTTjson mj;
    private String korisnik;

    public MQTTjson getMj() {
        return mj;
    }

    public void setMj(MQTTjson mj) {
        this.mj = mj;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }
    
}
