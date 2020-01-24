package com.dinaro.models;

/**
 * Created by goodlife on 28,August,2019
 */
public class PieTransaction {

    String type;
    Double total;
    String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public PieTransaction(String type, Double total, String icon) {
        this.type = type;
        this.total = total;
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PieTransaction{" +
                "type='" + type + '\'' +
                ", total=" + total +
                ", icon='" + icon + '\'' +
                '}';
    }
}
