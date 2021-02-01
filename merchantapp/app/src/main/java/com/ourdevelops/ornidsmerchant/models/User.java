package com.ourdevelops.ornidsmerchant.models;

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
    @SerializedName("partner_id")
    @Expose
    private String id;

    @SerializedName("partner_name")
    @Expose
    private String namamitra;

    @SerializedName("partner_address")
    @Expose
    private String partner_address;

    @SerializedName("partner_email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("partner_telephone")
    @Expose
    private String noTelepon;

    @SerializedName("partner_phone")
    @Expose
    private String phone;

    @SerializedName("partner_country_code")
    @Expose
    private String countrycode;
///
    @SerializedName("merchant_id")
    @Expose
    private String merchant_id;

    @SerializedName("balance")
    @Expose
    private long walletSaldo;

    @SerializedName("merchant_address")
    @Expose
    private String merchant_address;

    @SerializedName("merchant_latitude")
    @Expose
    private String merchant_latitude;

    @SerializedName("merchant_longitude")
    @Expose
    private String merchant_longitude;

    @SerializedName("open_hour")
    @Expose
    private String open_hour;

    @SerializedName("close_hour")
    @Expose
    private String close_hour;

    @SerializedName("merchant_image")
    @Expose
    private String merchant_image;

    @SerializedName("merchant_name")
    @Expose
    private String namamerchant;

    @SerializedName("merchant_token")
    @Expose
    private String merchant_token;

    @SerializedName("merchant_status")
    @Expose
    private String merchant_status;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamamitra() {
        return namamitra;
    }

    public void setNamamitra(String namamitra) {
        this.namamitra = namamitra;
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

    public String getAlamat_mitra() {
        return partner_address;
    }

    public void setAlamat_mitra(String partner_address) {
        this.partner_address = partner_address;
    }

    public long getWalletSaldo() {
        return walletSaldo;
    }

    public void setWalletSaldo(long walletSaldo) {
        this.walletSaldo = walletSaldo;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getId_merchant() {
        return merchant_id;
    }

    public void setId_merchant(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getAlamat_merchant() {
        return merchant_address;
    }

    public void setAlamat_merchant(String merchant_address) {
        this.merchant_address = merchant_address;
    }

    public String getLatitude_merchant() {
        return merchant_latitude;
    }

    public void setLatitude_merchant(String merchant_latitude) {
        this.merchant_latitude = merchant_latitude;
    }

    public String getLongitude_merchant() {
        return merchant_longitude;
    }

    public void setLongitude_merchant(String merchant_longitude) {
        this.merchant_longitude = merchant_longitude;
    }

    public String getJam_buka() {
        return open_hour;
    }

    public void setJam_buka(String open_hour) {
        this.open_hour = open_hour;
    }

    public String getJam_tutup() {
        return close_hour;
    }

    public void setJam_tutup(String close_hour) {
        this.close_hour = close_hour;
    }

    public String getFoto_merchant() {
        return merchant_image;
    }

    public void setFoto_merchant(String merchant_image) {
        this.merchant_image = merchant_image;
    }

    public String getNamamerchant() {
        return namamerchant;
    }

    public void setNamamerchant(String namamerchant) {
        this.namamerchant = namamerchant;
    }

    public String getToken_merchant() {
        return merchant_token;
    }

    public void setToken_merchant(String merchant_token) {
        this.merchant_token = merchant_token;
    }

    public String getStatus_merchant() {
        return merchant_status;
    }

    public void setStatus_merchant(String merchant_status) {
        this.merchant_status = merchant_status;
    }
}
