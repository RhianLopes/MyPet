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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.R;
import com.example.mypet.UserService;
import com.example.mypet.model.User;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class EditHumanUserActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextView tvLogo;
    private TextView tvName;
    @NotEmpty(message = "Required field")
    @Length(max = 100)
    private EditText etName;
    private TextView tvNickname;
    @Length(max = 50)
    private EditText etNickname;
    private TextView tvEmail;
    @NotEmpty(message = "Required field")
    @Length(max= 100)
    @Email
    private EditText etEmail;
    private TextView tvPhoto;
    @Length(max = 1000)
    private EditText etPhoto;
    private Button btEdit;
    private Retrofit retrofit;
    private Validator validator;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long userId;
    private String userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_human_user);
        this.inicilizeComponents();


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");
        UserService userServiceId = retrofit.create(UserService.class);

        userServiceId.find(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user;
                user = response.body();
                if (user != null) {
                    etName.setText(user.getName());
                    etEmail.setText(user.getEmail());
                    etNickname.setText(user.getNickname());
                    etPhoto.setText(user.getPhoto());
                    userPassword = user.getPassword();
                    userId = user.getId();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        this.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                btRegistrer_onClick(view);

            }
        });
    }
    private void inicilizeComponents(){
        this.tvLogo = findViewById(R.id.tv_logo);

        this.tvName=findViewById(R.id.name);
        this.etName=findViewById(R.id.et_name);
        this.tvNickname=findViewById(R.id.tv_nickname);
        this.etNickname= findViewById(R.id.et_nickname);
        this.tvEmail= findViewById(R.id.tv_email);
        this.etEmail=findViewById(R.id.et_email);
        this.tvPhoto=findViewById(R.id.tv_photo);
        this.etPhoto=findViewById(R.id.et_photo);
        this.btEdit= findViewById(R.id.bt_edit);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    private void btRegistrer_onClick(View view){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        User user = new User();
        user.setName(etName.getText().toString());
        user.setNickname(etNickname.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPassword(userPassword);
        user.setPhoto(etPhoto.getText().toString());
        user.setId(userId);

        UserService userService = retrofit.create(UserService.class);

        userService.update(user,token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EditHumanUserActivity.this, "User successfully edited!", Toast.LENGTH_SHORT).show();
                    Intent itPet = new Intent(EditHumanUserActivity.this, PetLoginActivity.class);
                    startActivity(itPet);
                    finish();
                }else{
                    Toast.makeText(EditHumanUserActivity.this, "Error! We coudn't edit your profile, sorry! Try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditHumanUserActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
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


}
