package com.example.romain.home.model;

/**
 * Created by romain on 15/07/15.
 */
public enum Requests {

    SUMMARY("summary"),
    LIST_ACTIONS("list_actions"),
    SEND_ACTION("send_action"),
    DATA("data"),
    IMG("img");

    private String key;
    private String[] args;

    Requests(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
