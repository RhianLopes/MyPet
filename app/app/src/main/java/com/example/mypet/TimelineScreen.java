package com.example.mypet;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.annotation.Url;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.mypet.R.layout.list_text;

public class TimelineScreen extends AppCompatActivity {

    private Button btEditPet;
    private Button btAddPost;
    private RecyclerView recicleView;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_timeline_screen);
        this.incializeComponents();


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PostService postService = retrofit.create(PostService.class);
        postService.findAll(token).enqueue(new Callback<ArrayList<Post>>() {

            @Override
            public void onResponse(Call<ArrayList<Post>> call, retrofit2.Response<ArrayList<Post>> response) {

                if(response.isSuccessful()){
                    Toast.makeText(TimelineScreen.this, "Welcome to MyPet", Toast.LENGTH_SHORT).show();
                    ArrayList<Post> postArrayList = response.body();

                    recicleView.setLayoutManager(new LinearLayoutManager(TimelineScreen.this));
                    recicleView.setAdapter(new PostAdapter(postArrayList, TimelineScreen.this));

                } else {
                    Toast.makeText(TimelineScreen.this, "Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(TimelineScreen.this, "erro", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void incializeComponents(){
        this.btAddPost = findViewById(R.id.bt_add_pet);
        this.btEditPet = findViewById(R.id.bt_edit);
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
