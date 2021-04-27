/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.starting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.auth.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();

        }, 4000);

    }
}
