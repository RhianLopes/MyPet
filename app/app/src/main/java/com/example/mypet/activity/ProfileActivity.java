package com.example.mypet.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.FollowerService;
import com.example.mypet.model.Follower;
import com.example.mypet.model.Pet;
import com.example.mypet.R;
import com.example.mypet.PetService;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvDescription;
    private TextView numberFollower;
    private Button btFollow;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long id;
    private Long idPet;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        this.inicializeComponents();

        id = getIntent().getExtras().getLong("id");
        idPet = getIntent().getExtras().getLong("idPet");
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        pet = new Pet();

        PetService petService = retrofit.create(PetService.class);

        petService.find(id, token).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    pet = response.body();
                    if (response.body() != null) {
                        if (pet != null) {
                            tvDescription.setText(pet.getDescription());
                            tvName.setText(pet.getName());
                            Picasso.with(ProfileActivity.this).load(pet.getPhoto()).into(ivPhoto);
                            numberFollower.setText("Followers: " + pet.getFollower());
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "We had problems to loading the petfile, try again later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }



            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
        btFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Follower follower = new Follower();

                follower.setPetFollower(new Pet());
                follower.getPetFollower().setId(idPet);
                follower.setPetFollowed(new Pet());
                follower.getPetFollowed().setId(id);

                FollowerService followerService = retrofit.create(FollowerService.class);

                followerService.insert(follower, token).enqueue(new Callback<Follower>() {
                    @Override
                    public void onResponse(Call<Follower> call, Response<Follower> response) {
                        if(response.isSuccessful()){
                            btFollow.setText("Following");
                            Long followers = pet.getFollowed()+1;
                            numberFollower.setText("Followers: " +followers);
                        }else{
                            Toast.makeText(ProfileActivity.this, "Sorry we had problems, try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Follower> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Sorry we had internal problems, try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    private void inicializeComponents(){

        this.ivPhoto = findViewById(R.id.iv_photo);
        this.tvDescription = findViewById(R.id.tv_description);
        this.tvName = findViewById(R.id.pet_name);
        this.btFollow = findViewById(R.id.bt_follow);
        this.numberFollower = findViewById(R.id.number_follower);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
