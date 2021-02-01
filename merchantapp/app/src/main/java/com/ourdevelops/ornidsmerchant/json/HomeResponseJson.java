package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornidsmerchant.models.PayuModel;
import com.ourdevelops.ornidsmerchant.models.TransMerchantModel;
import com.ourdevelops.ornidsmerchant.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class HomeResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("balance")
    @Expose
    private String balance;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("stripe_publish")
    @Expose
    private String stripePublished;

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

    @SerializedName("paypal_key")
    @Expose
    private String paypalkey;

    @SerializedName("paypal_mode")
    @Expose
    private String paypalmode;

    @SerializedName("paypal_active")
    @Expose
    private String paypalactive;

    @SerializedName("data")
    @Expose
    private List<TransMerchantModel> data = new ArrayList<>();

    @SerializedName("user")
    @Expose
    private List<User> user = new ArrayList<>();

    @SerializedName("payu")
    @Expose
    private List<PayuModel> payu = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransMerchantModel> getData() {
        return data;
    }

    public void setData(List<TransMerchantModel> data) {
        this.data = data;
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

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<PayuModel> getPayu() {
        return payu;
    }

    public void setPayu(List<PayuModel> payu) {
        this.payu = payu;
    }

    public String getStripePublished() {
        return stripePublished;
    }

    public void setStripePublished(String stripePublished) {
        this.stripePublished = stripePublished;
    }
}
