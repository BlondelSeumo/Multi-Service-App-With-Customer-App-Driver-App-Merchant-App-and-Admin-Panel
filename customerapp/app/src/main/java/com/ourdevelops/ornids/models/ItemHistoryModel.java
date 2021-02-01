package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.R;


import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 12/1/2019.
 */

public class ItemHistoryModel implements Serializable{
    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("driver_id")
    public String driver_id;

    @Expose
    @SerializedName("service_order")
    public String service_order;

    @Expose
    @SerializedName("start_latitude")
    public double start_latitude;

    @Expose
    @SerializedName("start_longitude")
    public double start_longitude;

    @Expose
    @SerializedName("end_latitude")
    public double end_latitude;

    @Expose
    @SerializedName("end_longitude")
    public double end_longitude;

    @Expose
    @SerializedName("order_time")
    public String order_time;

    @Expose
    @SerializedName("finish_time")
    public String finish_time;

    @Expose
    @SerializedName("pickup_address")
    public String pickup_address;

    @Expose
    @SerializedName("destination_address")
    public String destination_address;

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("nama_depan_driver")
    public String nama_depan_driver;

    @Expose
    @SerializedName("nama_belakang_driver")
    public String nama_belakang_driver;

    @Expose
    @SerializedName("phone_number")
    public String phone_number;

    @Expose
    @SerializedName("photo")
    public String photo;

    @Expose
    @SerializedName("rating")
    public String rating;

    @Expose
    @SerializedName("price")
    public long price;

    @Expose
    @SerializedName("distance")
    public double distance;

    @Expose
    @SerializedName("reg_id")
    public String reg_id;

    @Expose
    @SerializedName("brand")
    public String brand;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("jenis")
    public String jenis;

    @Expose
    @SerializedName("vehicle_registration_number")
    public String vehicle_registration_number;

    @Expose
    @SerializedName("color")
    public String color;

    @Expose
    @SerializedName("image_id")
    public int image_id = R.drawable.drivermap;


}
