package com.example.mypet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PetService {

    @POST("api/pet/register")
    Call<Pet> register(@Body Pet pet, @Header("Authorization") String authHeader);

    @GET("api/pet/find-by-user")
    Call<ArrayList<Pet>> findByUserId(@Header("Authorization") String authHeader);

}
