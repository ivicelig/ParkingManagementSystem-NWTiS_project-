package org.foi.nwtis.ivicelig.json;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MQTTjson implements Serializable{


    @SerializedName("parkiraliste")
    @Expose
    private int parkiraliste;
    @SerializedName("vozilo")
    @Expose
    private String vozilo;
    @SerializedName("akcija")
    @Expose
    private int akcija;
    @SerializedName("vrijeme")
    @Expose
    private String vrijeme;

    

    public String getVozilo() {
        return vozilo;
    }

    public void setVozilo(String vozilo) {
        this.vozilo = vozilo;
    }

   

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public int getParkiraliste() {
        return parkiraliste;
    }

    public void setParkiraliste(int parkiraliste) {
        this.parkiraliste = parkiraliste;
    }

    public int getAkcija() {
        return akcija;
    }

    public void setAkcija(int akcija) {
        this.akcija = akcija;
    }
    

   

}
