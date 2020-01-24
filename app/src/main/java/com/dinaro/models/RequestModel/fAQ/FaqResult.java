package com.dinaro.models.RequestModel.fAQ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqResult {

    @SerializedName("data")
    @Expose
    private List<FaqData> data = null;

    public List<FaqData> getData() {
        return data;
    }

    public void setData(List<FaqData> data) {
        this.data = data;
    }

}
