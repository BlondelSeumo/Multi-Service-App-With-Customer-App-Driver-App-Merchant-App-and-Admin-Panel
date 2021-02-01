package com.ourdevelops.ornidsmerchant.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ourdevelops.ornidsmerchant.models.CategoryItemModel;
import com.ourdevelops.ornidsmerchant.models.CategoryItemNonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class CategoryResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("dataactive")
    @Expose
    private List<CategoryItemModel> data = new ArrayList<>();

    @SerializedName("datanonactive")
    @Expose
    private List<CategoryItemNonModel> datanon = new ArrayList<>();

    @SerializedName("totalitemactive")
    @Expose
    private String totalitemactive;

    @SerializedName("totalitemnonactive")
    @Expose
    private String totalitemnonactive;

    @SerializedName("totalitempromo")
    @Expose
    private String totalitempromo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryItemModel> getData() {
        return data;
    }

    public void setData(List<CategoryItemModel> data) {
        this.data = data;
    }

    public List<CategoryItemNonModel> getDatanon() {
        return datanon;
    }

    public void setDatanon(List<CategoryItemNonModel> datanon) {
        this.datanon = datanon;
    }

    public String getTotalitemactive() {
        return totalitemactive;
    }

    public void setTotalitemactive(String totalitemactive) {
        this.totalitemactive = totalitemactive;
    }

    public String getTotalitemnonactive() {
        return totalitemnonactive;
    }

    public void setTotalitemnonactive(String totalitemnonactive) {
        this.totalitemnonactive = totalitemnonactive;
    }

    public String getTotalitempromo() {
        return totalitempromo;
    }

    public void setTotalitempromo(String totalitempromo) {
        this.totalitempromo = totalitempromo;
    }
}
