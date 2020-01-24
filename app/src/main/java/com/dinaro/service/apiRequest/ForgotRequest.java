package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class ForgotRequest {

    @SerializedName("user_id")
    private String userId;
    @SerializedName("phone_number")
    private String mobile;
    @SerializedName("country_code")
    private String countryCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
