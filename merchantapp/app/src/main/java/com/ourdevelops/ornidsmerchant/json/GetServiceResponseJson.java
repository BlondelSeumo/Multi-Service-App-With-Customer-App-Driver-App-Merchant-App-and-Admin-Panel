package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornidsmerchant.models.CategoryModel;
import com.ourdevelops.ornidsmerchant.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class GetServiceResponseJson {

    @Expose
    @SerializedName("service")
    private List<ServiceModel> service = new ArrayList<>();

    @Expose
    @SerializedName("category")
    private List<CategoryModel> category = new ArrayList<>();

    public List<ServiceModel> getData() {
        return service;
    }

    public void setData(List<ServiceModel> service) {
        this.service = service;
    }

    public List<CategoryModel> getKategori() {
        return category;
    }

    public void setKategori(List<CategoryModel> category) {
        this.category = category;
    }



}
