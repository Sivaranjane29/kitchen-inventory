/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.report;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.daily.DailyRecipe;
import com.example.kitcheninventory.activity.report.purchase.PurchaseSummary;
import com.example.kitcheninventory.adaptor.ViewPagerAdapter;
import com.example.kitcheninventory.fragment.ItemFragment;
import com.example.kitcheninventory.fragment.RecipeFragment;
import com.example.kitcheninventory.utils.MyHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Objects;

public class DailyReport extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter mAdaptor;

    public String strFromDt, strTxtFrom;
    public String strToDt, strTxtTo;

    Calendar mCalendarFrmDt = Calendar.getInstance();
    Calendar mCalendarToDt = Calendar.getInstance();
    int mYear1, mMonth1, mDay1;
    int mYear2, mMonth2, mDay2;

    public TextView txtFrmDt, txtToDt, txt_reload;
    Button btnReset, btnApply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daily Report");

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        txt_reload = findViewById(R.id.txt_reload);

        strFromDt = MyHelper.getCurrentDateForDatabase();
        strToDt = MyHelper.getCurrentDateForDatabase();

        mAdaptor = new ViewPagerAdapter(getSupportFragmentManager());
        mAdaptor.addFragment(new RecipeFragment(), "Recipe");
        mAdaptor.addFragment(new ItemFragment(), "Item");
        viewPager.setAdapter(mAdaptor);
        tabLayout.setupWithViewPager(viewPager);
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

    @SuppressLint("SetTextI18n")
    private void ShowDialog() {

        Dialog dialog = new Dialog(DailyReport.this);
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

            txt_reload.setText("Load");
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(DailyReport.this,
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(DailyReport.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    mCalendarToDt.set(year, monthOfYear, dayOfMonth);
                    strTxtTo = MyHelper.getDateForViewCalendar(mCalendarToDt);
                    txtToDt.setText(strTxtTo);
                    strToDt = MyHelper.getDateForDatabase(mCalendarToDt);
                }, mYear2, mMonth2, mDay2);
        datePickerDialog.show();
    }

}
