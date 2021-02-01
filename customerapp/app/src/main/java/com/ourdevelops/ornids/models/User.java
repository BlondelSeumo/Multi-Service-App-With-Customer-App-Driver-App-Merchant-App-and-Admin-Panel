package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class User extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("customer_fullname")
    @Expose
    private String customer_fullname;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone_number")
    @Expose
    private String noTelepon;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("created_on")
    @Expose
    private String createdOn;

    @SerializedName("dob")
    @Expose
    private String tglLahir;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("customer_image")
    @Expose
    private String customer_image;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    private long walletSaldo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullnama() {
        return customer_fullname;
    }

    public void setFullnama(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getWalletSaldo() {
        return walletSaldo;
    }

    public void setWalletSaldo(long walletSaldo) {
        this.walletSaldo = walletSaldo;
    }

    public String getFotopelanggan() {
        return customer_image;
    }

    public void setFotopelanggan(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}
