package com.ourdevelops.ornidsmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 12/01/2019.
 */

public class TransMerchantModel implements Serializable {
    @Expose
    @SerializedName("merchant_transaction_id")
    public String idtransmerchant;

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("merchant_id")
    public String merchant_id;

    @Expose
    @SerializedName("total_price")
    public String total_price;

    @Expose
    @SerializedName("created")
    public String created;

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("quantity")
    public String quantity;

    @Expose
    @SerializedName("customer_fullname")
    public String customer_fullname;

    @Expose
    @SerializedName("customer_id")
    public String idpelanggan;

    @Expose
    @SerializedName("driver_id")
    public String iddriver;

    public String getIdtransmerchant() {
        return idtransmerchant;
    }

    public void setIdtransmerchant(String idtransmerchant) {
        this.idtransmerchant = idtransmerchant;
    }

    public String getId_transaksi() {
        return transaction_id;
    }

    public void setId_transaksi(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getId_merchant() {
        return merchant_id;
    }

    public void setId_merchant(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getTotal_biaya() {
        return total_price;
    }

    public void setTotal_biaya(String total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFullnama() {
        return customer_fullname;
    }

    public void setFullnama(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public void setIdpelanggan(String idpelanggan) {
        this.idpelanggan = idpelanggan;
    }

    public String getIddriver() {
        return iddriver;
    }

    public void setIddriver(String iddriver) {
        this.iddriver = iddriver;
    }


}
