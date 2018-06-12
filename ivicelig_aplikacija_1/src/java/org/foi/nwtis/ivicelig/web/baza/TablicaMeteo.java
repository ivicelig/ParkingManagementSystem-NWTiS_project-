package org.foi.nwtis.ivicelig.web.baza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.web.json.Main;
import org.foi.nwtis.ivicelig.web.json.ResponseJSON;
import org.foi.nwtis.ivicelig.web.json.Weather;
import org.foi.nwtis.ivicelig.web.json.Wind;
import org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci;

/**
 *
 * @author Ivica
 */
public class TablicaMeteo extends TablicaAbstraktna {

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
        MeteoPodaci mp = (MeteoPodaci) t;
        ResponseJSON rj = mp.getRj();
        String vrijeme = rj.getWeather().get(0).getMain();
        String vrijemeOpis = rj.getWeather().get(0).getDescription();
        String temp = rj.getMain().getTemp().toString();
        String tempMax = rj.getMain().getTempMax().toString();
        String tempMin = rj.getMain().getTempMin().toString();
        String vlaga = rj.getMain().getHumidity().toString();
        String tlak = rj.getMain().getPressure().toString();
        String vjetar = rj.getWind().getSpeed().toString();
        String vjetarSmjer = "0";
        if (rj.getWind().getDeg() != null) {
            vjetarSmjer = rj.getWind().getDeg().toString();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sCertDate = sdf.format(new Date());
        String upit = "INSERT INTO meteo(`id`,`vrijeme`, `vrijemeOpis`, `temp`, `tempMin`, `tempMax`, `vlaga`, `tlak`, `vjetar`, `vjetarSmjer`, `preuzeto`) VALUES "
                + "(" + String.valueOf(mp.getId()) + ",'" + vrijeme + "','" + vrijemeOpis + "'," + temp + "," + tempMin + ""
                + "," + tempMax + "," + vlaga + "," + tlak + "," + vjetar + "," + vjetarSmjer + ",'" + sCertDate + "')";
        return upit;
    }

    @Override
    public boolean update(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getAllRecords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public MeteoPodaci getLastRecordByID(String id) {
        MeteoPodaci mp = new MeteoPodaci();

        try {
            ResultSet rs;
            PreparedStatement pst;
            Connection con = getConnection();

            String stm = "SELECT * FROM meteo WHERE id=" + id + " ORDER BY idMeteo DESC LIMIT 1";

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();
            if (rs.next() == false) {
                mp = null;
            } else {

                ResponseJSON rj = new ResponseJSON();

                Weather w = new Weather();
                w.setMain(rs.getString(3));
                w.setDescription(rs.getString(4));
                List<Weather> weather = new ArrayList<>();
                weather.add(w);
                rj.setWeather(weather);
                Main main = new Main();
                main.setTemp(Double.parseDouble(rs.getString(5)));
                main.setTempMin(Double.parseDouble(rs.getString(6)));
                main.setTempMax(Double.parseDouble(rs.getString(7)));
                main.setHumidity(Double.parseDouble(rs.getString(8)));
                main.setPressure(Double.parseDouble(rs.getString(9)));
                rj.setMain(main);
                Wind wind = new Wind();
                wind.setSpeed(Double.parseDouble(rs.getString(10)));
                wind.setDeg(Double.parseDouble(rs.getString(11)));
                rj.setWind(wind);
                Date date = new Date();
                try {
                    date = parseDate(rs.getString(12), "yyyy-MM-dd HH:mm:ss.SSS");
                } catch (ParseException ex) {
                    Logger.getLogger(TablicaMeteo.class.getName()).log(Level.SEVERE, null, ex);
                }
                mp.setPreuzeto(date);
                mp.setId(Integer.parseInt(id));
                mp.setRj(rj);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return mp;
    }

    public List<MeteoPodaci> getN_LastRecordsByID(String id, int n) {

        List<MeteoPodaci> listaN_MeteoPodataka = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement pst;
            Connection con = getConnection();

            String stm = "SELECT * FROM meteo WHERE id=" + id + " ORDER BY idMeteo DESC LIMIT " + n;

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while (rs.next()) {

                MeteoPodaci mp = new MeteoPodaci();
                ResponseJSON rj = new ResponseJSON();

                Weather w = new Weather();
                w.setMain(rs.getString(3));
                w.setDescription(rs.getString(4));
                List<Weather> weather = new ArrayList<>();
                weather.add(w);
                rj.setWeather(weather);
                Main main = new Main();
                main.setTemp(Double.parseDouble(rs.getString(5)));
                main.setTempMin(Double.parseDouble(rs.getString(6)));
                main.setTempMax(Double.parseDouble(rs.getString(7)));
                main.setHumidity(Double.parseDouble(rs.getString(8)));
                main.setPressure(Double.parseDouble(rs.getString(9)));
                rj.setMain(main);
                Wind wind = new Wind();
                wind.setSpeed(Double.parseDouble(rs.getString(10)));
                wind.setDeg(Double.parseDouble(rs.getString(11)));
                rj.setWind(wind);
                Date date = new Date();
                try {
                    date = parseDate(rs.getString(12), "yyyy-MM-dd HH:mm:ss.SSS");
                } catch (ParseException ex) {
                    Logger.getLogger(TablicaMeteo.class.getName()).log(Level.SEVERE, null, ex);
                }
                mp.setPreuzeto(date);
                mp.setId(Integer.parseInt(id));
                mp.setRj(rj);
                listaN_MeteoPodataka.add(mp);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return listaN_MeteoPodataka;
    }

     public List<MeteoPodaci> vratiMeteoUINtervalu(String id, Timestamp od, Timestamp Do) {
        List<MeteoPodaci> listaPodataka = new ArrayList<>();
        Date odDatum = new Date(od.getTime());
        Date doDatum = new Date(Do.getTime());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String odDatumString =df.format(odDatum);
        String doDatumString = df.format(doDatum);
        try {
            ResultSet rs;
            PreparedStatement pst;
            Connection con = getConnection();

            String stm = "SELECT * FROM meteo WHERE id=" + id + " AND preuzeto >= '"+odDatumString+"' AND preuzeto <= '"+doDatumString+"'";

            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while (rs.next()) {

                MeteoPodaci mp = new MeteoPodaci();
                ResponseJSON rj = new ResponseJSON();

                Weather w = new Weather();
                w.setMain(rs.getString(3));
                w.setDescription(rs.getString(4));
                List<Weather> weather = new ArrayList<>();
                weather.add(w);
                rj.setWeather(weather);
                Main main = new Main();
                main.setTemp(Double.parseDouble(rs.getString(5)));
                main.setTempMin(Double.parseDouble(rs.getString(6)));
                main.setTempMax(Double.parseDouble(rs.getString(7)));
                main.setHumidity(Double.parseDouble(rs.getString(8)));
                main.setPressure(Double.parseDouble(rs.getString(9)));
                rj.setMain(main);
                Wind wind = new Wind();
                wind.setSpeed(Double.parseDouble(rs.getString(10)));
                wind.setDeg(Double.parseDouble(rs.getString(11)));
                rj.setWind(wind);
                Date date = new Date();
                try {
                    date = parseDate(rs.getString(12), "yyyy-MM-dd HH:mm:ss.SSS");
                } catch (ParseException ex) {
                    Logger.getLogger(TablicaMeteo.class.getName()).log(Level.SEVERE, null, ex);
                }
                mp.setPreuzeto(date);
                mp.setId(Integer.parseInt(id));
                mp.setRj(rj);
                listaPodataka.add(mp);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return listaPodataka;
    }

    private Date parseDate(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
    
}
