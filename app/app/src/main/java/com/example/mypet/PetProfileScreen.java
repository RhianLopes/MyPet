package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mypet.ui.petprofilemenu.PetProfileMenuFragment;

public class PetProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile_screen_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PetProfileMenuFragment.newInstance())
                    .commitNow();
        }
    }
}
