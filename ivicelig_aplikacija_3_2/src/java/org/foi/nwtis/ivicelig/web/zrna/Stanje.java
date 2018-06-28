/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.zrna;

/**
 *
 * @author Ivica
 */
public class Stanje {
    private int id;
    private int stanje;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStanje() {
        return stanje;
    }

    public void setStanje(int stanje) {
        this.stanje = stanje;
    }

    public Stanje(int id, int stanje) {
        this.id = id;
        this.stanje = stanje;
    }
    
}
