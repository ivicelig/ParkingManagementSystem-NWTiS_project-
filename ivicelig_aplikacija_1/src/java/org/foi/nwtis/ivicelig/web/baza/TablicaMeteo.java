package org.foi.nwtis.ivicelig.web.baza;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.foi.nwtis.ivicelig.web.json.ResponseJSON;
import org.foi.nwtis.ivicelig.web.podaci.MeteoPodaci;

/**
 *
 * @author Ivica
 */
public class TablicaMeteo extends TablicaAbstraktna {
    
    @Override
    public  boolean insert(Object t) {
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
        if(rj.getWind().getDeg() != null){
        vjetarSmjer = rj.getWind().getDeg().toString();
        }
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
              String sCertDate = sdf.format(new Date());
        String upit = "INSERT INTO meteo(`id`,`vrijeme`, `vrijemeOpis`, `temp`, `tempMin`, `tempMax`, `vlaga`, `tlak`, `vjetar`, `vjetarSmjer`, `preuzeto`) VALUES "
                + "("+String.valueOf(mp.getId())+",'"+vrijeme+"','"+vrijemeOpis+"',"+temp+","+tempMin+""
                + ","+tempMax+","+vlaga+","+tlak+","+vjetar+","+vjetarSmjer+",'"+sCertDate+"')";
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

    

}
