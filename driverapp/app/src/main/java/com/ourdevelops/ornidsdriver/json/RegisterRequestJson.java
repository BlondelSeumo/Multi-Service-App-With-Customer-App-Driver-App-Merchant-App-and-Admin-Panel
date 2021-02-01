package com.ourdevelops.ornidsdriver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class RegisterRequestJson {

    @SerializedName("driver_name")
    @Expose
    private String namadriver;

    @SerializedName("user_nationid")
    @Expose
    private String noktp;

    @SerializedName("dob")
    @Expose
    private String tglLahir = "-";

    @SerializedName("phone_number")
    @Expose
    private String noTelepon;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("job")
    @Expose
    private String job;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("driver_address")
    @Expose
    private String alamat;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("vehicle_registration_number")
    @Expose
    private String nomorkendaraan;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("idcard_images")
    @Expose
    private String fotoktp;

    @SerializedName("driver_license_images")
    @Expose
    private String fotosim;

    @SerializedName("driver_license_id")
    @Expose
    private String idsim;

    @SerializedName("checked")
    @Expose
    private String checked;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;


    public String getNamadriver() {
        return namadriver;
    }

    public void setNamadriver(String namadriver) {
        this.namadriver = namadriver;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return photo;
    }

    public void setFoto(String photo) {
        this.photo = photo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getNomorkendaraan() {
        return nomorkendaraan;
    }

    public void setNomorkendaraan(String nomorkendaraan) {
        this.nomorkendaraan = nomorkendaraan;
    }

    public String getWarna() {
        return color;
    }

    public void setWarna(String color) {
        this.color = color;
    }

    public String getFotoktp() {
        return fotoktp;
    }

    public void setFotoktp(String fotoktp) {
        this.fotoktp = fotoktp;
    }

    public String getFotosim() {
        return fotosim;
    }

    public void setFotosim(String fotosim) {
        this.fotosim = fotosim;
    }

    public String getIdsim() {
        return idsim;
    }

    public void setIdsim(String idsim) {
        this.idsim = idsim;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }


}
