package com.example.mypet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mypet.R;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView tvLogo;
    private Button btLogin;
    private Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.initializeComponents();


        this.btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itSingUpScreen = new Intent(HomeScreenActivity.this, SignUpActivity.class);
                startActivity(itSingUpScreen);
                finish();
            }
        });
        this.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itLoginScreen = new Intent(HomeScreenActivity.this, UserLoginActivity.class);
                startActivity(itLoginScreen);
                finish();
            }
        });
    }

    private void initializeComponents(){
        this.tvLogo= findViewById(R.id.tv_logo);
        this.btLogin= findViewById(R.id.bt_login);
        this.btSignUp= findViewById(R.id.bt_sign_up);

    }
}
