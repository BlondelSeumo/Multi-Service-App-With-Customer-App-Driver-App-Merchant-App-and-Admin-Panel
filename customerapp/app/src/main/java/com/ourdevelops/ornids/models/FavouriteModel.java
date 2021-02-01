package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class FavouriteModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("news_id")
    private String news_id;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("news_images")
    private String news_images;

    @Expose
    @SerializedName("news_created")
    private String news_created;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("userid")
    private String userid;



    public String getIdberita() {
        return news_id;
    }

    public void setIdberita(String news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFotoberita() {
        return news_images;
    }

    public void setFotoberita(String news_images) {
        this.news_images = news_images;
    }

    public String getCreatedberita() {
        return news_created;
    }

    public void setCreatedberita(String news_created) {
        this.news_created = news_created;
    }

    public String getKategori() {
        return category;
    }

    public void setKategori(String category) {
        this.category = category;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
