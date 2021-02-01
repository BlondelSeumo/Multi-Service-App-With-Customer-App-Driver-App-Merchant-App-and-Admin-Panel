package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 12/20/2019.
 */

public class WalletModel extends RealmObject implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("wallet_amount")
    @Expose
    private String wallet_amount;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("holder_name")
    @Expose
    private String namapemilik;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJumlah() {
        return wallet_amount;
    }

    public void setJumlah(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getWaktu() {
        return date;
    }

    public void setWaktu(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamapemilik() {
        return namapemilik;
    }

    public void setNamapemilik(String status) {
        this.namapemilik = namapemilik;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
