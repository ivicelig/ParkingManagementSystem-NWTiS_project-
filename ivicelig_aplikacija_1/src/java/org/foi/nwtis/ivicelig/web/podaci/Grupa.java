/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.podaci;

/**
 *
 * @author Ivica
 */
public class Grupa {
    private String korime;
    private String rola;

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public Grupa(String korime, String rola) {
        this.korime = korime;
        this.rola = rola;
    }
    
}
