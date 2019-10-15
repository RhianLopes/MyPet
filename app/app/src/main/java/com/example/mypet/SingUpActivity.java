package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class SingUpActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextView tvLogo;
    private ImageView ivPaw;
    private TextView tvName;
    @NotEmpty(message = "Required field")
    @Length(max = 100)
    private EditText etName;
    private TextView tvNickname;
    @Length(max = 50)
    private EditText etNickname;
    private TextView tvEmail;
    @NotEmpty(message = "Required field")
    @Email(message = "invalid email")
    private EditText etEmail;
    private TextView tvPassword;
    @Password(scheme = Password.Scheme.ALPHA_NUMERIC)
    @Length(max = 80)
    private EditText etPassword;
    private TextView tvPhoto;
    @Length(max = 1000)
    private EditText etPhoto;
    private Button btRegister;
    private Validator validator;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        this.inicilizeComponents();



    }
    private void inicilizeComponents(){
        this.tvLogo = findViewById(R.id.tv_logo);
        this.ivPaw=findViewById(R.id.iv_paw);
        this.tvName=findViewById(R.id.tv_name);
        this.etName=findViewById(R.id.et_name);
        this.tvNickname=findViewById(R.id.tv_nickname);
        this.etNickname= findViewById(R.id.et_nickname);
        this.tvEmail= findViewById(R.id.tv_email);
        this.etEmail=findViewById(R.id.et_email);
        this.tvPassword= findViewById(R.id.tv_password);
        this.etPassword= findViewById(R.id.et_password);
        this.tvPhoto=findViewById(R.id.tv_photo);
        this.etPhoto=findViewById(R.id.et_photo);
        this.btRegister= findViewById(R.id.bt_registrer);
        this.validator= new Validator(this);
        this.validator.setValidationListener(this);
        retrofit = new Retrofit.Builder()
                   .baseUrl("http://192.168.43.244:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btRegister_onclick(view);
                User user = new User();
                user.setName(etName.getText().toString());
                user.setNickname(etNickname.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setPhoto(etPhoto.getText().toString());

                UserService userService = retrofit.create(UserService.class);

                userService.register(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(SingUpActivity.this, "Oi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SingUpActivity.this, "oii", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void btRegister_onclick(View view){
        validator.validate();

    }
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Welcome to MyPet", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onValidationFailed(List<ValidationError> errors){
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
