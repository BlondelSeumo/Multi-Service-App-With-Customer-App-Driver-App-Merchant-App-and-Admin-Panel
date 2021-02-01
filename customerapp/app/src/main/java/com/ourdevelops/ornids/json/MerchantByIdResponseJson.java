package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.CatItemModel;
import com.ourdevelops.ornids.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class MerchantByIdResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("idmerchant")
    @Expose
    private String idmerchant;

    @SerializedName("namamerchant")
    @Expose
    private String namamerchant;

    @SerializedName("alamatmerchant")
    @Expose
    private String alamatmerchant;

    @SerializedName("latmerchant")
    @Expose
    private String latmerchant;

    @SerializedName("longmerchant")
    @Expose
    private String longmerchant;

    @SerializedName("bukamerchant")
    @Expose
    private String bukamerchant;

    @SerializedName("tutupmerchant")
    @Expose
    private String tutupmerchant;

    @SerializedName("descmerchant")
    @Expose
    private String descmerchant;

    @SerializedName("fotomerchant")
    @Expose
    private String fotomerchant;

    @SerializedName("telpmerchant")
    @Expose
    private String telpmerchant;

    @SerializedName("category")
    @Expose
    private String kategorimerchant;

    @SerializedName("partner")
    @Expose
    private String partner;

    @SerializedName("promo")
    @Expose
    private String promo;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("idfitur")
    @Expose
    private String idfitur;

    @SerializedName("itembyid")
    @Expose
    private List<ItemModel> item = new ArrayList<>();

    @SerializedName("kategoriitem")
    @Expose
    private List<CatItemModel> category = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemModel> getData() {
        return item;
    }

    public void setData(List<ItemModel> item) {
        this.item = item;
    }

    public List<CatItemModel> getKategori() {
        return category;
    }

    public void setKategori(List<CatItemModel> category) {
        this.category = category;
    }

    public String getIdmerchant() {
        return idmerchant;
    }

    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    public String getNamamerchant() {
        return namamerchant;
    }

    public void setNamamerchant(String namamerchant) {
        this.namamerchant = namamerchant;
    }

    public String getAlamatmerchant() {
        return alamatmerchant;
    }

    public void setAlamatmerchant(String alamatmerchant) {
        this.alamatmerchant = alamatmerchant;
    }

    public String getLatmerchant() {
        return latmerchant;
    }

    public void setLatmerchant(String latmerchant) {
        this.latmerchant = latmerchant;
    }

    public String getLongmerchant() {
        return longmerchant;
    }

    public void setLongmerchant(String longmerchant) {
        this.longmerchant = longmerchant;
    }

    public String getBukamerchant() {
        return bukamerchant;
    }

    public void setBukamerchant(String bukamerchant) {
        this.bukamerchant = bukamerchant;
    }

    public String getTutupmerchant() {
        return tutupmerchant;
    }

    public void setTutupmerchant(String tutupmerchant) {
        this.tutupmerchant = tutupmerchant;
    }

    public String getDescmerchant() {
        return descmerchant;
    }

    public void setDescmerchant(String descmerchant) {
        this.descmerchant = descmerchant;
    }

    public String getFotomerchant() {
        return fotomerchant;
    }

    public void setFotomerchant(String fotomerchant) {
        this.fotomerchant = fotomerchant;
    }

    public String getTelpmerchant() {
        return telpmerchant;
    }

    public void setTelpmerchant(String telpmerchant) {
        this.telpmerchant = telpmerchant;
    }

    public String getKategorimerchant() {
        return kategorimerchant;
    }

    public void setKategorimerchant(String kategorimerchant) {
        this.kategorimerchant = kategorimerchant;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getIdfitur() {
        return idfitur;
    }

    public void setIdfitur(String idfitur) {
        this.idfitur = idfitur;
    }
}
