package com.example.romain.home.views.items;

/**
 * Created by romain on 07/07/15.
 */
public class SummaryItem {

    private String title;
    private String value;
    private String avgValue;
    private String unit;

    public SummaryItem(String title, String value, String avgValue, String unit) {
        this.title = title;
        this.value = value;
        this.avgValue = avgValue;
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(String avgValue) {
        this.avgValue = avgValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
