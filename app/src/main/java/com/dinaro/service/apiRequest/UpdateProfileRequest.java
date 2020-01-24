package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("name")
    private String fName;

    @SerializedName("middle_name")
    private String mName;

    @SerializedName("last_name")
    private String lName;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("email")
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
