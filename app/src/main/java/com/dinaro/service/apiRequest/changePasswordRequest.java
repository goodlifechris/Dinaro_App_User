package com.dinaro.service.apiRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class changePasswordRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("newpassword")
    @Expose
    private String newpassword;
    @SerializedName("oldpassword")
    @Expose
    private String oldPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String cPassword) {
        this.oldPassword = cPassword;
    }
}
