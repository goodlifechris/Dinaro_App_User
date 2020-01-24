package com.dinaro.models.RequestModel.loginPin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResultpin {

    @SerializedName("data")
    @Expose

    private Datapin data;

    public Datapin getData() {
        return data;
    }

    public void setData(Datapin data) {
        this.data = data;
    }


 /*   private Datapin data;

    public Datapin getData() {
        return data;
    }

    public void setData(Datapin data) {
        this.data = data;
    }*/
}
