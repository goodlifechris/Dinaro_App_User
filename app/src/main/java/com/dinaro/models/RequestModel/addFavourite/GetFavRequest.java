package com.dinaro.models.RequestModel.addFavourite;

import com.google.gson.annotations.SerializedName;

public class GetFavRequest {

    @SerializedName("pay_bill_id")
    private String payBillId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("type")
    private String type;

    public String getPayBillId() {
        return payBillId;
    }

    public void setPayBillId(String payBillId) {
        this.payBillId = payBillId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return
                "GetFavRequest{" +
                        "pay_bill_id = '" + payBillId + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",type = '" + type + '\'' +
                        "}";
    }
}