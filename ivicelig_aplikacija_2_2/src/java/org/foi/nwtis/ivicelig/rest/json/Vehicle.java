package org.foi.nwtis.ivicelig.rest.json;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("registracija")
    @Expose
    private String registracija;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

}
