package com.ourdevelops.ornids.json.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ourdevelops Team on 10/19/2019.
 */

public class DriverMSendRequest implements Serializable {

    @Expose
    @SerializedName("type")
    private int type;

    @Expose
    @SerializedName("transaction_id")
    private String idTransaksi;

    @Expose
    @SerializedName("customer_id")
    private String idPelanggan;

    @Expose
    @SerializedName("reg_id_pelanggan")
    private String regIdPelanggan;

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
    @SerializedName("rate")
    private String rate;

    @Expose
    @SerializedName("promo_code")
    private String kodePromo;

    @Expose
    @SerializedName("promo_discount")
    private String kreditPromo;

    @Expose
    @SerializedName("pakai_mpay")
    private boolean pakaiMPay;

    @Expose
    @SerializedName("nama_pelanggan")
    private String namaPelanggan;

    @Expose
    @SerializedName("telepon")
    private String telepon;

    @Expose
    @SerializedName("time_accept")
    private String time_accept;

    @Expose
    @SerializedName("sender_name")
    public String namaPengirim;

    @Expose
    @SerializedName("sender_phone")
    public String teleponPengirim;

    @Expose
    @SerializedName("receiver_name")
    public String namaPenerima;

    @Expose
    @SerializedName("receiver_phone")
    public String teleponPenerima;

    @Expose
    @SerializedName("goods_item")
    public String namaBarang;



    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getRegIdPelanggan() {
        return regIdPelanggan;
    }

    public void setRegIdPelanggan(String regIdPelanggan) {
        this.regIdPelanggan = regIdPelanggan;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public boolean isPakaiMPay() {
        return pakaiMPay;
    }

    public void setPakaiMPay(boolean pakaiMPay) {
        this.pakaiMPay = pakaiMPay;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getTime_accept() {
        return time_accept;
    }

    public void setTime_accept(String time_accept) {
        this.time_accept = time_accept;
    }
}
