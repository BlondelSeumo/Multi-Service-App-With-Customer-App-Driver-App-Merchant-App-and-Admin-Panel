package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class CatItemModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("category_name_item")
    private String category_name_item;

    @Expose
    @SerializedName("category_item_id")
    private String category_item_id;





    public String getId_kategori_item() {
        return category_item_id;
    }

    public void setId_kategori_item(String category_item_id) {
        this.category_item_id = category_item_id;
    }

    public String getNama_kategori() {
        return category_name_item;
    }

    public void setNama_kategori(String category_name) {
        this.category_name_item = category_name;
    }


}
