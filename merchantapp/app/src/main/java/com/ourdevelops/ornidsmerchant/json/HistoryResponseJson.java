package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornidsmerchant.models.TransMerchantModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class HistoryResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<TransMerchantModel> data = new ArrayList<>();

    @SerializedName("daily")
    @Expose
    private String daily;

    @SerializedName("month")
    @Expose
    private String monthly;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("earning")
    @Expose
    private String earning;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransMerchantModel> getData() {
        return data;
    }

    public void setData(List<TransMerchantModel> data) {
        this.data = data;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

}
