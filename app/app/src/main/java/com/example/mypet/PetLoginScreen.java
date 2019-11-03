package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.mypet.R.layout.list_text;

public class PetLoginScreen extends AppCompatActivity {

    private TextView tvLogo;
    private ImageView ivPaw;
    private ListView list;
    private Button btAddPet;
    private Button btEditUser;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private ArrayList<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_login_screen);
        this.initializeComponents();


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PetService petService = retrofit.create(PetService.class);

        petService.findByUserId(token).enqueue(new Callback<ArrayList<Pet>>() {

            @Override
            public void onResponse(Call<ArrayList<Pet>> call, retrofit2.Response<ArrayList<Pet>> response) {

                if(response.isSuccessful()){
                    Toast.makeText(PetLoginScreen.this, "Welcome to MyPet", Toast.LENGTH_SHORT).show();
                    ArrayList<Pet> petArrayList = response.body();
                    

                    ArrayAdapter<Pet> adapter = new ArrayAdapter<Pet>(PetLoginScreen.this, R.layout.list_text,R.id.textView2, petArrayList);
                    list.setAdapter(adapter);


                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent itTimeline = new Intent(PetLoginScreen.this, TimelineScreen.class);
                            startActivity(itTimeline);
                        }
                    });

                } else {
                    Toast.makeText(PetLoginScreen.this, "Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pet>> call, Throwable t) {
                Toast.makeText(PetLoginScreen.this, "erro", Toast.LENGTH_SHORT).show();
            }
        });

        this.btAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAddScreen = new Intent(PetLoginScreen.this, AddPetActivity.class);
                startActivity(itAddScreen);
            }
        });
        this.btEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itEdit = new Intent(PetLoginScreen.this, EditHumanUserActivity.class);
                startActivity(itEdit);
            }
        });



    }

    private void initializeComponents(){
        this.tvLogo= findViewById(R.id.tv_logo);
        this.list = findViewById(R.id.list);
        this.btAddPet = findViewById(R.id.bt_add_pet);
        this.btEditUser = findViewById(R.id.bt_edit_user);
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
