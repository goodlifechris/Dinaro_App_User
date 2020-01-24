package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordRequest {
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
