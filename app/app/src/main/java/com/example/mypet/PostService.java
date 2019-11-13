package com.example.mypet;

import com.example.mypet.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {

    @Headers("Content-Type: application/json")
    @GET("api/post/find-all")
    Call<ArrayList<Post>> findAll(@Header("Authorization") String authHeader);

    @POST("api/post/register")
    Call<Post> register(@Body Post post, @Header("Authorization") String authHeader);

    @GET("api/post/find/{id}")
    Call<Post> findById(@Path("id") Long id, @Header("Authorization") String authHeader);

    @GET("api/post/find-by-pet/{petId}")
    Call<ArrayList<Post>> findAllByPetId(@Path("id") Long id, @Header("Authorization") String authHeader);

    @DELETE("api/post/delete/{id}")
    Call<Void> delete(@Path("id") Long id);

}
