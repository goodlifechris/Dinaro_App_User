package com.dinaro.models.RequestModel.payBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class payBillData {

    @SerializedName("utility")
    @Expose
    private List<Utility> utility ;
    @SerializedName("restaurant")
    @Expose
    private List<Restaurant> restaurant ;

    public List<Utility> getUtility() {
        return utility;
    }

    public void setUtility(List<Utility> utility) {
        this.utility = utility;
    }

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }
}
