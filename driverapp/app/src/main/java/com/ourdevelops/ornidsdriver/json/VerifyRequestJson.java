package com.ourdevelops.ornidsdriver.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class VerifyRequestJson {

    @SerializedName("phone_number")
    @Expose
    private String id;

    @SerializedName("verifycode")
    @Expose
    private String verifycode;

    @SerializedName("transaction_id")
    @Expose
    private String idtrans;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public String getIdtrans() {
        return idtrans;
    }

    public void setIdtrans(String idtrans) {
        this.idtrans = idtrans;
    }



}
