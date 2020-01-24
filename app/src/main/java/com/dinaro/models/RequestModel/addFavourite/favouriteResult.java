package com.dinaro.models.RequestModel.addFavourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class favouriteResult {

    @SerializedName("data")
    @Expose
    private favouriteData data;

    public favouriteData getData() {
        return data;
    }

    public void setData(favouriteData data) {
        this.data = data;
    }
}
