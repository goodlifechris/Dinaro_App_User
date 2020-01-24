package com.dinaro.models.RequestModel.recipt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReciptData {


    @SerializedName("card_title")
    @Expose
    private String cardTitle;
    @SerializedName("card_image")
    @Expose
    private String cardImage;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("card_id")
    @Expose
private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
