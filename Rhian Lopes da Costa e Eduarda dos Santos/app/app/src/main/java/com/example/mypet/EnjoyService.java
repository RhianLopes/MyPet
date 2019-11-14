package com.example.mypet;

import com.example.mypet.model.Enjoy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EnjoyService {

    @POST("api/enjoy/register")
    Call<Enjoy> register(@Body Enjoy enjoy);

    @GET("api/enjoy/find-all-by-post/{postId}")
    Call<ArrayList<Enjoy>> findAllByPost(@Path("postId") Long postId, @Header("Authorization") String authHeader );
}
