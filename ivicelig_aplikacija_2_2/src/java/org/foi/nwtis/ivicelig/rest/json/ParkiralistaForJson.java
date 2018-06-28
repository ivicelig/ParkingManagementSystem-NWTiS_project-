package org.foi.nwtis.ivicelig.rest.json;




import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkiralistaForJson {

    @SerializedName("odgovor")
    @Expose
    private List<OdgovorParkiralista> odgovor = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("poruka")
    @Expose
    private String poruka;

    public List<OdgovorParkiralista> getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(List<OdgovorParkiralista> odgovor) {
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
