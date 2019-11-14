package com.example.mypet.activity;

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
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class SignUpActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextView tvLogo;
    private TextView tvName;
    private TextView tvNickname;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvPhoto;
    @NotEmpty(message = "Required field")
    @Length(max = 100)
    private EditText etName;
    @Length(max = 50)
    private EditText etNickname;
    @NotEmpty(message = "Required field")
    @Length(max= 100)
    @Email
    private EditText etEmail;
    @Password
    @Length(max = 80)
    private EditText etPassword;
    @Length(max = 1000)
    private EditText etPhoto;
    private Button btRegister;
    private Retrofit retrofit;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        this.inicilizeComponents();

            this.btRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

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
                            if(response.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Welcome to MyPet!", Toast.LENGTH_SHORT).show();
                                btRegistrer_onClick(view);
                                Intent itLogin = new Intent(SignUpActivity.this, UserLoginActivity.class);
                                startActivity(itLogin);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Sorry, we had problems! Try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignUpActivity.this, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
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
        this.tvPassword= findViewById(R.id.tv_password);
        this.etPassword= findViewById(R.id.et_password);
        this.tvPhoto=findViewById(R.id.tv_photo);
        this.etPhoto=findViewById(R.id.et_photo);
        this.btRegister= findViewById(R.id.bt_registrer);
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

    }
}
