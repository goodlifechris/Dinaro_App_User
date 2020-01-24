package com.dinaro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingupVerifyModel {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("otp")
    @Expose
    private  String otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
