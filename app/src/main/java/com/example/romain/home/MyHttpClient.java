package com.example.romain.home;

import android.content.Context;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLException;

/**
 * Created by romain on 3/5/16.
 */
public class MyHttpClient extends DefaultHttpClient {

    final Context context;

    public MyHttpClient(Context context) {
        this.context = context;
    }

    @Override
    protected ClientConnectionManager createClientConnectionManager() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", newSslSocketFactory(), 8899));
        return new SingleClientConnManager(getParams(), registry);
    }

    private SSLSocketFactory newSslSocketFactory() {
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.mykeystore);
            try {
                trusted.load(in, "passpass".toCharArray());
            }
            finally {
                in.close();
            }

            SSLSocketFactory mySslFact = new SSLSocketFactory(trusted);
            mySslFact.setHostnameVerifier(new MyHstNameVerifier());
            return mySslFact;
        } catch(Exception e) {
            throw new AssertionError(e);
        }
    }

    public class MyHstNameVerifier extends org.apache.http.conn.ssl.AbstractVerifier {

        String[] allowHost = {"rb", "192.168.0.104", "128.79.58.158"};

        @Override

        public void verify(String host, String[] cns,
                           String[] subjectAlts) throws SSLException {
            // If the host is any the hosts to be allowed, return, else throw exception
            for (int i=0; i < allowHost.length; i++) {
                if (host.equals(allowHost[i]))
                    return;
            }
            throw new SSLException(host);
        }
    }
}
