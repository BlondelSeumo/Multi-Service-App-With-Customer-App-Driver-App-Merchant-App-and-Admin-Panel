package com.ourdevelops.ornidsmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class CustomerModel extends RealmObject implements Serializable{

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("customer_fullname")
    private String customer_fullname;

    @Expose
    @SerializedName("phone_number")
    private String noTelepon;

    @Expose
    @SerializedName("customer_image")
    private String photo;

    @Expose
    @SerializedName("token")
    private String token;



    public String getFullnama() {
        return customer_fullname;
    }

    public void setFullnama(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getFoto() {
        return photo;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
