/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.podaci;

import java.util.Date;
import org.foi.nwtis.ivicelig.rest.json.ResponseJSON;


/**
 *
 * @author Ivica
 */
public class MeteoPodaci {
    private ResponseJSON rj;
    private int id;
    private Date preuzeto;

    public Date getPreuzeto() {
        return preuzeto;
    }

    public void setPreuzeto(Date preuzeto) {
        this.preuzeto = preuzeto;
    }

    public MeteoPodaci(ResponseJSON rj, int id) {
        this.rj = rj;
        this.id = id;
    }
    public MeteoPodaci(){
        
    }

    public ResponseJSON getRj() {
        return rj;
    }

    public void setRj(ResponseJSON rj) {
        this.rj = rj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}