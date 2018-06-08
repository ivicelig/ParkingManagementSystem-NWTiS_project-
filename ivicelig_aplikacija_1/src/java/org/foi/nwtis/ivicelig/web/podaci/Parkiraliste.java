package org.foi.nwtis.ivicelig.web.podaci;

public class Parkiraliste {

    private int id;
    private String naziv;
    private String adresa;
    private Lokacija geoloc;

    /**
     *
     */
    public Parkiraliste() {
    }

    /**
     *
     * @param id
     * @param naziv
     * @param adresa
     * @param geoloc
     */
    public Parkiraliste(int id, String naziv, String adresa, Lokacija geoloc) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.geoloc = geoloc;
    }

    /**
     *
     * @return
     */
    public Lokacija getGeoloc() {
        return geoloc;
    }

    /**
     *
     * @param geoloc
     */
    public void setGeoloc(Lokacija geoloc) {
        this.geoloc = geoloc;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     *
     * @param naziv
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     *
     * @return
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     *
     * @param adresa
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
