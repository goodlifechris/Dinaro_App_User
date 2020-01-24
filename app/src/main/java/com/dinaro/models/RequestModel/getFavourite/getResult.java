package com.dinaro.models.RequestModel.getFavourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getResult {

    @SerializedName("data")
    @Expose
    private List<GetData> data = null;

    public List<GetData> getData() {
        return data;
    }

    public void setData(List<GetData> data) {
        this.data = data;
    }
}
