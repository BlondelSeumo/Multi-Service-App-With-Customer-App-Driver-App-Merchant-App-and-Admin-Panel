package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class AddEditItemRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String notelepon;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("idmerchant")
    @Expose
    private String idmerchant;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("promo_price")
    @Expose
    private String hargapromo;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("foto_lama")
    @Expose
    private String fotolama;

    @SerializedName("promo_status")
    @Expose
    private String status;


    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdmerchant() {
        return idmerchant;
    }

    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHarga() {
        return price;
    }

    public void setHarga(String price) {
        this.price = price;
    }

    public String getHargapromo() {
        return hargapromo;
    }

    public void setHargapromo(String hargapromo) {
        this.hargapromo = hargapromo;
    }

    public String getKategori() {
        return category;
    }

    public void setKategori(String category) {
        this.category = category;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return photo;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }

    public String getFotolama() {
        return fotolama;
    }

    public void setFotolama(String fotolama) {
        this.fotolama = fotolama;
    }
}
