package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class ActiveCatRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("idkategori")
    @Expose
    private String idkategori;

    @SerializedName("status")
    @Expose
    private String status;

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(String idkategori) {
        this.idkategori = idkategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
