package com.dinaro.models.billpaymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonDataModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("show_on_app")
    @Expose
    private String show_on_app;
    @SerializedName("favstatus")
    @Expose
    private String favstatus;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShow_on_app() {
        return show_on_app;
    }

    public void setShow_on_app(String show_on_app) {
        this.show_on_app = show_on_app;
    }

    public String getFavstatus() {
        return favstatus;
    }

    public void setFavstatus(String favstatus) {
        this.favstatus = favstatus;
    }
}
