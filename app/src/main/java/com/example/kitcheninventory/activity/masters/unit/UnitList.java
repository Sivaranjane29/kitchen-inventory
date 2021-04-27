/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.unit;

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
import com.example.kitcheninventory.adaptor.UnitAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MUnit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class UnitList extends AppCompatActivity {

    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MUnit> mUnitList;
    UnitAdaptor mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unitlist);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Unit");

        mUtils = new CommonUtils(UnitList.this);
        mPref = new PrefManager(UnitList.this);

        mRecycleView = findViewById(R.id.recycleUnit);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddCus = findViewById(R.id.fabUnitAdd);
        fabAddCus.setOnClickListener(v -> {
            startActivity(new Intent(UnitList.this, AddUnit.class));
        });

        //fetchCategory
        new fetchUnit().execute();
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

    class fetchUnit extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mUnitList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mUnit_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mUnitList.size() == 0) {
                mUtils.showInfo("No unit Found");
            }
            loadDataList(mUnitList);
        }
    }


    private void loadDataList(List<MUnit> mList) {
        mAdapter = new UnitAdaptor(UnitList.this, mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UnitList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new fetchUnit().execute();
    }

}
