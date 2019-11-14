package com.example.mypet.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mypet.EnjoyService;
import com.example.mypet.R;
import com.example.mypet.adapter.EnjoyAdapter;
import com.example.mypet.model.Enjoy;

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


public class ListLikesActivity extends AppCompatActivity {

    private RecyclerView listLikes;
    private SharedPreferences sharedPreferences;
    private String token;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_likes);
        this.inicializaComponents();

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        Long idPost = getIntent().getLongExtra("idPost", 0);


        EnjoyService enjoyService = retrofit.create(EnjoyService.class);

        enjoyService.findAllByPost(idPost, token).enqueue(new Callback<ArrayList<Enjoy>>() {
            @Override
            public void onResponse(Call<ArrayList<Enjoy>> call, retrofit2.Response<ArrayList<Enjoy>> response) {
                if(response.isSuccessful()){
                    ArrayList<Enjoy> enjoy = response.body();



                    listLikes.setLayoutManager(new LinearLayoutManager(ListLikesActivity.this));
                    listLikes.setAdapter(new EnjoyAdapter(enjoy, ListLikesActivity.this));

                }else{
                    Toast.makeText(ListLikesActivity.this, "Sorry, Try again later.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Enjoy>> call, Throwable t) {

            }
        });





    }

    private void inicializaComponents(){

        listLikes = findViewById(R.id.rv_likes);
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
