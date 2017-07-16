package com.example.ainozen.movgeek.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ainozenbook on 9/15/2016.
 */
public interface RestInterface {

    @GET("movie/popular")
    Call<DefaultResponse> getPopularMovie(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<DefaultResponse> getTopRatedMovie(@Query("api_key") String apiKey);
}
