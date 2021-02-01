package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class EditMerchantRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("latitude")
    @Expose
    private String merchant_latitude;

    @SerializedName("longitude")
    @Expose
    private String merchant_longitude;

    @SerializedName("open_hour")
    @Expose
    private String open_hour;

    @SerializedName("close_hour")
    @Expose
    private String close_hour;

    @SerializedName("photo")
    @Expose
    private String merchant_image;

    @SerializedName("foto_lama")
    @Expose
    private String foto_lama;

    @SerializedName("nama")
    @Expose
    private String namamerchant;



    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getFoto_lama() {
        return foto_lama;
    }

    public void setFoto_lama(String foto_lama) {
        this.foto_lama = foto_lama;
    }

    public String getNamamerchant() {
        return namamerchant;
    }

    public void setNamamerchant(String namamerchant) {
        this.namamerchant = namamerchant;
    }


}
