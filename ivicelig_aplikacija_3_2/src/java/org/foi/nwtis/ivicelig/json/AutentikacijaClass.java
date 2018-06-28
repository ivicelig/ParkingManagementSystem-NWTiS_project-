package org.foi.nwtis.ivicelig.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutentikacijaClass {

    @SerializedName("lozinka")
    @Expose
    private String lozinka;

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

}
