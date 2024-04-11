package br.rafaelhorochovec.app_streetfighter_android.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {

    private static Retrofit retrofit;
    private static String BASE_URL = "http://192.168.100.62:8080";

    public static Retrofit getRetrofitInstance() {
        retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        return retrofit;
    }
}