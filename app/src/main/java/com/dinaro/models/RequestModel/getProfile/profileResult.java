package com.dinaro.models.RequestModel.getProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class profileResult {

    @SerializedName("data")
    @Expose
    private profileData data;

    public profileData getData() {
        return data;
    }

    public void setData(profileData data) {
        this.data = data;
    }
}
