package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.AllServiceModel;
import com.ourdevelops.ornids.models.NewsModel;
import com.ourdevelops.ornids.models.CatMerchantModel;
import com.ourdevelops.ornids.models.ServiceModel;
import com.ourdevelops.ornids.models.MerchantModel;
import com.ourdevelops.ornids.models.MerchantNearModel;
import com.ourdevelops.ornids.models.PayuModel;
import com.ourdevelops.ornids.models.PromoModel;
import com.ourdevelops.ornids.models.RatingModel;
import com.ourdevelops.ornids.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class GetHomeResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("balance")
    @Expose
    private String balance;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("currency_text")
    @Expose
    private String currency_text;

    @SerializedName("app_aboutus")
    @Expose
    private String aboutus;

    @SerializedName("app_email")
    @Expose
    private String email;

    @SerializedName("app_contact")
    @Expose
    private String phone;

    @SerializedName("app_website")
    @Expose
    private String website;

    @SerializedName("stripe_active")
    @Expose
    private String stripeactive;

    @SerializedName("stripe_publish")
    @Expose
    private String stripePublish;

    @SerializedName("paypal_key")
    @Expose
    private String paypalkey;

    @SerializedName("paypal_mode")
    @Expose
    private String paypalmode;

    @SerializedName("paypal_active")
    @Expose
    private String paypalactive;

    @SerializedName("service")
    @Expose
    private List<ServiceModel> service = new ArrayList<>();

    @SerializedName("allfitur")
    @Expose
    private List<AllServiceModel> allfitur = new ArrayList<>();

    @SerializedName("ratinghome")
    @Expose
    private List<RatingModel> rating = new ArrayList<>();

    @SerializedName("beritahome")
    @Expose
    private List<NewsModel> berita = new ArrayList<>();

    @SerializedName("slider")
    @Expose
    private List<PromoModel> slider = new ArrayList<>();

    @SerializedName("data")
    @Expose
    private List<User> data = new ArrayList<>();

    @SerializedName("merchantpromo")
    @Expose
    private List<MerchantModel> merchantpromo = new ArrayList<>();

    @SerializedName("merchantnearby")
    @Expose
    private List<MerchantNearModel> merchantnear = new ArrayList<>();

    @SerializedName("kategorymerchanthome")
    @Expose
    private List<CatMerchantModel> catmerchant = new ArrayList<>();

    @SerializedName("payu")
    @Expose
    private List<PayuModel> payu = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaldo() {
        return balance;
    }

    public void setSaldo(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencytext() {
        return currency_text;
    }

    public void setCurrencytext(String currencytext) {
        this.currency_text = currencytext;
    }

    public List<ServiceModel> getFitur() {
        return service;
    }

    public void setFitur(List<ServiceModel> service) {
        this.service = service;
    }

    public List<PromoModel> getSlider() {
        return slider;
    }

    public void setSlider(List<PromoModel> slider) {
        this.slider = slider;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<RatingModel> getRating() {
        return rating;
    }

    public void setRating(List<RatingModel> rating) {
        this.rating = rating;
    }

    public List<NewsModel> getBerita() {
        return berita;
    }

    public void setBerita(List<NewsModel> berita) {
        this.berita = berita;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public String getStripeactive() {
        return stripeactive;
    }

    public void setStripeactive(String stripeactive) {
        this.stripeactive = stripeactive;
    }

    public String getPaypalkey() {
        return paypalkey;
    }

    public void setPaypalkey(String paypalkey) {
        this.paypalkey = paypalkey;
    }

    public String getPaypalmode() {
        return paypalmode;
    }

    public void setPaypalmode(String paypalmode) {
        this.paypalmode = paypalmode;
    }

    public String getPaypalactive() {
        return paypalactive;
    }

    public void setPaypalactive(String paypalactive) {
        this.paypalactive = paypalactive;
    }

    public List<MerchantModel> getMerchantpromo() {
        return merchantpromo;
    }

    public void setMerchantpromo(List<MerchantModel> merchantpromo) {
        this.merchantpromo = merchantpromo;
    }

    public List<CatMerchantModel> getCatmerchant() {
        return catmerchant;
    }

    public void setCatmerchant(List<CatMerchantModel> catmerchant) {
        this.catmerchant = catmerchant;
    }

    public List<MerchantNearModel> getMerchantnear() {
        return merchantnear;
    }

    public void setMerchantnear(List<MerchantNearModel> merchantnear) {
        this.merchantnear = merchantnear;
    }

    public List<AllServiceModel> getAllfitur() {
        return allfitur;
    }

    public void setAllfitur(List<AllServiceModel> allfitur) {
        this.allfitur = allfitur;
    }

    public List<PayuModel> getPayu() {
        return payu;
    }

    public void setPayu(List<PayuModel> payu) {
        this.payu = payu;
    }

    public String getStripePublish() {
        return stripePublish;
    }

    public void setStripePublish(String stripePublish) {
        this.stripePublish = stripePublish;
    }
}
