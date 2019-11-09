package com.example.mypet.Services;

import com.example.mypet.Comment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CommentService {

    @GET("api/comment/find-all")
    Call<ArrayList<Comment>> findAll (@Header("Authorization") String authHeader);


}
