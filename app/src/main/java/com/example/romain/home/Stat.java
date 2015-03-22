package com.example.romain.home;

import android.renderscript.Sampler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by romain on 26/02/15.
 */
public class Stat {

    private String key;
    private String value;

    public Stat(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }


    public static List<Stat> create(JSONObject json){
        List<Stat> list = new ArrayList<Stat>();
        int size = json.length();
        Iterator itr = json.keys();
        while(itr.hasNext()) {
            String key = (String) itr.next();
            try {
                if (json.get(key) instanceof JSONObject){
                    List<Stat> l = Stat.create((JSONObject)json.get(key));
                    list.addAll(l);
                }else {
                    if (json.get(key) instanceof JSONArray){
                        JSONArray j = (JSONArray) json.get(key);
                        List<Stat> l = Stat.create(j);
                        list.addAll(l);
                    }else{
                        String value = json.get(key).toString();
                        if (value != null && !value.equals("")){
                            list.add(new Stat(key, value));
                        }
                        Log.d("HOME", "unread message");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static List<Stat> create(JSONArray j) {
        List<Stat> list = new ArrayList<Stat>();
        int size = j.length();
        for (int i=0; i< size; i++){
            try {
                if (j.get(i) instanceof JSONObject){
                    List<Stat> l = create((JSONObject)j.get(i));
                    list.addAll(l);
                }else{
                    if (j.get(i) instanceof JSONArray){
                        List<Stat> l = create((JSONArray)j.get(i));
                        list.addAll(l);
                    }else{
                        //list.add(new Stat(key, json.get(key).toString()));
                        Log.d("HOME", "unread message");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;

    }
}
