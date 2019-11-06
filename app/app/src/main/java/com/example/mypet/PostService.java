package com.example.mypet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {

    @Headers("Content-Type: application/json")
    @GET("api/post/find-all")
    Call<ArrayList<Post>> findAll(@Header("Authorization") String authHeader);

    @POST("api/post/register")
    Call<Post> register(@Body Post post, @Header("Authorization") String authHeader);


}
