package com.example.mypet;

import com.example.mypet.model.Pet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PetService {

    @POST("api/pet/register")
    Call<Pet> register(@Body Pet pet, @Header("Authorization") String authHeader);

    @GET("api/pet/find-by-user")
    Call<ArrayList<Pet>> findByUserId(@Header("Authorization") String authHeader);

    @GET("api/pet/find/{id}")
    Call<Pet> find(@Path("id") Long id,  @Header("Authorization") String authHeader);

    @PUT("api/pet/edit")
    Call<Pet> edit(@Body Pet pet, @Header("Authorization") String authHeader);


}
