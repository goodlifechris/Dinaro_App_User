package com.dinaro.models.RequestModel.getProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class profileBase {

    @SerializedName("result")
    @Expose
    private profileResult result;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public profileResult getResult() {
        return result;
    }

    public void setResult(profileResult result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
