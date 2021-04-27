/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.cat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.PrefManager;
import com.example.kitcheninventory.adaptor.CategoryAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class CategoryList extends AppCompatActivity {

    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MCategory> mCategoryList;
    CategoryAdaptor mAdapter;
    FloatingActionButton fabAddCus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Category");

        mUtils = new CommonUtils(CategoryList.this);
        mPref = new PrefManager(CategoryList.this);

        mRecycleView = findViewById(R.id.recycleCategory);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        fabAddCus = findViewById(R.id.fabCategoryAdd);

        fabAddCus.setOnClickListener(v -> {
            startActivity(new Intent(CategoryList.this, AddCategory.class));
        });

        //fetchCategory
        new fetchCategory().execute();
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


    @Override
    public void onBackPressed() {
        finish();
    }

    class fetchCategory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mCategoryList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mCategory_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mCategoryList.size() == 0) {
                mUtils.showInfo("No category Found");
            }
            loadDataList(mCategoryList);
        }
    }


    private void loadDataList(List<MCategory> mList) {
        mAdapter = new CategoryAdaptor(CategoryList.this, mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new fetchCategory().execute();
    }
}