package com.dinaro.models.RequestModel.pin;

import com.dinaro.service.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class ResultPin extends BaseResponse {
    @SerializedName("pin")
    private String pin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
