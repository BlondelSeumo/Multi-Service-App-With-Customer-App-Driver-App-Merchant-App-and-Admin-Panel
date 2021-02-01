package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.WalletModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class WalletResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<WalletModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<WalletModel> getData() {
        return data;
    }

    public void setData(List<WalletModel> data) {
        this.data = data;
    }
}
