package com.ourdevelops.ornids.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 11/28/2019.
 */

public class RateRequestJson {
    @Expose
    @SerializedName("driver_id")
    public String driver_id;

    @Expose
    @SerializedName("customer_id")
    public String customer_id;

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("rating")
    public String rating;

    @Expose
    @SerializedName("note")
    public String note;

}
