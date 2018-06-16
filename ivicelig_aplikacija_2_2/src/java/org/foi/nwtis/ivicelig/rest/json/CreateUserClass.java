package org.foi.nwtis.ivicelig.rest.json;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateUserClass {

    @SerializedName("ki")
    @Expose
    private String kime;
    @SerializedName("lozinka")
    @Expose
    private String lozinka;
    @SerializedName("prezime")
    @Expose
    private String prezime;
    @SerializedName("ime")
    @Expose
    private String ime;

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKime() {
        return kime;
    }

    public void setKime(String kime) {
        this.kime = kime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

}
