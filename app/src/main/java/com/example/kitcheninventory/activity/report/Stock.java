/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.report;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.shopthings.ShopItem;
import com.example.kitcheninventory.adaptor.StockAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.fragment.BottomSheetFragment;
import com.example.kitcheninventory.model.StockRowList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stock extends AppCompatActivity {

    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<StockRowList> mStockList = new ArrayList<>();
    List<MStock> allList = new ArrayList<>();
    StockAdaptor mAdapter;
    public TextView txtListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inventory");

        mUtils = new CommonUtils(Stock.this);
        mPref = new PrefManager(Stock.this);

        txtListener = findViewById(R.id.txtListener);
        mRecycleView = findViewById(R.id.recycleStock);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        new fetchAllStock().execute();

        txtListener.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                new fetchAllStock().execute();
            }
        });
    }


    class fetchAllStock extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mStockList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mStock_dao()
                        .getAllStock();

                allList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mStock_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mStockList.size() == 0) {
                mUtils.showInfo("No stock Found");
            }
            loadDataList(mStockList);
        }
    }

    private void loadDataList(List<StockRowList> mStockList) {
        mAdapter = new StockAdaptor(Stock.this, mStockList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Stock.this);
        mRecycleView.setLayoutManager(layoutManager);
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
