package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class RatingModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("customer_image")
    private String customer_image;

    @Expose
    @SerializedName("customer_fullname")
    private String customer_fullname;

    @Expose
    @SerializedName("update_at")
    private String update_at;

    @Expose
    @SerializedName("note")
    private String note;

    @Expose
    @SerializedName("rating")
    private String rating;





    public String getFotopelanggan() {
        return customer_image;
    }

    public void setFotopelanggan(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getFullnama() {
        return customer_fullname;
    }

    public void setFullnama(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getCatatan() {
        return note;
    }

    public void setCatatan(String note) {
        this.note = note;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
