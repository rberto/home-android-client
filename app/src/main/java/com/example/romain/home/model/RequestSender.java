package com.example.romain.home.model;

import android.content.Context;
import android.util.Log;

import com.example.romain.home.MyHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by romain on 15/07/15.
 */
public class RequestSender {

    public static final RequestSender instance = new RequestSender();

    private String password;
    private String user;
    private String address;
    private String port;
    private Context context;

    private RequestSender(){}

    public static RequestSender getInstance(){
        return instance;
    }

    private String buildUri(Requests request){
        String url = "https://" + address + ":" + port + "/api?user=" + user + "&password=" + password + "&datatype=" + request.getKey();
        if (request.getArgs() != null){
            for (String arg : request.getArgs())
            url = url + "&name=" + arg;
        }
        return url;
    }

    public String sendRequest(Requests request){
        HttpClient httpclient = new MyHttpClient(context);
        HttpResponse response;
        String responseString = null;
        try {
            String url = buildUri(request);
            assert url != null;
            Log.i("HOME", url);
            response = httpclient.execute(new HttpGet(url));
            Log.i("HOME", "executed");
            StatusLine statusLine = response.getStatusLine();
            Log.i("HOME", "statusline read");

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                Log.i("HOME", "statusline OK");

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                Log.i("HOME", "closed");

                responseString = out.toString();
            } else {
                //Closes the connection.
                Log.e("HOME", "statusline KO");

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.e("HOME", "ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("HOME", "IOException");
            e.printStackTrace();
        } catch (IllegalStateException e){
            Log.e("HOME", "No network connection!");
            e.printStackTrace();
        }
        return responseString;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
