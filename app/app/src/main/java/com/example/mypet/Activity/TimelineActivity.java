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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mypet.Post;
import com.example.mypet.Adapter.PostAdapter;
import com.example.mypet.R;
import com.example.mypet.Services.PostService;
import com.example.mypet.fragment.MenuFragment;

import java.io.IOException;
import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private Button btEditPet;
    private Button btAddPost;
    private RecyclerView recicleView;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long idPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_timeline_screen);
        this.incializeComponents();

        idPet = getIntent().getLongExtra("id", 0);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PostService postService = retrofit.create(PostService.class);
        postService.findAll(token).enqueue(new Callback<ArrayList<Post>>() {

            @Override
            public void onResponse(Call<ArrayList<Post>> call, retrofit2.Response<ArrayList<Post>> response) {

                if(response.isSuccessful()){
                    ArrayList<Post> postArrayList = response.body();

                    recicleView.setLayoutManager(new LinearLayoutManager(TimelineActivity.this));
                    recicleView.setAdapter(new PostAdapter(postArrayList, TimelineActivity.this));

                } else {
                    Toast.makeText(TimelineActivity.this, "We had problems to loading posts, try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(TimelineActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
            }
        });

        btAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAddPost = new Intent (TimelineActivity.this, NewPostActivity.class);
                itAddPost.putExtra("id", idPet);
                startActivity(itAddPost);
            }
        });


        btEditPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAddPost = new Intent (TimelineActivity.this, MenuFragment.class);
                itAddPost.putExtra("id", idPet);
                startActivity(itAddPost);
            }
        });
    }

    private void incializeComponents(){
        this.btAddPost = findViewById(R.id.bt_add_post);
        this.btEditPet = findViewById(R.id.bt_edit_pet);
        this.recicleView = findViewById(R.id.recicleView);
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
