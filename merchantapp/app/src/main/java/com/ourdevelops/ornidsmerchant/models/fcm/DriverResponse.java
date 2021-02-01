package com.ourdevelops.ornidsmerchant.models.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class DriverResponse implements Serializable{

    public static final String ACCEPT = "2";
    public static final String REJECT = "0";

    @Expose
    @SerializedName("driver_id")
    private String iddriver;

    @Expose
    @SerializedName("customer_id")
    private String idpelanggan;


    @Expose
    @SerializedName("transaction_id")
    private String idTransaksi;

    @Expose
    @SerializedName("response")
    private String response;

    @Expose
    @SerializedName("type")
    public int type;



    public String getIddriver() {
        return iddriver;
    }

    public void setIddriver(String iddriver) {
        this.iddriver = iddriver;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public void setIdpelanggan(String idpelanggan) {
        this.idpelanggan = idpelanggan;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
