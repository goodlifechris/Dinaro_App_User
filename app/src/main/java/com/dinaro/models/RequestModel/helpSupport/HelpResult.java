package com.dinaro.models.RequestModel.helpSupport;

import com.dinaro.service.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HelpResult extends BaseResponse {

    @SerializedName("data")
    @Expose
    private HelpData data;

    public HelpData getData() {
        return data;
    }

    public void setData(HelpData data) {
        this.data = data;
    }
}
