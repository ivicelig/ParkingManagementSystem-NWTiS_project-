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
import java.util.List;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;

/**
 *
 * @author Ivica
 */
public class TablicaKorisnici extends TablicaAbstraktna {

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
    public String createInsertQuery(Object t) {
        Korisnik k = (Korisnik) t;
        String korIme = k.getKorIme();
        String lozinka = k.getLozinka();
        String ime = k.getIme();
        String prezime = k.getPrezime();

        String upit = "INSERT INTO korisnici(kor_ime, lozinka, ime, prezime) VALUES ('" + korIme + "','" + lozinka + "','" + ime + "','" + prezime + "')";
        return upit;
    }

    @Override
    public List<Korisnik> getAllRecords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Korisnik getByID(String korIme) {
        Korisnik korisnik = new Korisnik();

        try {
            ResultSet rs;
            PreparedStatement pst;
            Connection con = getConnection();

            String stm = "SELECT * FROM korisnici WHERE kor_ime='" + korIme + "'";
            System.out.println(stm);

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();
            if (rs.next() == false) {
                korisnik = null;
            } else {

                korisnik.setKorIme(rs.getString(1));
                korisnik.setLozinka(rs.getString(2));
                korisnik.setIme(rs.getString(3));
                korisnik.setPrezime(rs.getString(4));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return korisnik;
    }

}
