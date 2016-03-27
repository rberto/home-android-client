package com.example.romain.home.views.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by romain on 07/07/15.
 */
public class SummaryItem implements Parcelable {

    private String key;
    private String title;
    private String value;
    private String avgValue;
    private String unit;

    public SummaryItem(String key, String title, String value, String avgValue, String unit) {
        this.key = key;
        this.title = title;
        this.value = value;
        this.avgValue = avgValue;
        this.unit = unit;
    }

    protected SummaryItem(Parcel in) {
        key = in.readString();
        title = in.readString();
        value = in.readString();
        avgValue = in.readString();
        unit = in.readString();
    }

    public static final Creator<SummaryItem> CREATOR = new Creator<SummaryItem>() {
        @Override
        public SummaryItem createFromParcel(Parcel in) {
            return new SummaryItem(in);
        }

        @Override
        public SummaryItem[] newArray(int size) {
            return new SummaryItem[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.title);
        parcel.writeString(this.value);
        parcel.writeString(this.avgValue);
        parcel.writeString(this.unit);
    }
}
