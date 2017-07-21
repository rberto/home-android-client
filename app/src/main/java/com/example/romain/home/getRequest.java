package com.example.romain.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.romain.home.model.RequestSender;
import com.example.romain.home.model.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.lang.ref.WeakReference;

/**
 * Created by romain on 30/11/14.
 */
public abstract class getRequest extends AsyncTask<Requests, Integer, OutputStream> {

    private Context mContext;
    protected WeakReference<RequestReciever> fragmentWeakRef;
    protected Requests request;

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
    protected OutputStream doInBackground(Requests... r) {
        request = r[0];

        Log.i("wifi", getWifiName(mContext));

        WifiManager wifiManager = (WifiManager)mContext.getSystemService(mContext.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID", wifiInfo.getSSID());

        String ssid = wifiInfo.getSSID();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (ssid.contains("TP-LINK_A501") || wifiInfo.getSSID().contains("TP-LINK_A500_5G")){

            String syncConnPref = sharedPref.getString("server_ip_add", "");
            RequestSender.getInstance().setAddress(syncConnPref);
        }else{
            String syncConnPref = sharedPref.getString("server_remote_ip", "");
            RequestSender.getInstance().setAddress(syncConnPref);
        }
        RequestSender.getInstance().setContext(mContext);
        RequestSender.getInstance().setPassword(sharedPref.getString("password", ""));
        RequestSender.getInstance().setPort("8889");
        RequestSender.getInstance().setUser(sharedPref.getString("username", ""));
        return RequestSender.getInstance().sendRequest(request);
    }

}