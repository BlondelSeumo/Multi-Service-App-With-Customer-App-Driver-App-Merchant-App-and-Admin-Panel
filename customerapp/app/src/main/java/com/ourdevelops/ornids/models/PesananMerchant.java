package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Balqis Studio on 1/5/2017.
 */

public class PesananMerchant extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("item_id")
    private int idItem;

    @Expose
    @SerializedName("total_cost")
    private long totalHarga;

    @Expose
    @SerializedName("qty")
    private int qty;

    @Expose
    @SerializedName("note")
    private String note = "";

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public long getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(long totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCatatan() {
        return note;
    }

    public void setCatatan(String note) {
        this.note = note;
    }
}
