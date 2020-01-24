package com.dinaro.service.apiRequest;

import com.google.gson.annotations.SerializedName;

public class PinRequest {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("country_code")
    private String country_code;
    @SerializedName("otp")
    private String otp;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
