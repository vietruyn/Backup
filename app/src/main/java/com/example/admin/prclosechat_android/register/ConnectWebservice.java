package com.example.admin.prclosechat_android.register;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class ConnectWebservice {
    private String link = null;
    private String data = null;
    public volatile boolean parsingComplete = true;

    public ConnectWebservice(String url) {
        this.link = url;
    }

    public String getData() {
        return data;

    }

    public void fetchJSON() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(link);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        // Starts the query
                        conn.connect();
                        InputStream stream = conn.getInputStream();
                        data = convertStreamToString(stream);
                        stream.close();
                        parsingComplete = false;

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                        Log.e(">>>>>>>>>>>>>>>>>", e.toString());
//						data = e.toString();
                        parsingComplete = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(">>>>>>>>>>>>>>>>>", e.toString());
//						data = e.toString();
                        parsingComplete = false;
                    }
                }
            });

            thread.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static String convertStreamToString(InputStream is) {
        try {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

}
