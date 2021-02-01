package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class GetOnRequestJson {

    @SerializedName("idmerchant")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("status")
    @Expose
    private String turn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
