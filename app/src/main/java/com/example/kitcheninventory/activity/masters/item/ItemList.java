/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.item;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.model.MRowItemList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.PrefManager;
import com.example.kitcheninventory.adaptor.ItemAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemList extends AppCompatActivity {

    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MRowItemList> mItemList = new ArrayList<>();
    ItemAdaptor mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Items");

        mUtils = new CommonUtils(ItemList.this);
        mPref = new PrefManager(ItemList.this);

        mRecycleView = findViewById(R.id.recycleItemList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddCus = findViewById(R.id.fabItemAdd);
        fabAddCus.setOnClickListener(v -> {
            startActivity(new Intent(ItemList.this, AddItem.class));
        });

        //fetchItems
        new fetchItems().execute();
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

    class fetchItems extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mItem_dao()
                        .getAllItems();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mItemList.size() == 0) {
                mUtils.showInfo("No item Found");
            }
            loadDataList(mItemList);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void loadDataList(List<MRowItemList> mList) {
        mAdapter = new ItemAdaptor(ItemList.this, mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ItemList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new fetchItems().execute();
    }
}
