package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView tvLogo;
    private Button btLogin;
    private Button btSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.initializeComponents();


        this.btSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itLoginScreen = new Intent(HomeScreenActivity.this, SingUpActivity.class);
                startActivity(itLoginScreen);
                finish();
            }
        });

    }

    private void initializeComponents(){
        this.tvLogo= findViewById(R.id.tv_logo);
        this.btLogin= findViewById(R.id.bt_login);
        this.btSingUp= findViewById(R.id.bt_sing_up);

    }
}
