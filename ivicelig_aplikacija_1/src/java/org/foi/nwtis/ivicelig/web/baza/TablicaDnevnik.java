/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.baza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.foi.nwtis.ivicelig.web.podaci.Dnevnik;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;

/**
 *
 * @author Ivica
 */
public class TablicaDnevnik extends TablicaAbstraktna{

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
    public List<Dnevnik> getAllRecords() {
          List<Dnevnik> dnevnik = new ArrayList<>();

        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            Connection con = getConnection();

            String stm = "SELECT * FROM dnevnik";
            

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while (rs.next()) {
                

                Dnevnik dnevnikZapis = new Dnevnik();
                dnevnikZapis.setId(Integer.parseInt(rs.getString(1)));
                dnevnikZapis.setNaziv(rs.getString(2));
                dnevnikZapis.setOpis(rs.getString(3));
                dnevnikZapis.setVrijeme(rs.getString(4));
                dnevnik.add(dnevnikZapis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dnevnik;

    }

    @Override
    public String createInsertQuery(Object t) {
         Dnevnik k = (Dnevnik) t;
        

        String upit = "INSERT INTO `dnevnik`(`id`, `naziv`, `opis`, `vrijeme`) VALUES (DEFAULT, '"+k.getNaziv()+"','"+k.getOpis()+"','"+k.getVrijeme()+"')";
        return upit;
    }
    
}
