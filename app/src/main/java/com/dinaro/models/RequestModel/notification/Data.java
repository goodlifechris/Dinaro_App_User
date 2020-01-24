package com.dinaro.models.RequestModel.notification;


import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("sendSuccessStatus")
    private String sendSuccessStatus;

    @SerializedName("sendFailStatus")
    private String sendFailStatus;

    public String getSendSuccessStatus() {
        return sendSuccessStatus;
    }

    public void setSendSuccessStatus(String sendSuccessStatus) {
        this.sendSuccessStatus = sendSuccessStatus;
    }

    public String getSendFailStatus() {
        return sendFailStatus;
    }

    public void setSendFailStatus(String sendFailStatus) {
        this.sendFailStatus = sendFailStatus;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "sendSuccessStatus = '" + sendSuccessStatus + '\'' +
                        ",sendFailStatus = '" + sendFailStatus + '\'' +
                        "}";
    }
}