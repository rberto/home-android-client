package com.example.romain.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.romain.home.model.RequestSender;
import com.example.romain.home.model.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by romain on 30/11/14.
 */
public class getRequest extends AsyncTask<Requests, Integer, String> {

    private Context mContext;
    private WeakReference<RequestReciever> fragmentWeakRef;
    private Requests request;

    public getRequest(RequestReciever frag, Context context){
        this.fragmentWeakRef = new WeakReference<RequestReciever>(frag);
        this.mContext = context;
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) fragmentWeakRef.get().getAct().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public String getWifiName(Context context) {
        String ssid = "none";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED) {
            ssid = wifiInfo.getSSID();
        }
        return ssid;
    }

    @Override
    protected String doInBackground(Requests... r) {
        request = r[0];

        Log.i("wifi", getWifiName(mContext));

        WifiManager wifiManager = (WifiManager)mContext.getSystemService(mContext.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID", wifiInfo.getSSID());

        if (wifiInfo.getSSID().contains("Bbox-A7A999C0-5GHz")){
            RequestSender.getInstance().setAddress("192.168.1.33");
        }else{
            RequestSender.getInstance().setAddress("128.79.58.158");
        }
        RequestSender.getInstance().setContext(mContext);
        RequestSender.getInstance().setPassword("azerty");
        RequestSender.getInstance().setPort("8889");
        RequestSender.getInstance().setUser("romain");
        return RequestSender.getInstance().sendRequest(request);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null){
            Log.i("HOME", result);
            JSONObject mainObject = null;
            try {
                mainObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.fragmentWeakRef.get() != null) {
                this.fragmentWeakRef.get().onResponce(mainObject, request);
            }
        }
    }
}