package com.dinaro.models.RequestModel.legal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class legalResult {
    @SerializedName("data")
    @Expose
    private List<legalData> data = null;

    public List<legalData> getData() {
        return data;
    }

    public void setData(List<legalData> data) {
        this.data = data;
    }
}
