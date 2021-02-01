package com.ourdevelops.ornidsmerchant.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class ServiceModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("service_id")
    private int idFitur;

    @Expose
    @SerializedName("service")
    private String service;

    public int getIdFitur() {
        return idFitur;
    }

    public void setIdFitur(int idFitur) {
        this.idFitur = idFitur;
    }

    public String getFitur() {
        return service;
    }

    public void setFitur(String service) {
        this.service = service;
    }


}
