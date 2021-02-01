package com.ourdevelops.ornidsdriver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class JobModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("driver_job")
    private String job;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
