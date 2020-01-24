package com.dinaro.models.RequestModel.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RatingBase {

    @SerializedName("result")
    @Expose
    private RatingResult result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("status")
    @Expose
    private Integer status;

    public RatingResult getResult() {
        return result;
    }

    public void setResult(RatingResult result) {
        this.result = result;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
