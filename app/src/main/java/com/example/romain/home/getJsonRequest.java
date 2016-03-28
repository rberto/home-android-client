package com.example.romain.home;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;

/**
 * Created by romain on 3/27/16.
 */
public class getJsonRequest extends getRequest{

    public getJsonRequest(RequestReciever frag, Context context) {
        super(frag, context);
    }

    @Override
    protected void onPostExecute(OutputStream result) {
        super.onPostExecute(result);
        if (result != null){
            Log.i("HOME", result.toString());
            JSONObject mainObject = null;
            try {
                mainObject = new JSONObject(result.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.fragmentWeakRef.get() != null) {
                this.fragmentWeakRef.get().onResponce(mainObject, request);
            }
        }
    }


}
