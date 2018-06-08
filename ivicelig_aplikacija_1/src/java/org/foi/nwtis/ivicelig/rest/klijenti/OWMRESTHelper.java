/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.ivicelig.rest.klijenti;

/**
 *
 * @author dkermek
 */
public class OWMRESTHelper {
    private static final String OWM_BASE_URI = "http://api.openweathermap.org/data/2.5/";    
    private String apiKey;

    /**
     *
     * @param apiKey
     */
    public OWMRESTHelper(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     *
     * @return
     */
    public static String getOWM_BASE_URI() {
        return OWM_BASE_URI;
    }

    /**
     *
     * @return
     */
    public static String getOWM_Current_Path() {
        return "weather";
    }
        
    /**
     *
     * @return
     */
    public static String getOWM_Forecast_Path() {
        return "forecast";
    }
        
    /**
     *
     * @return
     */
    public static String getOWM_ForecastDaily_Path() {
        return "forecast/daily";
    }
        
    /**
     *
     * @return
     */
    public static String getOWM_StationsNear_Path() {
        return "station/find";
    }
        
    /**
     *
     * @return
     */
    public static String getOWM_Station_Path() {
        return "station";
    }
        
}
