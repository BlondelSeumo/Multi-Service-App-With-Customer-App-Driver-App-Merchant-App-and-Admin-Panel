package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.mapboxsdk.style.layers.PropertyValue;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class DriverModel extends RealmObject implements Serializable{

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("driver_name")
    private String namaDriver;

    @Expose
    @SerializedName("latitude")
    private double latitude;

    @Expose
    @SerializedName("longitude")
    private double longitude;

    @Expose
    @SerializedName("update_at")
    private Date updateAt;

    @Expose
    @SerializedName("phone_number")
    private String noTelepon;

    @Expose
    @SerializedName("photo")
    private String photo;

    @Expose
    @SerializedName("reg_id")
    private String regId;

    @Expose
    @SerializedName("driver_job")
    private String driverJob;

    @Expose
    @SerializedName("distance")
    private String distance;

    @Expose
    @SerializedName("brand")
    private String brand;

    @Expose
    @SerializedName("vehicle_registration_number")
    private String vehicle_registration_number;

    @Expose
    @SerializedName("color")
    private String color;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("bearing")
    private String bearing;



    public String getNamaDriver() {
        return namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getFoto() {
        return photo;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getDriverJob() {
        return driverJob;
    }

    public void setDriverJob(String driverJob) {
        this.driverJob = driverJob;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMerek() {
        return brand;
    }

    public void setMerek(String brand) {
        this.brand = brand;
    }

    public String getNomor_kendaraan() {
        return vehicle_registration_number;
    }

    public void setNomor_kendaraan(String vehicle_registration_number) {
        this.vehicle_registration_number = vehicle_registration_number;
    }

    public String getWarna() {
        return color;
    }

    public void setWarna(String color) {
        this.color = color;
    }

    public String getTipe() {
        return type;
    }

    public void setTipe(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setBearing(String bearing) {
        this.bearing = bearing;
    }


    public String getBearing() {
        return bearing;
    }
}
