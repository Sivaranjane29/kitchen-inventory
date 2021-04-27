/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.video;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kitcheninventory.R;

import java.util.Objects;

public class VideoDetails extends AppCompatActivity {
    LinearLayout lnrIngredients, lnrIngredientInner, lnrDirections, lnrDirectionsInner, lnrNutrition, lnrNutritionInner;
    boolean IsOpenIng, IsOpenDir, IsOpenNut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("How To");

        lnrIngredients = findViewById(R.id.lnrIngredients);
        lnrIngredientInner = findViewById(R.id.lnrIngredientInner);
        lnrDirections = findViewById(R.id.lnrDirections);
        lnrDirectionsInner = findViewById(R.id.lnrDirectionsInner);
        lnrNutrition = findViewById(R.id.lnrNutrition);
        lnrNutritionInner = findViewById(R.id.lnrNutritionInner);

        lnrIngredients.setOnClickListener(v -> {
            if (!IsOpenIng) {
                IsOpenIng = true;
                lnrIngredientInner.setVisibility(View.VISIBLE);
            } else {
                IsOpenIng = false;
                lnrIngredientInner.setVisibility(View.GONE);
            }
        });
        lnrDirections.setOnClickListener(v -> {
            if (!IsOpenDir) {
                IsOpenDir = true;
                lnrDirectionsInner.setVisibility(View.VISIBLE);
            } else {
                IsOpenDir = false;
                lnrDirectionsInner.setVisibility(View.GONE);
            }
        });
        lnrNutrition.setOnClickListener(v -> {
            if (!IsOpenNut) {
                IsOpenNut = true;
                lnrNutritionInner.setVisibility(View.VISIBLE);
            } else {
                IsOpenNut = false;
                lnrNutritionInner.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
