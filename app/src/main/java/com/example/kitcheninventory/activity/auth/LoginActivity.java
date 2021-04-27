/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.DashBoard;
import com.example.kitcheninventory.R;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    TextView txtSkip, txtLogIn;
    LinearLayout txtgmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dummy);

        txtLogIn = findViewById(R.id.txtLogIn);
        txtSkip = findViewById(R.id.txtSkip);
        txtgmail = findViewById(R.id.txtgmail);

        txtgmail.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, DashBoard.class);
            startActivity(intent);
            finish();
        });
    }
}
