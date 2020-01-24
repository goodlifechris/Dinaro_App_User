package com.dinaro.models.RequestModel.addCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class addCardResult {

    @SerializedName("data")
    @Expose
    private addCardData data;

    public addCardData getData() {
        return data;
    }

    public void setData(addCardData data) {
        this.data = data;
    }
}
