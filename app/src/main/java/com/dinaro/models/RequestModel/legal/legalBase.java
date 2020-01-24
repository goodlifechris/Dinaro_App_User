package com.dinaro.models.RequestModel.legal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class legalBase {
    @SerializedName("result")
    @Expose
    private legalResult result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public legalResult getResult() {
        return result;
    }

    public void setResult(legalResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}
