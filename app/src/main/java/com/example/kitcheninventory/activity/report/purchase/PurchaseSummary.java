/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.report.purchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.PurchaseSummaryAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MPurchaseSummaryList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.MyHelper;
import com.example.kitcheninventory.utils.PrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class PurchaseSummary extends AppCompatActivity {
    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MPurchaseSummaryList> mSummaryList = new ArrayList<>();
    PurchaseSummaryAdaptor mAdapter;
    String strFromDt, strTxtFrom;
    String strToDt, strTxtTo;

    Calendar mCalendarFrmDt = Calendar.getInstance();
    Calendar mCalendarToDt = Calendar.getInstance();
    int mYear1, mMonth1, mDay1;
    int mYear2, mMonth2, mDay2;

    TextView txtFrmDt, txtToDt;
    Button btnReset, btnApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summarylist);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Purchase Report");

        mUtils = new CommonUtils(PurchaseSummary.this);
        mPref = new PrefManager(PurchaseSummary.this);

        mRecycleView = findViewById(R.id.recyclePurchaseList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        strTxtFrom = MyHelper.getCurrentDateForView();
        strTxtTo = MyHelper.getCurrentDateForView();

        strFromDt = MyHelper.getCurrentDateForDatabase();
        strToDt = MyHelper.getCurrentDateForDatabase();

        new FetchSummary().execute();
    }

    class FetchSummary extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mSummaryList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mPurchaseSummary_dao()
                        .getPurchaseSummary(strFromDt, strToDt);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mSummaryList.size() == 0) {
                mUtils.showInfo("No report Found");
            }
            loadDataList(mSummaryList);
        }
    }

    private void loadDataList(List<MPurchaseSummaryList> mSummaryList) {
        mAdapter = new PurchaseSummaryAdaptor(PurchaseSummary.this, mSummaryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PurchaseSummary.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.app_bar_filter:
                ShowDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    private void ShowDialog() {

        Dialog dialog = new Dialog(PurchaseSummary.this);
        dialog.setContentView(R.layout.dialog_filter);

        txtFrmDt = dialog.findViewById(R.id.txtFrmDt);
        txtToDt = dialog.findViewById(R.id.txtToDt);

        btnReset = dialog.findViewById(R.id.btnReset);
        btnApply = dialog.findViewById(R.id.btnApply);

        SetDefaults();

        txtFrmDt.setOnClickListener(v -> ShowFromDate());
        txtToDt.setOnClickListener(v -> ShowToDate());

        btnReset.setOnClickListener(v -> dialog.dismiss());
        btnApply.setOnClickListener(v -> {

            new FetchSummary().execute();
            dialog.dismiss();
        });

        dialog.show();

    }

    void SetDefaults() {
        Calendar mCalendar = Calendar.getInstance();

        strFromDt = MyHelper.getCurrentDateForDatabase();
        strToDt = MyHelper.getCurrentDateForDatabase();

        txtFrmDt.setText(MyHelper.getDateForViewCalendar(mCalendar));
        txtToDt.setText(MyHelper.getDateForViewCalendar(mCalendar));
    }

    void ShowFromDate() {

        mYear1 = mCalendarFrmDt.get(Calendar.YEAR);
        mMonth1 = mCalendarFrmDt.get(Calendar.MONTH);
        mDay1 = mCalendarFrmDt.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseSummary.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mCalendarFrmDt.set(year, monthOfYear, dayOfMonth);
                    strTxtFrom = MyHelper.getDateForViewCalendar(mCalendarFrmDt);
                    txtFrmDt.setText(strTxtFrom);
                    strFromDt = MyHelper.getDateForDatabase(mCalendarFrmDt);
                }, mYear1, mMonth1, mDay1);
        datePickerDialog.show();
    }

    void ShowToDate() {

        mYear2 = mCalendarToDt.get(Calendar.YEAR);
        mMonth2 = mCalendarToDt.get(Calendar.MONTH);
        mDay2 = mCalendarToDt.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseSummary.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mCalendarToDt.set(year, monthOfYear, dayOfMonth);
                    strTxtTo = MyHelper.getDateForViewCalendar(mCalendarToDt);
                    txtToDt.setText(strTxtTo);
                    strToDt = MyHelper.getDateForDatabase(mCalendarToDt);
                }, mYear2, mMonth2, mDay2);
        datePickerDialog.show();
    }


}