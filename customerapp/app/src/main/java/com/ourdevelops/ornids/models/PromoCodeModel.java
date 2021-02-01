package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PromoCodeModel {
    @Expose
    @SerializedName("promo_title")
    private String namapromo;

    @Expose
    @SerializedName("promo_code")
    private String promocode;

    @Expose
    @SerializedName("expired")
    private Date expired;

    @Expose
    @SerializedName("promo_image")
    private String imagepromo;


    public String getNamapromo() {
        return namapromo;
    }

    public void setNamapromo(String namapromo) {
        this.namapromo = namapromo;
    }


    public String getImagepromo() {
        return imagepromo;
    }

    public void setImagepromo(String imagepromo) {
        this.imagepromo = imagepromo;
    }

    public String getKodepromo() {
        return promocode;
    }

    public void setKodepromo(String promocode) {
        this.promocode = promocode;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
