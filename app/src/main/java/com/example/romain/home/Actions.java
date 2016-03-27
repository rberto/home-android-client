package com.example.romain.home;

import android.drm.DrmStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by romain on 19/04/15.
 */
public class Actions {

    private String name;
    private String documentation;

    public Actions(String name, String documentation) {
        this.name = name;
        this.documentation = documentation;
    }

    public static List<Actions> create(JSONObject json) {
        List<Actions> actions = new ArrayList<Actions>();
        Iterator itr = json.keys();
        while(itr.hasNext()) {
            String key = (String) itr.next();
            try {
                actions.add(new Actions(key, json.getString(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return actions;
    }

    @Override
    public String toString(){
        return documentation;
    }
}
