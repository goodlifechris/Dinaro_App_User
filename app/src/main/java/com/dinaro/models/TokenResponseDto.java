package com.dinaro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponseDto {
    @SerializedName("status")
    @Expose
    private  String status;
    @SerializedName("result")
    @Expose
    private  String token;
    @SerializedName("message")
    @Expose
    private  String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
