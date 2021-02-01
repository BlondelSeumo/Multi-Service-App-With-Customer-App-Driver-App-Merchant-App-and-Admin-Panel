package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class ItemOrderModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName("item_name")
    private String item_name;

    @Expose
    @SerializedName("item_amount")
    private String item_amount;


    public String getNama_item() {
        return item_name;
    }

    public void setNama_item(String item_name) {
        this.item_name = item_name;
    }

    public String getJumlah_item() {
        return item_amount;
    }

    public void setJumlah_item(String item_amount) {
        this.item_amount = item_amount;
    }


}
