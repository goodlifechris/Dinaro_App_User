package com.dinaro.models.RequestModel.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingData {

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
