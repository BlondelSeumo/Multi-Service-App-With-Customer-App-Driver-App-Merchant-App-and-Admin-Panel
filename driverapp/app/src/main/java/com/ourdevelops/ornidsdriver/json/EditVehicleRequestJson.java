package com.ourdevelops.ornidsdriver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class EditVehicleRequestJson {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phone_number")
    @Expose
    private String noTelepon;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("no_kendaraan")
    @Expose
    private String no_kendaraan;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("id_kendaraan")
    @Expose
    private String id_kendaraan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getMerek() {
        return brand;
    }

    public void setMerek(String brand) {
        this.brand = brand;
    }

    public String getTipe() {
        return type;
    }

    public void setTipe(String type) {
        this.type = type;
    }

    public String getNo_kendaraan() {
        return no_kendaraan;
    }

    public void setNo_kendaraan(String no_kendaraan) {
        this.no_kendaraan = no_kendaraan;
    }

    public String getWarna() {
        return color;
    }

    public void setWarna(String color) {
        this.color = color;
    }

    public String getId_kendaraan() {
        return id_kendaraan;
    }

    public void setId_kendaraan(String id_kendaraan) {
        this.id_kendaraan = id_kendaraan;
    }


}
