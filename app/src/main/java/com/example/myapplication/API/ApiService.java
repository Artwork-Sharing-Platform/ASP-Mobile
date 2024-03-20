package com.example.myapplication.API;

import com.example.myapplication.Models.Art;
import com.example.myapplication.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder().baseUrl("https://pesterin.up.railway.app")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/api/v1/art/getAllArtworkByCreatedAtArt")
    Call<List<Art>> getAllArt();

    @GET("/api/v1/art/getArtWorkById/{id}")
    Call<Art> getArtWorkById(@Path("id") String id);

    @POST("/api/v1/auth//loginMobile")
    Call<User> createUser(@Body User user);
}
