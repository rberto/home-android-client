package com.example.romain.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public getRequest(RequestReciever frag){
        this.fragmentWeakRef = new WeakReference<RequestReciever>(frag);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) fragmentWeakRef.get().getAct().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    protected String doInBackground(Requests... r) {
        request = r[0];
        if (!isOnline()){
            throw new IllegalStateException();
        }
        RequestSender.getInstance().setAddress("192.168.1.92");
//        RequestSender.getInstance().setAddress("212.194.111.127");
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
                //String uniName = mainObject.getString("temp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.fragmentWeakRef.get() != null) {
//                try {
                this.fragmentWeakRef.get().onResponce(mainObject, request);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }
//        else{
//            this.fragmentWeakRef.get().error();
//        }
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