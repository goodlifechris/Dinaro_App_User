package com.dinaro.models.RequestModel.payBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class payBillResult {

    @SerializedName("data")
    @Expose
    private  payBillData payBillData;

    public payBillData getPayBillData() {
        return payBillData;
    }

    public void setPayBillData(payBillData payBillData) {
        this.payBillData = payBillData;
    }
}
