package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class HomeRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("idmerchant")
    @Expose
    private String idmerchant;

    @SerializedName("idmitra")
    @Expose
    private String idmitra;

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getIdmerchant() {
        return idmerchant;
    }

    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    public String getIdmitra() {
        return idmitra;
    }

    public void setIdmitra(String idmitra) {
        this.idmitra = idmitra;
    }


}
