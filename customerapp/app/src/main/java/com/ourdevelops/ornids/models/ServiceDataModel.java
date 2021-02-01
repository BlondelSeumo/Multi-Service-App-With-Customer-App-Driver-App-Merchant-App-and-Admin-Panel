package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class ServiceDataModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("service_id")
    private int idFitur;

    @Expose
    @SerializedName("service")
    private String service;

    @Expose
    @SerializedName("cost")
    private long cost;

    @Expose
    @SerializedName("minimum_cost")
    private long minimum_cost;

    @Expose
    @SerializedName("cost_desc")
    private String keteranganBiaya;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("diskon")
    private String diskon;

    @Expose
    @SerializedName("final_cost")
    private double biayaAkhir;

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("driver_job")
    private String job;

    @Expose
    @SerializedName("home")
    private String home;



    public int getIdFitur() {
        return idFitur;
    }

    public void setIdFitur(int idFitur) {
        this.idFitur = idFitur;
    }

    public String getFitur() {
        return service;
    }

    public void setFitur(String service) {
        this.service = service;
    }

    public long getBiaya() {
        return cost;
    }

    public void setBiaya(long cost) {
        this.cost = cost;
    }

    public String getKeteranganBiaya() {
        return keteranganBiaya;
    }

    public void setKeteranganBiaya(String keteranganBiaya) {
        this.keteranganBiaya = keteranganBiaya;
    }

    public String getKeterangan() {
        return description;
    }

    public void setKeterangan(String description) {
        this.description = description;
    }

    public long getBiaya_minimum() {
        return minimum_cost;
    }

    public void setBiaya_minimum(long minimum_cost) {
        this.minimum_cost = minimum_cost;
    }

    public String getDiskon() { return diskon;    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public double getBiayaAkhir() {
        return biayaAkhir;
    }

    public void setBiayaAkhir(double biayaAkhir) {
        this.biayaAkhir = biayaAkhir;
    }

    public String getIcon() { return icon;    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHome() { return home;    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
