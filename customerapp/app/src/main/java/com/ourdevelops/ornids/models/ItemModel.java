package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class ItemModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("item_id")
    private int item_id;

    @Expose
    @SerializedName("item_name")
    private String item_name;

    @Expose
    @SerializedName("item_price")
    private String item_price;

    @Expose
    @SerializedName("promo_price")
    private String promo_price;

    @Expose
    @SerializedName("category_name_item")
    private String item_category;

    @Expose
    @SerializedName("item_desc")
    private String item_desc;

    @Expose
    @SerializedName("item_image")
    private String item_image;

    @Expose
    @SerializedName("promo_status")
    private String promo_status;



    public int getId_item() {
        return item_id;
    }

    public void setId_item(int item_id) {
        this.item_id = item_id;
    }

    public String getNama_item() {
        return item_name;
    }

    public void setNama_item(String item_name) {
        this.item_name = item_name;
    }

    public String getHarga_item() {
        return item_price;
    }

    public void setHarga_item(String item_price) {
        this.item_price = item_price;
    }

    public String getHarga_promo() {
        return promo_price;
    }

    public void setHarga_promo(String promo_price) {
        this.promo_price = promo_price;
    }

    public String getKategori_item() {
        return item_category;
    }

    public void setKategori_item(String item_category) {
        this.item_category = item_category;
    }

    public String getDeskripsi_item() {
        return item_desc;
    }

    public void setDeskripsi_item(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getFoto_item() {
        return item_image;
    }

    public void setFoto_item(String item_image) {
        this.item_image = item_image;
    }

    public String getStatus_promo() {
        return promo_status;
    }

    public void setStatus_promo(String promo_status) {
        this.promo_status = promo_status;
    }


}
