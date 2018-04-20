package com.pnf.elar.app.activity.schedule.apiClient.retrofit;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The type Retro client.
 */
public class RetroClient {
    /**
     * Get retro client api.
     *
     * @return the api
     */
    public static API getRetroClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.elar.se")
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())

                .client(client)
                .build();
        return retrofit.create(API.class);

    }


}
