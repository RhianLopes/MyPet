package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class PetLoginScreen extends ListActivity {

    private TextView tvLogo;
    private ImageView ivPaw;
    private ListView lvPet;
    private Button btAddPet;
    private Button btEditUser;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_login_screen);
        this.initializeComponents();


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PetService petService = retrofit.create(PetService.class);

        ArrayList<Pet> petArrayList = (ArrayList<Pet>) petService.findByUserId(token);
        ArrayAdapter<Pet> adapter = new ArrayAdapter<Pet>(this, android.R.layout.simple_list_item_1, petArrayList);
        lvPet.setAdapter(adapter);


    }

    private void initializeComponents(){
        this.tvLogo= findViewById(R.id.tv_logo);
        this.ivPaw = findViewById(R.id.iv_paw);
        this.lvPet =  (ListView) findViewById(android.R.id.list);
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

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
