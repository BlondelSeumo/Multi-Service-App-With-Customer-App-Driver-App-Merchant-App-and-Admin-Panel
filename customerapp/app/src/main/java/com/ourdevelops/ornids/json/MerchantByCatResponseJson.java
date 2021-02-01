package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.MerchantModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class MerchantByCatResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("merchantbykategori")
    @Expose
    private List<MerchantModel> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MerchantModel> getData() {
        return data;
    }

    public void setData(List<MerchantModel> data) {
        this.data = data;
    }
}
