package com.example.romain.home.views.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by romain on 14/08/15.
 */
public class ActionsItem implements Parcelable{

    private String title;
    private String description;

    public ActionsItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    protected ActionsItem(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<ActionsItem> CREATOR = new Creator<ActionsItem>() {
        @Override
        public ActionsItem createFromParcel(Parcel in) {
            return new ActionsItem(in);
        }

        @Override
        public ActionsItem[] newArray(int size) {
            return new ActionsItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.description);
    }
}
