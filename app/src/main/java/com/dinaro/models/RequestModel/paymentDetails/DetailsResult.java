package com.dinaro.models.RequestModel.paymentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsResult {
    @SerializedName("data")
    @Expose
    private DetailsData data;

    public DetailsData getData() {
        return data;
    }

    public void setData(DetailsData data) {
        this.data = data;
    }

}
