package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoModel {
    @Expose
    @SerializedName("photo")
    private String photo;

    @Expose
    @SerializedName("promotion_service")
    private int promotion_service;

    @Expose
    @SerializedName("promotion_link")
    private String promotion_link;

    @Expose
    @SerializedName("promotion_type")
    private String promotion_type;

    @Expose
    @SerializedName("icon")
    private String icon;

    public String getFoto() {
        return photo;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }

    public int getFiturpromosi() {
        return promotion_service;
    }

    public void setFiturpromosi(int promotion_service) {
        this.promotion_service = promotion_service;
    }

    public String getLinkpromosi() {
        return promotion_link;
    }

    public void setLink_promosi(String promotion_link) {
        this.promotion_link = promotion_link;
    }

    public String getTypepromosi() {
        return promotion_type;
    }

    public void setTypepromosi(String promotion_type) {
        this.promotion_type = promotion_type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
