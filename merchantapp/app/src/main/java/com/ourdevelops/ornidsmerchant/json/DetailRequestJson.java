package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 24/02/2019.
 */

public class DetailRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("driver_id")
    @Expose
    private String idDriver;

    @SerializedName("customer_id")
    @Expose
    private String idpelanggan;

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

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public void setIdpelanggan(String idpelanggan) {
        this.idpelanggan = idpelanggan;
    }

}
