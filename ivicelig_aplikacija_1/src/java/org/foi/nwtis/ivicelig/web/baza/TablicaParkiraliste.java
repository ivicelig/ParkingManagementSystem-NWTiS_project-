package org.foi.nwtis.ivicelig.web.baza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.foi.nwtis.ivicelig.web.podaci.Lokacija;
import org.foi.nwtis.ivicelig.web.podaci.Parkiraliste;

/**
 *
 * @author Ivica
 */
public class TablicaParkiraliste extends TablicaAbstraktna {

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
    public String createInsertQuery(Object t) {
        Parkiraliste p = (Parkiraliste) t;
        String naziv = p.getNaziv();
        String adresa = p.getAdresa();
        Lokacija l = p.getGeoloc();

        String upit = "INSERT INTO parkiralista"
                + "(naziv,adresa,latitude,longitude)"
                + "VALUES('" + naziv + "','" + adresa + "',"
                + l.getLatitude() + "," + l.getLongitude() + ")";
        return upit;
    }

    @Override
    public List<Parkiraliste> getAllRecords() {

        List<Parkiraliste> parkiralista = new ArrayList<>();

        try {
            ResultSet rs = null;
            PreparedStatement pst = null;
            Connection con = getConnection();

            String stm = "SELECT * FROM parkiralista";
            

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while (rs.next()) {
                Lokacija l = new Lokacija(rs.getString(4), rs.getString(5));

                Parkiraliste parkiraliste = new Parkiraliste();
                parkiraliste.setId(Integer.parseInt(rs.getString(1)));
                parkiraliste.setNaziv(rs.getString(2));
                parkiraliste.setAdresa(rs.getString(3));
                parkiraliste.setGeoloc(l);
                parkiralista.add(parkiraliste);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parkiralista;

    }

    @Override
    public boolean update(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
