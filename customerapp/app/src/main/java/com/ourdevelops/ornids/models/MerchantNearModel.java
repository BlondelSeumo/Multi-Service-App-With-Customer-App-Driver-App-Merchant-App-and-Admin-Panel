package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class MerchantNearModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("merchant_id")
    private String merchant_id;

    @Expose
    @SerializedName("merchant_name")
    private String merchant_name;

    @Expose
    @SerializedName("merchant_address")
    private String merchant_address;

    @Expose
    @SerializedName("merchant_latitude")
    private String merchant_latitude;

    @Expose
    @SerializedName("merchant_longitude")
    private String merchant_longitude;

    @Expose
    @SerializedName("open_hour")
    private String open_hour;

    @Expose
    @SerializedName("close_hour")
    private String close_hour;

    @Expose
    @SerializedName("merchant_desc")
    private String merchant_desc;

    @Expose
    @SerializedName("category_name")
    private String merchant_category;

    @Expose
    @SerializedName("merchant_image")
    private String merchant_image;

    @Expose
    @SerializedName("merchant_telephone_number")
    private String merchant_telephone_number;

    @Expose
    @SerializedName("promo_status")
    private String promo_status;

    @Expose
    @SerializedName("distance")
    private String distance;

    public String getId_merchant() {
        return merchant_id;
    }

    public void setId_merchant(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getNama_merchant() {
        return merchant_name;
    }

    public void setNama_merchant(String merchant_name) {
        this.merchant_name = merchant_name;
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

    public String getDeskripsi_merchant() {
        return merchant_desc;
    }

    public void setDeskripsi_merchant(String merchant_desc) {
        this.merchant_desc = merchant_desc;
    }

    public String getCategory_merchant() {
        return merchant_category;
    }

    public void setCategory_merchant(String merchant_category) {
        this.merchant_category = merchant_category;
    }

    public String getFoto_merchant() {
        return merchant_image;
    }

    public void setFoto_merchant(String merchant_image) {
        this.merchant_image = merchant_image;
    }

    public String getTelepon_merchant() {
        return merchant_telephone_number;
    }

    public void setTelepon_merchant(String merchant_telephone_number) {
        this.merchant_telephone_number = merchant_telephone_number;
    }


    public String getStatus_promo() {
        return promo_status;
    }

    public void setStatus_promo(String promo_status) {
        this.promo_status = promo_status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
