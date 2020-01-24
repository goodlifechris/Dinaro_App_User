package com.dinaro.models.RequestModel.cardList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cardListBase {

    @SerializedName("result")
    @Expose
    private cardListResult result;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public cardListResult getResult() {
        return result;
    }

    public void setResult(cardListResult result) {
        this.result = result;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
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

}
