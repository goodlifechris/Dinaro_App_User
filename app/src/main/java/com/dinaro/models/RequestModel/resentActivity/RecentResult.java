package com.dinaro.models.RequestModel.resentActivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentResult {
    @SerializedName("data")
    @Expose
    private List<ResentData> data = null;

    public List<ResentData> getData() {
        return data;
    }

    public void setData(List<ResentData> data) {
        this.data = data;
    }
}
