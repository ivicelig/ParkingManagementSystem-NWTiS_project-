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
public class PracenjeMjesta {
    private int idParkiralista;
    private int kapacitet;
    private int zauzeto = 0;

    public int getIdParkiralista() {
        return idParkiralista;
    }

    public void setIdParkiralista(int idParkiralista) {
        this.idParkiralista = idParkiralista;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public int getZauzeto() {
        return zauzeto;
    }

    public void setZauzeto(int zauzeto) {
        this.zauzeto = zauzeto;
    }

    public PracenjeMjesta(int idParkiralista, int kapacitet) {
        this.idParkiralista = idParkiralista;
        this.kapacitet = kapacitet;
       
    }
    
    public void ulaz(){
        zauzeto++;
        
    }
    public void izlaz(){
        zauzeto--;
        
    }
    
}
