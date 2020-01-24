package com.dinaro.models.billpaymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class BillPayDataResponseDto {

    @SerializedName("data")
    private Map<String, ArrayList<CommonDataModel>> arrayListMap;

    public Map<String, ArrayList<CommonDataModel>> getArrayListMap() {
        return arrayListMap;
    }

    public void setArrayListMap(Map<String, ArrayList<CommonDataModel>> arrayListMap) {
        this.arrayListMap = arrayListMap;
    }

}
