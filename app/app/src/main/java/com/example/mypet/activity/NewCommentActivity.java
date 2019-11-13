package com.example.mypet.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.R;
import com.example.mypet.model.Comment;
import com.example.mypet.model.Pet;
import com.example.mypet.model.Post;
import com.example.mypet.CommentService;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewCommentActivity extends AppCompatActivity {

    private TextView tvLogo;
    private TextView tvComment;
    private EditText etNewComment;
    private Button btCreateComment;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        this.incializeComponents();

        final Long idPet = getIntent().getLongExtra("idPet", 0);
        final Long idPost = getIntent().getLongExtra("idPost", 0);


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        this.btCreateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comment comment = new Comment();
                comment.setContent(etNewComment.getText().toString());
                comment.setPost(new Post());
                comment.getPost().setId(idPost);
                comment.setPet(new Pet());
                comment.getPet().setId(idPet);
                CommentService commentService = retrofit.create(CommentService.class);

                commentService.insert(comment, token).enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, retrofit2.Response<Comment> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(NewCommentActivity.this, "New comment created", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(NewCommentActivity.this, CommentActivity.class);
                            startActivity(it);

                        }else{
                            Toast.makeText(NewCommentActivity.this, "Sorry, we had problems! Try again later.", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        Toast.makeText(NewCommentActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void incializeComponents(){

        this.tvLogo = findViewById(R.id.tv_logo);
        this.tvComment = findViewById(R.id.tv_pet_name);
        this.etNewComment = findViewById(R.id.et_comment);
        this.btCreateComment = findViewById(R.id.bt_create);
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
