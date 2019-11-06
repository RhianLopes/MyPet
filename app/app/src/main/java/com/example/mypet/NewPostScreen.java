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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class NewPostScreen extends AppCompatActivity implements Validator.ValidationListener {

    private TextView tvLogo;
    private TextView tvPhoto;
    private TextView tvDescription;
    @NotEmpty
    private EditText etPhoto;
    @NotEmpty
    private EditText etDescription;
    private Button btAddPost;
    private Validator validator;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long idPet;
    private Pet pet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_screen);

        this.inicializeComponents();

        idPet = getIntent().getLongExtra("id", 0);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");


        btAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Post post = new Post();
                post.setPhotos(etPhoto.getText().toString());
                post.setDescription(etDescription.getText().toString());
                post.setPet(new Pet());
                post.getPet().setId(idPet);
                PostService postService =  retrofit.create(PostService.class);
                postService.register(post, token).enqueue(new Callback<Post>(){

                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(NewPostScreen.this, "New post created!", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(NewPostScreen.this, "Error! Try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(NewPostScreen.this, "We can´t creat the new post, sorry!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void inicializeComponents(){

        this.tvLogo = findViewById(R.id.tv_logo);
        this.tvPhoto = findViewById(R.id.tv_photo);
        this.tvDescription = findViewById(R.id.tv_description);
        this.etPhoto= findViewById(R.id.et_photo);
        this.etDescription = findViewById(R.id.et_description);
        this.btAddPost = findViewById(R.id.bt_add_post);

        this.validator = new Validator(this);
        this.validator.setValidationListener(NewPostScreen.this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



    }

    private void btLogin_onClick(View view){
        validator.validate();
    }




    public void onValidationSucceeded() {

    }


    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error: errors){
            View component = error.getView();
            String errorMessage = error.getCollatedErrorMessage(this);
            if (component instanceof EditText){
                ((TextView) component).setError(errorMessage);
            }
        }
        Toast.makeText(this, "Error! Try Again!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
