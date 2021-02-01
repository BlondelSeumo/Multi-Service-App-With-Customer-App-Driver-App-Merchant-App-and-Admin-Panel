package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.ourdevelops.ornids.models.DiskonWalletModel;
import com.ourdevelops.ornids.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class GetServiceResponseJson {

    @Expose
    @SerializedName("data")
    private List<ServiceModel> data = new ArrayList<>();

    @Expose
    @SerializedName("diskon_wallet")
    private DiskonWalletModel diskonWallet;

    @Expose
    @SerializedName("currency")
    private String currencyModel;


    public List<ServiceModel> getData() {
        return data;
    }

    public void setData(List<ServiceModel> data) {
        this.data = data;
    }

    public DiskonWalletModel getDiskonWallet() {
        return diskonWallet;
    }

    public void setDiskonWallet(DiskonWalletModel diskonWallet) {
        this.diskonWallet = diskonWallet;
    }

    public String getCurrencyModel() {
        return currencyModel;
    }

    public void setCurrencyModel(String currencyModel) {
        this.currencyModel = currencyModel;
    }

}
