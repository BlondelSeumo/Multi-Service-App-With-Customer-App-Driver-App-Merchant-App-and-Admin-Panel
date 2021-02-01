package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class AddEditKategoriRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("idmerchant")
    @Expose
    private String idmerchant;

    @SerializedName("namakategori")
    @Expose
    private String namakategori;

    @SerializedName("status")
    @Expose
    private String status;

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdmerchant() {
        return idmerchant;
    }

    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    public String getNamakategori() {
        return namakategori;
    }

    public void setNamakategori(String namakategori) {
        this.namakategori = namakategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
