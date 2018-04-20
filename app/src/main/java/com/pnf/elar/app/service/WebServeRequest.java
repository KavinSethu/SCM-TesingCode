package com.pnf.elar.app.service;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * Created by sthiritha on 20/5/16.
 */
public class WebServeRequest {

    public static String postJSONRequest(String url_details, String params) {
        URL url;
        String response = null;
        try {
            //    String query=String.format("param1=%s&param2=%s", URLEncoder.encode(param1,charset),URLEncoder.encode(param2,charset));
            url = new URL(url_details);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(150000);
            Log.d("url---",url_details);
            Log.d("params", "" + params);
            //   urlConnection.setRequestProperty("Accept-charset", charset);
            //    urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(150000);
            urlConnection.setRequestMethod("POST");
//            urlConnection.connect();
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(params.trim());
            wr.flush();
            wr.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (urlConnection.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                response = output;
            }

            urlConnection.disconnect();
            //textView.setText(in.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
