package com.pnf.elar.app.service;

import com.elar.util.SmartClassUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by VKrishnasamy on 11-11-2016.
 */

public class FormDataWebservice {

    public static String excutePost(String targetURL, String urlParameters,String method)
    {
        URL url;

        HttpURLConnection connection = null;
        try {
            url = new URL(targetURL);



            if (method.equals("POST")) {
                SmartClassUtil.PrintMessage("url ---- " + targetURL);

             SmartClassUtil.PrintMessage("urlParameters ---- " + urlParameters);


                //Create connection
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();


                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                SmartClassUtil.PrintMessage("Response ---- " + response.toString());

                return response.toString();
            }
            else
            {
                InputStream inputStream;
                try {

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int statusCodeValue = conn.getResponseCode();
                    if (statusCodeValue == 200) {
                        inputStream = new BufferedInputStream(conn.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = "";
                        String result = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }

                        if (null != inputStream) {
                            inputStream.close();
                        }
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError oume) {
                    System.gc();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
        return  null;
    }
}
