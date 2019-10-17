package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void inicializeComponents(){
        this.tvLogo = findViewById(R.id.tv_logo);
        this.ivPaw = findViewById(R.id.iv_paw);
        this.tvEmail = findViewById(R.id.tv_email);
        this.etEmail = findViewById(R.id.et_email);
        this.tvPassword = findViewById(R.id.tv_password);
        this.etPassword = findViewById(R.id.et_email);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);

    }
    private void btRegistrer_onClick(View view){
        validator.validate();
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
