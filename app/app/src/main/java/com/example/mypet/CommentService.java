package com.example.mypet;

import com.example.mypet.model.Comment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommentService {

    @GET("api/comment/find-all")
    Call<ArrayList<Comment>> findAll (@Header("Authorization") String authHeader);

    @POST("api/comment/register")
    Call<Comment> insert (@Body Comment comment, @Header("Authorization") String authHeader);


}
