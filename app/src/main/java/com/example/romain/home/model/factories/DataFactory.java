package com.example.romain.home.model.factories;

import com.example.romain.home.model.SummaryKeys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by romain on 07/07/15.
 */
public class DataFactory {

    private static final String DEFAULTVALUE = "--";

    public static String getValue(JSONObject json, SummaryKeys key){
        if (json.has(key.getKey())){
            try {
                return json.getString(key.getKey());
            } catch (JSONException e) {
                return DEFAULTVALUE;
            }
        }else {
            return DEFAULTVALUE;
        }
    }





}
