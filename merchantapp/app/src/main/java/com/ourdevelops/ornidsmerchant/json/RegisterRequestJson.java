package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class RegisterRequestJson {

    @SerializedName("partner_name")
    @Expose
    private String partner_name;

    @SerializedName("jenis_identitas")
    @Expose
    private String jenis_identitas;

    @SerializedName("user_nationid")
    @Expose
    private String user_nationid;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("partner_address")
    @Expose
    private String partner_address;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("service_id")
    @Expose
    private String service_id;

    @SerializedName("merchant_name")
    @Expose
    private String merchant_name;

    @SerializedName("merchant_address")
    @Expose
    private String merchant_address;

    @SerializedName("merchant_latitude")
    @Expose
    private String merchant_latitude;

    @SerializedName("merchant_longitude")
    @Expose
    private String merchant_longitude;

    @SerializedName("open_hour")
    @Expose
    private String open_hour;

    @SerializedName("close_hour")
    @Expose
    private String close_hour;

    @SerializedName("merchant_category")
    @Expose
    private String merchant_category;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("idcard_images")
    @Expose
    private String idcard_images;

    @SerializedName("checked")
    @Expose
    private String checked;

    public void setNama_mitra(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getNama_mitra() {
        return partner_name;
    }

    public void setJenis_identitas(String jenis_identitas) {
        this.jenis_identitas = jenis_identitas;
    }

    public String getJenis_identitas() {
        return jenis_identitas;
    }

    public void setNo_ktp(String user_nationid) {
        this.user_nationid = user_nationid;
    }

    public String getNo_ktp() {
        return user_nationid;
    }

    public void setNo_telepon(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNo_telepon() {
        return phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAlamat_mitra(String partner_address) {
        this.partner_address = partner_address;
    }

    public String getAlamat_mitra() {
        return partner_address;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setId_fitur(String service_id) {
        this.service_id = service_id;
    }

    public String getId_fitur() {
        return service_id;
    }

    public void setNama_merchant(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getNama_merchant() {
        return merchant_name;
    }

    public void setAlamat_merchant(String merchant_address) {
        this.merchant_address = merchant_address;
    }

    public String getAlamat_merchant() {
        return merchant_address;
    }

    public void setLatitude_merchant(String merchant_latitude) {
        this.merchant_latitude = merchant_latitude;
    }

    public String getLatitude_merchant() {
        return merchant_latitude;
    }

    public void setLongitude_merchant(String merchant_longitude) {
        this.merchant_longitude = merchant_longitude;
    }

    public String getLongitude_merchant() {
        return merchant_longitude;
    }

    public void setJam_buka(String open_hour) {
        this.open_hour = open_hour;
    }

    public String getJam_buka() {
        return open_hour;
    }

    public void setJam_tutup(String close_hour) {
        this.close_hour = close_hour;
    }

    public String getJam_tutup() {
        return close_hour;
    }

    public void setCategory_merchant(String merchant_category) {
        this.merchant_category = merchant_category;
    }

    public String getCategory_merchant() {
        return merchant_category;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }

    public String getFoto() {
        return photo;
    }

    public void setFoto_ktp(String idcard_images) {
        this.idcard_images = idcard_images;
    }

    public String getFoto_ktp() {
        return idcard_images;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getChecked() {
        return checked;
    }
}
