package com.dinaro.models;

import com.dinaro.R;

/**
 * Created by goodlife on 24,August,2019
 */
public class Transaction {

    String id;
    String type;
    String icon;
    double amount;
    String time;
    String name;
    String dateMonth;


    public Transaction(String id, String type, String icon, double amount, String time, String name,String dateMonth) {
        this.id = id;
        this.type = "Steers Ngong Rd";
        this.icon = String.valueOf(R.drawable.zuku);
        this.amount = amount;
        this.time = time;
        this.name = "payment";
        this.dateMonth=dateMonth;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
}
