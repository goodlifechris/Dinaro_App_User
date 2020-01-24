package com.dinaro.models.RequestModel.payBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Utility {
    @SerializedName("show_on_app")
    @Expose
    private String showOnApp;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("favstatus")
    @Expose
    private boolean isSelected;

    public String getShowOnApp() {
        return showOnApp;
    }

    public void setShowOnApp(String showOnApp) {
        this.showOnApp = showOnApp;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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


}
