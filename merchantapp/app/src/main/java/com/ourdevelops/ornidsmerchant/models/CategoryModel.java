package com.ourdevelops.ornidsmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class CategoryModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("category_merchant_id")
    private int idkategori;

    @Expose
    @SerializedName("category_name")
    private String nama;

    public int getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(int idkategori) {
        this.idkategori = idkategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


}
