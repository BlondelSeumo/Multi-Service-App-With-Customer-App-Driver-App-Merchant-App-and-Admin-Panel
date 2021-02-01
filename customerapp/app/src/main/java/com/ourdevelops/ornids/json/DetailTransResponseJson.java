package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.DriverModel;
import com.ourdevelops.ornids.models.ItemOrderModel;
import com.ourdevelops.ornids.models.TransModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class DetailTransResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<TransModel> data = new ArrayList<>();

    @Expose
    @SerializedName("driver")
    private List<DriverModel> driver = new ArrayList<>();

    @Expose
    @SerializedName("item")
    private List<ItemOrderModel> item = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransModel> getData() {
        return data;
    }

    public void setData(List<TransModel> data) {
        this.data = data;
    }

    public List<DriverModel> getDriver() {
        return driver;
    }

    public void setDriver(List<DriverModel> driver) {
        this.driver = driver;
    }

    public List<ItemOrderModel> getItem() {
        return item;
    }

    public void setItem(List<ItemOrderModel> item) {
        this.item = item;
    }
}
