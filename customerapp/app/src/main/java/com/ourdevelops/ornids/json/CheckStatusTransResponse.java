package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.DriverModel;
import com.ourdevelops.ornids.models.StatusTransModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 11/02/2019.
 */

public class CheckStatusTransResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<StatusTransModel> data = new ArrayList<>();
    @SerializedName("list_driver")
    @Expose
    private List<DriverModel> listDriver = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StatusTransModel> getData() {
        return data;
    }

    public void setData(List<StatusTransModel> data) {
        this.data = data;
    }

    public List<DriverModel> getListDriver() {
        return listDriver;
    }

    public void setListDriver(List<DriverModel> listDriver) {
        this.listDriver = listDriver;
    }
}
