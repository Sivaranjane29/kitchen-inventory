/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.sup;

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
import com.example.kitcheninventory.adaptor.SupplierAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MSupplier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class SupplierList extends AppCompatActivity {

    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MSupplier> mSupplierList;
    SupplierAdaptor mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Supplier");

        mUtils = new CommonUtils(SupplierList.this);
        mPref = new PrefManager(SupplierList.this);

        mRecycleView = findViewById(R.id.recycleSupplier);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddCus = findViewById(R.id.fabSupplierAdd);
        fabAddCus.setOnClickListener(v -> {
            startActivity(new Intent(SupplierList.this, AddSupplier.class));
        });

        //fetchSupplier
        new fetchSuppliers().execute();
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

    class fetchSuppliers extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mSupplierList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mSupplier_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mSupplierList.size() == 0) {
                mUtils.showInfo("No suppliers Found");
            }
            loadDataList(mSupplierList);
        }
    }


    private void loadDataList(List<MSupplier> mList) {
        mAdapter = new SupplierAdaptor(SupplierList.this, mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SupplierList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new fetchSuppliers().execute();
    }
}