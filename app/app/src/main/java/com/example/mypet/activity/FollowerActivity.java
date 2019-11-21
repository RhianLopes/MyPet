package com.example.mypet.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.FollowerService;
import com.example.mypet.R;
import com.example.mypet.adapter.FollowerAdapter;
import com.example.mypet.model.Follower;

import java.io.IOException;
import java.util.ArrayList;

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

public class FollowerActivity extends AppCompatActivity {

    private RecyclerView rvFollower;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long idPet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        this.rvFollower = findViewById(R.id.rv_follower);
        this.inicializeComponents();

        idPet =  getIntent().getLongExtra("idPet", 0);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        FollowerService followerService = retrofit.create(FollowerService.class);

        followerService.findAllByFollowed(idPet, token).enqueue(new Callback<ArrayList<Follower>>() {
            @Override
            public void onResponse(Call<ArrayList<Follower>> call, retrofit2.Response<ArrayList<Follower>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Follower> followerArrayList = response.body();


                    rvFollower.setLayoutManager(new LinearLayoutManager(FollowerActivity.this));
                    rvFollower.setAdapter(new FollowerAdapter(followerArrayList, FollowerActivity.this));

                }else{
                    Toast.makeText(FollowerActivity.this, "Sorry we had problems!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Follower>> call, Throwable t) {
                Toast.makeText(FollowerActivity.this, "   ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void inicializeComponents(){

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
