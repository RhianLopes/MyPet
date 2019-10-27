package com.example.mypet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PetService {

    @GET("api/pet/find-by-user")
    Call<ArrayList<Object>> findByUserId(@Header("Authorization") String authHeader);

}
