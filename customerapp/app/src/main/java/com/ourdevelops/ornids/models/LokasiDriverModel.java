package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 24/02/2019.
 */

public class LokasiDriverModel {

    @SerializedName("driver_id")
    @Expose
    private String idDriver;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    public String getIdDriver() {
        return idDriver;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
