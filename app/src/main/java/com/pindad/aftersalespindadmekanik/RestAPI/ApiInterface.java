package com.pindad.aftersalespindadmekanik.RestAPI;

import com.pindad.aftersalespindadmekanik.Modul.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by WIDHIYANTO NUGROHO on 09/02/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @PUT("login")
    Call<List<Users>> putLogin(@Field("username") String username,
                               @Field("password") String password);
}
