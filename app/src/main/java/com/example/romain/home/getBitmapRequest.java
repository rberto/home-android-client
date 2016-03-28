package com.example.romain.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Created by romain on 3/27/16.
 */
public class getBitmapRequest extends getRequest{

    public getBitmapRequest(RequestReciever frag, Context context) {
        super(frag, context);
    }

    @Override
    protected void onPostExecute(OutputStream result) {
        ByteArrayOutputStream buf = (ByteArrayOutputStream) result;
        super.onPostExecute(result);
        if (result != null){
            Log.i("HOME", result.toString());
            Bitmap mIcon11 = null;
            mIcon11 = BitmapFactory.decodeStream(new ByteArrayInputStream(buf.toByteArray()));
            if (this.fragmentWeakRef.get() != null) {
                this.fragmentWeakRef.get().onResponce(mIcon11, request);
            }
        }
    }
}
