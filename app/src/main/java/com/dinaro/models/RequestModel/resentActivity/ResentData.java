package com.dinaro.models.RequestModel.resentActivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResentData implements Serializable {

    @SerializedName("card_title")
    @Expose
    private String cardTitle;
    @SerializedName("card_image")
    @Expose
    private String cardImage;
    @SerializedName("consumer_name")
    @Expose
    private String consumerName;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("date")
    @Expose
    private String createdAt;
    private String currentMonth;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    private String mDate;

    public String getmDay() {
        return mDay;
    }

    public void setmDay(String mDay) {
        this.mDay = mDay;
    }
    private String mMonth;
    public String getmMonth() {
        return mMonth;
    }
    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    private String mDay;
    @SerializedName("bill_number")
    @Expose
    private String BillNumber;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("account_type")
    @Expose
    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    private boolean isHeader;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillNumber() {
        return BillNumber;
    }

    public void setBillNumber(String billNumber) {
        BillNumber = billNumber;
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

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }
}
