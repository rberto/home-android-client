package com.example.romain.home.views.items;

/**
 * Created by romain on 14/08/15.
 */
public class ActionsItem {

    private String title;
    private String description;

    public ActionsItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

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
}
