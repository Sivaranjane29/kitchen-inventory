/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.report.purchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.PurchaseSummaryAdaptor;
import com.example.kitcheninventory.adaptor.Re_purchase_detail_adaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MPurchaseSummaryList;
import com.example.kitcheninventory.model.MRePurchaseDetails;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Re_PurchaseDetails extends AppCompatActivity {
    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MRePurchaseDetails> mDetailsList = new ArrayList<>();
    Re_purchase_detail_adaptor mAdapter;
    long lngSummaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Purchase Report");

        mUtils = new CommonUtils(Re_PurchaseDetails.this);
        mPref = new PrefManager(Re_PurchaseDetails.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                lngSummaryId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            lngSummaryId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        mRecycleView = findViewById(R.id.recycleDetailsList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        new FetchItemDetails().execute();
    }

    class FetchItemDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mDetailsList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mPurchaseDetails_dao()
                        .getPurchaseDetailsById(lngSummaryId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mDetailsList.size() == 0) {
                mUtils.showInfo("No report Found");
            }
            loadDataList(mDetailsList);
        }
    }

    private void loadDataList(List<MRePurchaseDetails> mDetailsList) {
        mAdapter = new Re_purchase_detail_adaptor(Re_PurchaseDetails.this, mDetailsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Re_PurchaseDetails.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}