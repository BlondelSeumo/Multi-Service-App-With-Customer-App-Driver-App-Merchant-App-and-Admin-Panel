package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.CatMerchantModel;
import com.ourdevelops.ornids.models.MerchantNearModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class AllMerchantByNearResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("allmerchantnearby")
    @Expose
    private List<MerchantNearModel> data = new ArrayList<>();

    @SerializedName("kategorymerchant")
    @Expose
    private List<CatMerchantModel> category = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MerchantNearModel> getData() {
        return data;
    }

    public void setData(List<MerchantNearModel> data) {
        this.data = data;
    }

    public List<CatMerchantModel> getKategori() {
        return category;
    }

    public void setKategori(List<CatMerchantModel> category) {
        this.category = category;
    }
}
