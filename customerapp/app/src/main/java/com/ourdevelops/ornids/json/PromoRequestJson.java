package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 11/28/2019.
 */

public class PromoRequestJson {
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("service")
    @Expose
    private String service;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFitur() {
        return service;
    }

    public void setFitur(String service) {
        this.service = service;
    }
}
