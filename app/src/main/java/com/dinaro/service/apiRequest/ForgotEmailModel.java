package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class ForgotEmailModel {

    @SerializedName("otp")
    private String otp;

    @SerializedName("email")
    private String email;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return
                "ForgotEmailModel{" +
                        "otp = '" + otp + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }
}