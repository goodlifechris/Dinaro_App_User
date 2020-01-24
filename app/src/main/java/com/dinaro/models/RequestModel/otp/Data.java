package com.dinaro.models.RequestModel.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user")
    @Expose
    private UserotpData user;

    public UserotpData getUser() {
        return user;
    }

    public void setUser(UserotpData user) {
        this.user = user;
    }
}
