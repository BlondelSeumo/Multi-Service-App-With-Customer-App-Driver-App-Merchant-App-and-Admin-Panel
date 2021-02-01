package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class BankModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("bank_id")
    private String bank_id;

    @Expose
    @SerializedName("bank_name")
    private String bank_name;

    @Expose
    @SerializedName("bank_logo")
    private String bank_logo;

    @Expose
    @SerializedName("bank_account")
    private String bank_account;


    public String getId_bank() {
        return bank_id;
    }

    public void setId_bank(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getNama_bank() {
        return bank_name;
    }

    public void setNama_bank(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getRekening_bank() {
        return bank_account;
    }

    public void setRekening_bank(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getImage_bank() {
        return bank_logo;
    }

    public void setImage_bank(String bank_logo) {
        this.bank_logo = bank_logo;
    }
}
