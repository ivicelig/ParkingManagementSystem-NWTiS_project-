package org.foi.nwtis.ivicelig.web.podaci;

public class Lokacija {

    private String latitude;
    private String longitude;

    /**
     *
     */
    public Lokacija() {
    }

    /**
     *
     * @param latitude
     * @param longitude
     */
    public Lokacija(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     *
     * @param latitude
     * @param longitude
     */
    public void postavi(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
