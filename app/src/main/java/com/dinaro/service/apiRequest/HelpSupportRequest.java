package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class HelpSupportRequest {
    @SerializedName("user_id")
    private  String userid;
    @SerializedName("subject")
    private String subject;
    @SerializedName("description")
    private String description;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
