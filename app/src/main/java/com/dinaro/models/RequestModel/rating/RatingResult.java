package com.dinaro.models.RequestModel.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingResult {
    @SerializedName("data")
    @Expose
    private RatingData data;

    public RatingData getData() {
        return data;
    }

    public void setData(RatingData data) {
        this.data = data;
    }
}
