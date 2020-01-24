package com.dinaro.models.RequestModel.updateProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class updateResult  {

    @SerializedName("data")
    @Expose
    private updateData data;

    public updateData getData() {
        return data;
    }

    public void setData(updateData data) {
        this.data = data;
    }

}
