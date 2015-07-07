package com.example.romain.home.model;

/**
 * Created by romain on 15/07/15.
 */
public enum Requests {

    SUMMARY("summary"),
    LIST_ACTIONS("list_actions"),
    SEND_ACTION("send_action"),
    DATA("data");

    private String key;

    Requests(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
