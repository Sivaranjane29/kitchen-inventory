/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.shopthings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.ShopItemAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.cart.MCart;
import com.example.kitcheninventory.utils.CommonUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class ShopItem extends AppCompatActivity {
    RecyclerView mRecycleView;
    ShopItemAdaptor mAdapter;
    List<MCart> cartList;
    MaterialButton btnRemindMe;
    CommonUtils mUtils;
    FloatingActionButton fabShopAdd;
    LinearLayout lnrLayoutEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_item);

        mUtils = new CommonUtils(ShopItem.this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart List");

        lnrLayoutEmpty = findViewById(R.id.lnrLayoutEmpty);
        btnRemindMe = findViewById(R.id.btnRemindMe);
        fabShopAdd = findViewById(R.id.fabShopAdd);
        mRecycleView = findViewById(R.id.recycleShopItemList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        btnRemindMe.setOnClickListener(v -> {
            mUtils.showSuccess("Set Reminder");
            finish();
        });

        new FetchCart().execute();
    }

    private void loadDataList(List<MCart> LIST) {
        mAdapter = new ShopItemAdaptor(ShopItem.this, LIST);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShopItem.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    class FetchCart extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                cartList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mCart_dao()
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
            if (cartList.size() != 0) {
                lnrLayoutEmpty.setVisibility(View.GONE);
                mRecycleView.setVisibility(View.VISIBLE);
                loadDataList(cartList);
            } else {
                lnrLayoutEmpty.setVisibility(View.VISIBLE);
                mRecycleView.setVisibility(View.GONE);
            }
        }
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
