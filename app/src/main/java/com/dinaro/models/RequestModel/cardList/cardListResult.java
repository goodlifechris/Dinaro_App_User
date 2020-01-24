package com.dinaro.models.RequestModel.cardList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class cardListResult {

    @SerializedName("data")
    @Expose
    private List<cardListData> data = null;

    public List<cardListData> getData() {
        return data;
    }

    public void setData(List<cardListData> data) {
        this.data = data;
    }

}
