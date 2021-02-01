package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class RideCarRequestJson implements Serializable {

    @Expose
    @SerializedName("customer_id")
    private String idPelanggan;

    @Expose
    @SerializedName("service_order")
    private String orderFitur;

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
    @SerializedName("pickup_address")
    private String alamatAsal;

    @Expose
    @SerializedName("destination_address")
    private String alamatTujuan;

    @Expose
    @SerializedName("wallet_payment")
    private int pakaiWallet;

    @Expose
    @SerializedName("promo_discount")
    private String kreditpromo;

    @Expose
    @SerializedName("estimasi")
    private String estimasi;



    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
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

    public int isPakaiWallet() {
        return pakaiWallet;
    }

    public void setPakaiWallet(int pakaiMpay) {
        this.pakaiWallet = pakaiMpay;
    }

    public String getKreditpromo() {
        return kreditpromo;
    }

    public void setKreditpromo(String kreditpromo) {
        this.kreditpromo = kreditpromo;
    }

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

}
