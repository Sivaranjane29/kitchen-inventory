/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.shopthings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.shop.ShopAdd;
import com.example.kitcheninventory.adaptor.MarketAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.shop.MShop;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class MarketList extends AppCompatActivity {
    RecyclerView mRecycleView;
    MarketAdaptor mAdapter;
    List<MShop> mShopList;
    FloatingActionButton fabShopAdd;
    LinearLayout lnrLayoutEmpty;
    TextView txtErrorMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_item);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shops");

        txtErrorMessage = findViewById(R.id.txtErrorMessage);
        mRecycleView = findViewById(R.id.recycleShopItemList);
        lnrLayoutEmpty = findViewById(R.id.lnrLayoutEmpty);
        fabShopAdd = findViewById(R.id.fabShopAdd);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        fabShopAdd.setOnClickListener(v -> {
            startActivity(new Intent(MarketList.this, ShopAdd.class));
        });

        new FetchShops().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class FetchShops extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mShopList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mShop_dao()
                        .getAll();

            } catch (Exception e) {
                IsError = true;
                strError = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mShopList.size() != 0) {
                loadDataList(mShopList);
                lnrLayoutEmpty.setVisibility(View.GONE);
            } else {
                mRecycleView.setVisibility(View.GONE);
                lnrLayoutEmpty.setVisibility(View.VISIBLE);
                txtErrorMessage.setText("No Shops Found");
            }
        }
    }

    private void loadDataList(List<MShop> mShopList) {
        mAdapter = new MarketAdaptor(MarketList.this, mShopList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MarketList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchShops().execute();
    }
}
