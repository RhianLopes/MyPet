package com.example.mypet.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.model.Genre;
import com.example.mypet.model.Pet;
import com.example.mypet.R;
import com.example.mypet.PetService;
import com.example.mypet.model.Specie;

public class AddPetActivity extends AppCompatActivity {

    private TextView tvLogo;
    private TextView tvName;
    private EditText etName;
    private TextView tvSpecie;
    private RadioGroup rgSpecie;
    private RadioButton rbSelectedSpecie;
    private TextView tvDescription;
    private EditText etDescription;
    private TextView tvGenre;
    private RadioGroup rgGenre;
    private RadioButton rbSelectedGenre;
    private TextView tvPhoto;
    private EditText etPhoto;
    private Button btAddPet;
    private Retrofit retrofit;
    private Specie selectedSpecie;
    private Genre selectedGenre;
    private SharedPreferences sharedPreferences;
    private String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        this.inicializeComponents();


        this.rgSpecie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSelectedSpecie = findViewById(checkedId);
                if(rbSelectedSpecie == findViewById(R.id.rb_dog)){
                    selectedSpecie = Specie.DOG;
                }else if(rbSelectedSpecie == findViewById(R.id.rb_cat)){
                    selectedSpecie = Specie.CAT;
                }
            }
        });

        this.rgGenre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSelectedGenre = (RadioButton) group.findViewById(checkedId);
                if(rbSelectedGenre == findViewById(R.id.rb_girl)){
                    selectedGenre = Genre.FEMALE;
                }else if(rbSelectedGenre == findViewById(R.id.rb_boy)){
                    selectedGenre = Genre.MALE;
                }
            }

        });

        this.btAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
                token = sharedPreferences.getString("token", "erro");

                Pet pet = new Pet();
                 pet.setName(etName.getText().toString());
                 pet.setDescription(etDescription.getText().toString());
                 pet.setGenre(selectedGenre.toString());
                 pet.setSpecie(selectedSpecie.toString());
                 pet.setPhoto(etPhoto.getText().toString());


                PetService petService = retrofit.create(PetService.class);

                 petService.register(pet,token).enqueue(new Callback<Pet>() {
                     @Override
                     public void onResponse(Call<Pet> call, Response<Pet> response) {
                         if(response.isSuccessful()){
                             Toast.makeText(AddPetActivity.this, "The pet was created!", Toast.LENGTH_SHORT).show();
                             Intent itPetLogin = new Intent(AddPetActivity.this, PetLoginActivity.class);
                             startActivity(itPetLogin);
                             finish();
                         }else{
                             Toast.makeText(AddPetActivity.this, "Sorry! We couldn´t create the new pet, try again later!", Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onFailure(Call<Pet> call, Throwable t) {
                         Toast.makeText(AddPetActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        });

    }

    private void inicializeComponents(){
        this.tvLogo = findViewById(R.id.tv_logo);
        this.tvName = findViewById(R.id.name);
        this.etName = findViewById(R.id.et_name);
        this.tvSpecie = findViewById(R.id.tv_specie);
        this.tvDescription = findViewById(R.id.tv_description);
        this.etDescription = findViewById(R.id.et_description);
        this.tvGenre = findViewById(R.id.tv_genre);
        this.tvPhoto = findViewById(R.id.tv_photo);
        this.etPhoto = findViewById(R.id.et_photo);
        this.btAddPet = findViewById(R.id.bt_add_pet);
        this.rgGenre = findViewById(R.id.rg_genre);
        this.rgSpecie = findViewById(R.id.rg_specie);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
