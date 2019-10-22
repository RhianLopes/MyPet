package com.example.mypet;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("public/api/user/register")
    Call<User> register(@Body User user);

    @POST("public/authentication/login")
    Call<LoginRequest> register(@Body LoginRequest loginRequest);

}
