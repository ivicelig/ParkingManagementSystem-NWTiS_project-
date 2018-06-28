/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.baza;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.foi.nwtis.ivicelig.web.podaci.Grupa;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;

/**
 *
 * @author Ivica
 */
    public class TablicaGrupa extends TablicaAbstraktna{

    @Override
    public boolean insert(Object t) {
       try (
                Connection con = getConnection();
                Statement stmt = con.createStatement();) {
            String upit = createInsertQuery(t);
            stmt.execute(upit);
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getAllRecords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createInsertQuery(Object t) {
            Grupa k = (Grupa) t;
        

        String upit = "INSERT INTO `polaznici_grupe`(`kor_ime`, `gr_ime`) VALUES (\""+k.getKorime()+"\",\""+k.getRola()+"\")";
        return upit;
    }
    
}
