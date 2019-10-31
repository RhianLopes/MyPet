package com.example.mypet;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @POST("public/api/user/register")
    Call<User> register(@Body User user);

    @POST("public/authentication/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @PUT("api/user/edit")
    Call<User> update(@Body User user, @Header("Authorization") String authHeader);

    @GET("api/user/find-by-id")
    Call<User> find(@Header("Authorization") String authHeader);

}
