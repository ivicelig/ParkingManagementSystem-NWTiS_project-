package org.foi.nwtis.ivicelig.web.json;




import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkiralistaForJson {

    @SerializedName("odgovor")
    @Expose
    private List<Odgovor> odgovor = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("poruka")
    @Expose
    private String poruka;

    public List<Odgovor> getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(List<Odgovor> odgovor) {
        this.odgovor = odgovor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

}
