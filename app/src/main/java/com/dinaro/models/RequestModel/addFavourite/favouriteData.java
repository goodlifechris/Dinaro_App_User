package com.dinaro.models.RequestModel.addFavourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class favouriteData {


    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pay_bill_id")
    @Expose
    private String payBillId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayBillId() {
        return payBillId;
    }

    public void setPayBillId(String payBillId) {
        this.payBillId = payBillId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
