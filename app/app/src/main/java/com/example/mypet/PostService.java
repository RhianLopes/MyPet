package com.example.mypet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface PostService {

    @Headers("Content-Type: application/json")
    @GET("api/post/find-all")
    Call<ArrayList<Post>> findAll(@Header("Authorization") String authHeader);
}
