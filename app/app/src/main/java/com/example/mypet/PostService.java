package com.example.mypet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {

    @GET("api/post/find-all")
    Call<ArrayList<Object>> findAll();
}
