package com.Util.Model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gopi on 02-Apr-16.
 * Reference: http://www.iayon.com/consuming-rest-api-with-retrofit-2-0-in-android/
 */
public class RetrofitServiceBuilder {
    public static Object getService(Class serviceClass, String baseUrl) {
        /*OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                Log.w("Retrofit@Response", response.body().string());
                return response;
            }
        });*/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                     .baseUrl(baseUrl)
                     .addConverterFactory(GsonConverterFactory.create(gson))
                     .client(httpClient.connectTimeout(10, TimeUnit.MINUTES)
                             .writeTimeout(10, TimeUnit.MINUTES)
                             .readTimeout(10, TimeUnit.MINUTES)
                             .build())
                .build();
            return retrofit.create(serviceClass);
    }



}
