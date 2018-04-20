package com.pindad.aftersalespindadmekanik.RestAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WIDHIYANTO NUGROHO on 09/02/2018.
 */

public class ApiClient {

    public static final String BASE_URL = "http://api.pindad.com/as/";
//    public static final String BASE_URL = "http://192.168.90.1/rest_ci/as/";
//    public static final String BASE_URL = "http://api.androidhive.info/json/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
