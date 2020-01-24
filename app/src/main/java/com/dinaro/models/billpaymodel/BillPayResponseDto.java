package com.dinaro.models.billpaymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillPayResponseDto {
    @SerializedName("result")
    @Expose
    private  BillPayDataResponseDto billPayDataResponseDto;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response_code")
    @Expose
    private  String responseCode;
    @SerializedName("status")
    @Expose
    private  int status;

    public BillPayDataResponseDto getBillPayDataResponseDto() {
        return billPayDataResponseDto;
    }

    public void setBillPayDataResponseDto(BillPayDataResponseDto billPayDataResponseDto) {
        this.billPayDataResponseDto = billPayDataResponseDto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
