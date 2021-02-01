package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class EditProfileRequestJson {

    @SerializedName("partner_id")
    @Expose
    private String idmitra;

    @SerializedName("no_telepon_lama")
    @Expose
    private String noteleponlama;

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("customer_fullname")
    @Expose
    private String nama;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    public String getIdmitra() {
        return idmitra;
    }

    public void setIdmitra(String idmitra) {
        this.idmitra = idmitra;
    }

    public String getNoteleponlama() {
        return noteleponlama;
    }

    public void setNoteleponlama(String noteleponlama) {
        this.noteleponlama = noteleponlama;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


}
