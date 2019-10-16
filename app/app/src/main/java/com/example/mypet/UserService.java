package com.example.mypet;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("public/api/user/register")
    Call<User> register(@Body User user);
}
