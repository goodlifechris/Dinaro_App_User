package com.dinaro.models.RequestModel.recipt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReciptResult {

    public List<ReciptData> getData() {
        return data;
    }

    public void setData(List<ReciptData> data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private List<ReciptData> data = null;
}
