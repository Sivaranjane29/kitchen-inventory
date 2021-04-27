/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.DashBoard;
import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.AddNewRecipe;
import com.example.kitcheninventory.activity.search.Search;
import com.example.kitcheninventory.adaptor.discover.For_uAdaptor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MyProfile extends AppCompatActivity {
    ArrayList<String> NameList = new ArrayList<>();
    For_uAdaptor mAdapter;
    RecyclerView mRecycleView;
    FloatingActionButton FabProfileAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myprofile);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        mRecycleView = findViewById(R.id.RecyclerMyRecipe);
        FabProfileAdd = findViewById(R.id.FabProfileAdd);

        NameList.add("French Toast Casserole");
        NameList.add("Egg and Sausage Casserole");
        NameList.add("French Toast");
        NameList.add("Egg Toast");

        FabProfileAdd.setOnClickListener(v -> {
            startActivity(new Intent(MyProfile.this, AddNewRecipe.class));
        });

        LoadRecipe(NameList);
    }

    private void LoadRecipe(ArrayList<String> nameList) {
        mAdapter = new For_uAdaptor(MyProfile.this, nameList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(MyProfile.this, LinearLayoutManager.HORIZONTAL, false));
        mRecycleView.setAdapter(mAdapter);
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
