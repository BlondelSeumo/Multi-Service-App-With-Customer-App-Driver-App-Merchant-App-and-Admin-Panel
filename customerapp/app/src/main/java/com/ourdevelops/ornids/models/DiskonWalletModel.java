package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 12/20/2019.
 */

public class DiskonWalletModel extends RealmObject implements Serializable {

    @SerializedName("diskon")
    @Expose
    private String diskon;
    @SerializedName("final_cost")
    @Expose
    private Double biayaAkhir;

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public Double getBiayaAkhir() {
        return biayaAkhir;
    }

    public void setBiayaAkhir(Double biayaAkhir) {
        this.biayaAkhir = biayaAkhir;
    }
}
