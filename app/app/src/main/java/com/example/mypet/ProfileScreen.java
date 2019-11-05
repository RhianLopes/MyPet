package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileScreen extends AppCompatActivity {

    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvDescription;
    private Button btFollow;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        this.inicializeComponents();

        Long value = getIntent().getExtras().getLong("id");
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PetService petService = retrofit.create(PetService.class);

        petService.find(value, token).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if(response.isSuccessful()){
                    Pet pet = response.body();
                    Pet pet1 = pet;
                    if(response.body() != null) {
                        if (pet != null) {
                            tvDescription.setText(pet1.getDescription());
                            tvName.setText(pet1.getName());
                            Picasso.with(ProfileScreen.this).load(pet1.getPhoto()).into(ivPhoto);
                        }
                }else{
                    Toast.makeText(ProfileScreen.this, "Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
                }
            }


            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(ProfileScreen.this, "erro", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void inicializeComponents(){

        this.ivPhoto = findViewById(R.id.iv_photo);
        this.tvDescription = findViewById(R.id.tv_description);
        this.tvName = findViewById(R.id.tv_name);
        this.btFollow = findViewById(R.id.bt_follow);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
