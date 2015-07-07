package com.example.romain.home;

import android.app.Activity;

import com.example.romain.home.model.Requests;

import org.json.JSONObject;

/**
 * Created by romain on 15/07/15.
 */
public interface RequestReciever {

    public void onResponce(JSONObject resonce, Requests request);

    public Activity getAct();
}
