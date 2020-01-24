package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class PinConformRequest {
    @SerializedName("user_id")
    String userId;
    @SerializedName("pin")
    String pin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
