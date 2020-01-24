package com.dinaro.models.RequestModel.loginPin;

import com.dinaro.service.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userpin extends BaseResponse {


    @SerializedName("result")
    @Expose
    private LoginResultpin result;

  /*  @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
*/
    public LoginResultpin getResult() {
        return result;
    }

    public void setResult(LoginResultpin result) {
        this.result = result;
    }
/*
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
    }*/


}
