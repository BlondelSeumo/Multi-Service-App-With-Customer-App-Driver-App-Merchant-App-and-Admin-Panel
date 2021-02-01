package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.LokasiDriverModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 24/02/2019.
 */

public class LocationDriverResponse {

    @SerializedName("data")
    @Expose
    private List<LokasiDriverModel> data = new ArrayList<>();

    public List<LokasiDriverModel> getData() {
        return data;
    }

}
