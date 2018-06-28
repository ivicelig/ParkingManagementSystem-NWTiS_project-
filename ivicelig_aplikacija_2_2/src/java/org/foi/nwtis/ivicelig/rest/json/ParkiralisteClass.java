package org.foi.nwtis.ivicelig.rest.json;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkiralisteClass {

    @SerializedName("korime")
    @Expose
    private String korime;
    @SerializedName("lozinka")
    @Expose
    private String lozinka;
    @SerializedName("naziv")
    @Expose
    private String naziv;
    @SerializedName("adresa")
    @Expose
    private String adresa;
    @SerializedName("kapacitet")
    @Expose
    private int kapacitet;

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    

}
