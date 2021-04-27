/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.report;

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
import com.example.kitcheninventory.adaptor.Re_ROL_Adapter;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.utils.CommonUtils;

import java.util.List;
import java.util.Objects;

public class RolReport extends AppCompatActivity {

    private CommonUtils mUtils;
    RecyclerView mRecycleView;
    List<MItem> mItemList;
    LinearLayout lnrLayoutEmpty;
    Re_ROL_Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ROL Report");

        mUtils = new CommonUtils(RolReport.this);

        lnrLayoutEmpty = findViewById(R.id.lnrLayoutEmpty);
        mRecycleView = findViewById(R.id.recycleReport);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        new fetchItemRol().execute();
    }

    class fetchItemRol extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mItem_dao()
                        .getRolAlert();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mItemList.size() == 0) {
                lnrLayoutEmpty.setVisibility(View.VISIBLE);
                mRecycleView.setVisibility(View.GONE);
                mUtils.showInfo("No report Found");
            } else {
                lnrLayoutEmpty.setVisibility(View.GONE);
                mRecycleView.setVisibility(View.VISIBLE);
                loadDataList(mItemList);
            }
        }
    }

    private void loadDataList(List<MItem> mItemList) {
        mAdapter = new Re_ROL_Adapter(RolReport.this, mItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RolReport.this);
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
