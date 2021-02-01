package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class CatMerchantModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("category_merchant_id")
    private String category_merchant_id;

    @Expose
    @SerializedName("category_name")
    private String category_name;

    @Expose
    @SerializedName("category_images")
    private String category_images;

    @Expose
    @SerializedName("service_id")
    private String service_id;





    public String getId_kategori_merchant() {
        return category_merchant_id;
    }

    public void setId_kategori_merchant(String category_merchant_id) {
        this.category_merchant_id = category_merchant_id;
    }

    public String getNama_kategori() {
        return category_name;
    }

    public void setNama_kategori(String category_name) {
        this.category_name = category_name;
    }

    public String getFoto_kategori() {
        return category_images;
    }

    public void setFoto_kategori(String category_images) {
        this.category_images = category_images;
    }

    public String getId_fitur() {
        return service_id;
    }

    public void setId_fitur(String service_id) {
        this.service_id = service_id;
    }


}
