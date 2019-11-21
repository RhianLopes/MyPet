package com.example.mypet;

import com.example.mypet.model.Follower;
import com.example.mypet.model.Pet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FollowerService {

    @POST("api/follower/register")
    Call<Follower> insert(@Body Follower follower, @Header("Authorization") String authHeader);

    @GET("api/follower/find-all-by-followed/{petId}")
    Call<ArrayList<Follower>> findAllByFollowed(@Path("petId") Long petId, @Header("Authorization") String authHeader);

    @GET("api/follower/find-all-by-follower/{petId}")
    Call<ArrayList<Follower>> findAllByFollower(@Path("petId") Long petId, @Header("Authorization") String authHeader);
}
