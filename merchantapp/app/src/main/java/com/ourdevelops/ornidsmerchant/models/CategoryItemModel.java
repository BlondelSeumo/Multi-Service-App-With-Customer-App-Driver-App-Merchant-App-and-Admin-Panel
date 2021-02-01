package com.ourdevelops.ornidsmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class CategoryItemModel extends RealmObject implements Serializable{

    @Expose
    @SerializedName("category_item_id")
    private String category_item_id;

    @Expose
    @SerializedName("category_name_item")
    private String category_name_item;

    @Expose
    @SerializedName("category_status")
    private String category_status;

    @Expose
    @SerializedName("total_item")
    private String total_item;



    public String getId_kategori_item() {
        return category_item_id;
    }

    public void setId_kategori_item(String category_item_id) {
        this.category_item_id = category_item_id;
    }

    public String getNama_kategori_item() {
        return category_name_item;
    }

    public void setNama_kategori_item(String category_name_item) {
        this.category_name_item = category_name_item;
    }

    public String getStatus_kategori() {
        return category_status;
    }

    public void setStatus_kategori(String category_status) {
        this.category_status = category_status;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_itemoken(String total_item) {
        this.total_item = total_item;
    }

}
