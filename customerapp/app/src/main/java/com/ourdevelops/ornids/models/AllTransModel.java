package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class AllTransModel extends RealmObject implements Serializable{

    @PrimaryKey
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("customer_id")
    private String idPelanggan;

    @Expose
    @SerializedName("transaction_id")
    private String idTransaksi;

    @Expose
    @SerializedName("driver_id")
    private String idDriver;

    @Expose
    @SerializedName("service_order")
    private String orderFitur;

    @Expose
    @SerializedName("total_price")
    private String totalbiaya;

    @Expose
    @SerializedName("start_latitude")
    private double startLatitude;

    @Expose
    @SerializedName("start_longitude")
    private double startLongitude;

    @Expose
    @SerializedName("end_latitude")
    private double endLatitude;

    @Expose
    @SerializedName("end_longitude")
    private double endLongitude;

    @Expose
    @SerializedName("distance")
    private double distance;

    @Expose
    @SerializedName("price")
    private long price;

    @Expose
    @SerializedName("order_time")
    private Date waktuOrder;

    @Expose
    @SerializedName("finish_time")
    private Date waktuSelesai;

    @Expose
    @SerializedName("pickup_address")
    private String alamatAsal;

    @Expose
    @SerializedName("destination_address")
    private String alamatTujuan;

    @Expose
    @SerializedName("promo_code")
    private String kodePromo;

    @Expose
    @SerializedName("promo_discount")
    private String kreditPromo;

    @Expose
    @SerializedName("wallet_payment")
    private boolean pakaiWallet;

    @Expose
    @SerializedName("rate")
    private String rate;

    @Expose
    @SerializedName("status")
    public int status;

    @Expose
    @SerializedName("icon")
    public String icon;

    @Expose
    @SerializedName("service")
    public String service;

    @Expose
    @SerializedName("final_cost")
    public String biayaakhir;

    @Expose
    @SerializedName("transaction_status")
    public String statustransaksi;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.id = idTransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getOrderFitur() {
        return orderFitur;
    }

    public void setOrderFitur(String orderFitur) {
        this.orderFitur = orderFitur;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public double getJarak() {
        return distance;
    }

    public void setJarak(double distance) {
        this.distance = distance;
    }

    public long getHarga() {
        return price;
    }

    public void setHarga(long price) {
        this.price = price;
    }

    public Date getWaktuOrder() {
        return waktuOrder;
    }

    public void setWaktuOrder(Date waktuOrder) {
        this.waktuOrder = waktuOrder;
    }

    public Date getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(Date waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public void setAlamatAsal(String alamatAsal) {
        this.alamatAsal = alamatAsal;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public String getKreditPromo() {
        return kreditPromo;
    }

    public void setKreditPromo(String kreditPromo) {
        this.kreditPromo = kreditPromo;
    }

    public boolean isPakaiWallet() {
        return pakaiWallet;
    }

    public void setPakaiWallet(boolean pakaiWallet) {
        this.pakaiWallet = pakaiWallet;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFitur() {
        return service;
    }

    public void setFitur(String service) {
        this.service = service;
    }

    public String getBiayaakhir() {
        return biayaakhir;
    }

    public void setBiayaakhir(String biayaakhir) {
        this.biayaakhir = biayaakhir;
    }

    public String getStatustransaksi() {
        return statustransaksi;
    }

    public void setStatustransaksi(String statustransaksi) {
        this.statustransaksi = statustransaksi;
    }


}
