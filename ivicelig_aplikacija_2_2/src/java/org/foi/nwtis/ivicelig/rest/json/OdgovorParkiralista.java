package org.foi.nwtis.ivicelig.rest.json;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdgovorParkiralista {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("naziv")
    @Expose
    private String naziv;
    @SerializedName("adresa")
    @Expose
    private String adresa;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    
    @SerializedName("kapacitet")
    @Expose
    private int kapacitet;

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @SerializedName("status")
    @Expose
    private String status;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
