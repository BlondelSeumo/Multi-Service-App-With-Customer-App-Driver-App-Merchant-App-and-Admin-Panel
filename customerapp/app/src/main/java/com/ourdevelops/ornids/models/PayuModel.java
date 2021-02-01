package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class PayuModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("payu_key")
    private String payukey;

    @Expose
    @SerializedName("payu_id")
    private String payuid;

    @Expose
    @SerializedName("payu_salt")
    private String payusalt;

    @Expose
    @SerializedName("payu_debug")
    private String payudebug;

    @Expose
    @SerializedName("active")
    private String active;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayukey() {
        return payukey;
    }

    public void setPayukey(String payukey) {
        this.payukey = payukey;
    }

    public String getPayuid() {
        return payuid;
    }

    public void setPayuid(String payuid) {
        this.payuid = payuid;
    }


    public String getPayudebug() {
        return payudebug;
    }

    public void setPayudebug(String payudebug) {
        this.payudebug = payudebug;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPayusalt() {
        return payusalt;
    }

    public void setPayusalt(String payusalt) {
        this.payusalt = payusalt;
    }
}
