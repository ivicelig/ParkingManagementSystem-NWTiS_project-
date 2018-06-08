/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ivicelig.web.podaci.Parkiraliste;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Ivica
 */
public abstract class TablicaAbstraktna implements TablicaInterface {

    private String url;
    private String bazaPodataka;
    private String lozinka;
    private String korisnik;

    public TablicaAbstraktna() {
        getConnectionDataAndLoadDrivers();
    }

    private void getConnectionDataAndLoadDrivers() {

        BP_Konfiguracija bpk
                = (BP_Konfiguracija) SlusacAplikacije.sc.getAttribute("BP_Konfig");
        url = bpk.getServerDatabase();
        bazaPodataka = bpk.getUserDatabase();
        korisnik = bpk.getUserUsername();
        lozinka = bpk.getUserPassword();

        try {
            Class.forName(bpk.getDriverDatabase());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TablicaParkiraliste.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public  Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url + bazaPodataka,
                    korisnik, lozinka);
        } catch (SQLException ex) {
            Logger.getLogger(TablicaParkiraliste.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}
