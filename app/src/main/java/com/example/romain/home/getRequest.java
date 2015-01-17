package com.example.romain.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by romain on 30/11/14.
 */
public class getRequest extends AsyncTask<String, Integer, String> {

    private Context mContext;
    private WeakReference<MyFragment> fragmentWeakRef;

    public getRequest(MyFragment frag){
        this.fragmentWeakRef = new WeakReference<MyFragment>(frag);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) fragmentWeakRef.get().getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            if (!isOnline()){
                throw new IllegalStateException();
            }
            response = httpclient.execute(new HttpGet(uri[0]));
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null){
            Log.i("HOME", result);
            JSONObject mainObject = null;
            try {
                mainObject = new JSONObject(result);
                //String uniName = mainObject.getString("temp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.fragmentWeakRef.get() != null) {
                try {
                    this.fragmentWeakRef.get().updateUI(mainObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            this.fragmentWeakRef.get().error();
        }
    }


        /*String p = null;

        try {
            // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(mContext.getAssets().open("server.crt"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore trustedStore = KeyStore.getInstance(keyStoreType);
            trustedStore.load(null, null);
            trustedStore.setCertificateEntry("ca", ca);

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream fis = mContext.getAssets().open("client.p12");
            keyStore.load(fis, "".toCharArray());


            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, "".toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(trustedStore);


// Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLSv1");
            context.init(keyManagers, tmf.getTrustManagers(), null);

// Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("https://192.168.1.92:8899/?user=romain&password=azerty");
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            InputStream in = urlConnection.getInputStream();
            p = in.toString();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(p);
        return p;*/
}