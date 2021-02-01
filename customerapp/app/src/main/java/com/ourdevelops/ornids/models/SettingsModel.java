package com.ourdevelops.ornids.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 12/01/2019.
 */

public class SettingsModel implements Serializable {
    @Expose
    @SerializedName("app_privacy_policy")
    public String privacy;

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }


}
