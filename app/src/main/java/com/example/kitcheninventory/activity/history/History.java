/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.history;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.ViewPagerAdapter;
import com.example.kitcheninventory.fragment.ItemFragment;
import com.example.kitcheninventory.fragment.ItemHistoryFragment;
import com.example.kitcheninventory.fragment.RecipeFragment;
import com.example.kitcheninventory.fragment.RecipeHistoryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class History extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter mAdaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        mAdaptor = new ViewPagerAdapter(getSupportFragmentManager());
        mAdaptor.addFragment(new ItemHistoryFragment(), "Item");
        mAdaptor.addFragment(new RecipeHistoryFragment(), "Recipe");
        viewPager.setAdapter(mAdaptor);
        tabLayout.setupWithViewPager(viewPager);
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
