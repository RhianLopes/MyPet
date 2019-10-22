package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
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

public class LoginScreenActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextView tvLogo;
    private ImageView ivPaw;
    private TextView tvEmail;
    @NotEmpty(message = "Required field")
    @Length(max= 100)
    @Email
    private EditText etEmail;
    private TextView tvPassword;
    @Password
    @Length(min = 6,max = 80)
    private EditText etPassword;
    private Button btSignUp;
    private Validator validator;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.inicializeComponents();

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void inicializeComponents(){
        this.tvLogo = findViewById(R.id.tv_logo);
        this.ivPaw = findViewById(R.id.iv_paw);
        this.tvEmail = findViewById(R.id.tv_email);
        this.etEmail = findViewById(R.id.et_email);
        this.tvPassword = findViewById(R.id.tv_password);
        this.etPassword = findViewById(R.id.et_email);
        this.btSignUp = findViewById(R.id.bt_sign_in);
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


    private void doLogin(User email, User password){

    }
    @Override
    public void onValidationSucceeded() {
        Intent itPetScreen = new Intent(LoginScreenActivity.this, PetScreenActivity.class );
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
