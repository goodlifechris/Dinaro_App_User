package com.dinaro.models.RequestModel.VerifyEmailOtp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("user")
    @Expose
    private user user;

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

}
