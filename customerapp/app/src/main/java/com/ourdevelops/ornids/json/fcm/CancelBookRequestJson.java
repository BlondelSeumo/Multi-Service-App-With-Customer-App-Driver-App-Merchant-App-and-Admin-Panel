package com.ourdevelops.ornids.json.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ourdevelops Team on 11/28/2019.
 */

public class CancelBookRequestJson {
    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("driver_id")
    public String driver_id;



}
