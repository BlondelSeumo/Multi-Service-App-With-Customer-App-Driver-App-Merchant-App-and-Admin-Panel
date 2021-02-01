package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornids.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public class NewsDetailResponseJson {

    @Expose
    @SerializedName("data")
    private List<NewsModel> data = new ArrayList<>();

    public List<NewsModel> getData() {
        return data;
    }

    public void setData(List<NewsModel> data) {
        this.data = data;
    }
}
