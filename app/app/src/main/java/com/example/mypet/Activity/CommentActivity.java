package com.example.mypet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.mypet.Adapter.CommentAdapter;
import com.example.mypet.Comment;
import com.example.mypet.Post;
import com.example.mypet.R;
import com.example.mypet.Services.PostService;

import java.io.IOException;
import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private Button btNewComment;
    private RecyclerView recicleView;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_screen);
        this.incializeComponents();

        Long idPet = getIntent().getLongExtra("id", 0);
        Long idPost = getIntent().getLongExtra("idPost", 0);


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PostService postService = retrofit.create(PostService.class);

        postService.findById(idPost, token).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                Post post = response.body();
                ArrayList<Comment> commentArrayList =  post.getComment();


                recicleView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                recicleView.setAdapter(new CommentAdapter(commentArrayList, CommentActivity.this));
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void incializeComponents(){
        this.btNewComment = findViewById(R.id.bt_new_comment);
        this.recicleView = findViewById(R.id.comments);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
