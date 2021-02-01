package com.ourdevelops.ornidsdriver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornidsdriver.models.AllTransModel;
import com.ourdevelops.ornidsdriver.models.CustomerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class AllTransResponseJson {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<AllTransModel> data = new ArrayList<>();

    @Expose
    @SerializedName("driver")
    private List<CustomerModel> driver = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AllTransModel> getData() {
        return data;
    }

    public void setData(List<AllTransModel> data) {
        this.data = data;
    }

    public List<CustomerModel> getDriver() {
        return driver;
    }

    public void setDriver(List<CustomerModel> driver) {
        this.driver = driver;
    }
}
