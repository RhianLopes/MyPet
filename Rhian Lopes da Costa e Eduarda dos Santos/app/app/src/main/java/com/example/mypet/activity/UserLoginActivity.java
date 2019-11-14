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

import com.example.mypet.model.LoginRequest;
import com.example.mypet.model.LoginResponse;
import com.example.mypet.R;
import com.example.mypet.UserService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class UserLoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextView tvLogo;
    private TextView tvEmail;
    private TextView tvPassword;
    @NotEmpty(message = "Required field")
    @Length(max= 100)
    @Email
    private EditText etEmail;

    @Password
    @Length(min = 6,max = 80)
    private EditText etPassword;
    private Button btSignUp;
    private Validator validator;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.inicializeComponents();


        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                LoginRequest login = new LoginRequest();
                login.setEmail(etEmail.getText().toString());
                login.setPassword(etPassword.getText().toString());

                UserService userService = retrofit.create(UserService.class);

                userService.login(login).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(UserLoginActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                            btLogin_onClick(v);
                            sharedPreferences = getApplicationContext().getSharedPreferences("MyPet", 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            LoginResponse loginResponse = response.body();
                            editor.putString("token", loginResponse.getAccessToken());
                            editor.commit();
                            Intent itPetLogin = new Intent(UserLoginActivity.this, PetLoginActivity.class );

                            startActivity(itPetLogin);
                            finish();
                        }else{
                            Toast.makeText(UserLoginActivity.this, "Sorry, we had problems! Try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(UserLoginActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
                    }


                });
            }
        });

    }

    public void inicializeComponents(){
        this.tvLogo = findViewById(R.id.tv_logo);
        this.tvEmail = findViewById(R.id.tv_email);
        this.etEmail = findViewById(R.id.et_email);
        this.tvPassword = findViewById(R.id.tv_password);
        this.etPassword = findViewById(R.id.et_password);
        this.btSignUp = findViewById(R.id.bt_sign_in);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    private void btLogin_onClick(View view){
        validator.validate();
    }



    @Override
    public void onValidationSucceeded() {

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
