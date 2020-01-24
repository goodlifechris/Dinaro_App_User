package com.dinaro.service.apiRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCardRequest {


    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("card_holder_name")
    @Expose
    private String cardName;
    @SerializedName("expiry_date")
    @Expose
    private String date;
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("card_type")
    @Expose
    private String cardType;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardNam) {
        this.cardName = cardNam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
