package com.ourdevelops.ornidsdriver.models;

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

    @SerializedName("driver_name")
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

    @SerializedName("driver_address")
    @Expose
    private String alamat;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("dob")
    @Expose
    private String tglLahir;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reg_id")
    @Expose
    private String token;

    @SerializedName("photo")
    @Expose
    private String fotodriver;
////
    @SerializedName("vehicle_id")
    @Expose
    private String idk;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("jenis")
    @Expose
    private String jenis;

    @SerializedName("vehicle_registration_number")
    @Expose
    private String vehicle_registration_number;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("balance")
    @Expose
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public String getFotodriver() {
        return fotodriver;
    }

    public void setFotodriver(String fotodriver) {
        this.fotodriver = fotodriver;
    }

    public String getMerek() {
        return brand;
    }

    public void setMerek(String brand) {
        this.brand = brand;
    }

    public String getTipe() {
        return type;
    }

    public void setTipe(String type) {
        this.type = type;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getNomorkendaraan() {
        return vehicle_registration_number;
    }

    public void setNomorkendaraan(String vehicle_registration_number) {
        this.vehicle_registration_number = vehicle_registration_number;
    }

    public String getWarna() {
        return color;
    }

    public void setWarna(String color) {
        this.color = color;
    }

    public String getIdk() {
        return idk;
    }

    public void setIdk(String idk) {
        this.idk = idk;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}
