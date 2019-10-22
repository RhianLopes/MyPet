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

public class SignUpScreenActivity extends AppCompatActivity implements Validator.ValidationListener {

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
    @Length(max= 100)
    @Email
    private EditText etEmail;
    private TextView tvPassword;
    @Password
    @Length(max = 80)
    private EditText etPassword;
    private TextView tvPhoto;
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
                                Toast.makeText(SignUpScreenActivity.this, "Welcome to MyPet", Toast.LENGTH_SHORT).show();
                                btRegistrer_onClick(view);
                            }else{
                                Toast.makeText(SignUpScreenActivity.this, "Error! Try again later!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignUpScreenActivity.this, "erro", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });



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
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        retrofit = new Retrofit.Builder()
                   .baseUrl("http://10.0.2.2:8080/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    private void btRegistrer_onClick(View view){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Intent itPetScreen = new Intent(SignUpScreenActivity.this, PetLoginActivity.class );
        startActivity(itPetScreen);
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
