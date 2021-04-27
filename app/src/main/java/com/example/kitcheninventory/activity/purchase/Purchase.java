/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.purchase;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.sup.AddSupplier;
import com.example.kitcheninventory.activity.masters.sup.SupplierList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.utils.MyHelper;
import com.example.kitcheninventory.utils.PrefManager;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MSupplier;
import com.example.kitcheninventory.db.master.purchase.MPurchaseSummary;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Purchase extends AppCompatActivity {
    Spinner spSupplier;
    MPurchaseSummary mSummary;

    List<MSupplier> mSupplierList = new ArrayList<>();
    int[] arrSupplierId;
    ArrayAdapter SupplierSpinnerAdapter;
    private int SupplierId;

    CommonUtils mUtils;
    PrefManager mPref;
    TextInputEditText txtSerialNo, txtVoucherNo;
    TextView txtDate, txtAddSupplier;
    String strVoucherNo, strSerialNo;
    MaterialButton btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_summary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Purchase Summary");

        mUtils = new CommonUtils(Purchase.this);
        mPref = new PrefManager(Purchase.this);

        txtSerialNo = findViewById(R.id.txtSerialNo);
        txtVoucherNo = findViewById(R.id.txtVoucherNo);
        txtDate = findViewById(R.id.txtDate);
        txtAddSupplier = findViewById(R.id.txtAddSupplier);
        btnContinue = findViewById(R.id.btnContinue);
        spSupplier = findViewById(R.id.spSupplier);

        spSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (SupplierSpinnerAdapter.getCount() > 0) {
                    int intSupPosition = spSupplier.getSelectedItemPosition();
                    SupplierId = arrSupplierId[intSupPosition];
                } else {
                    mUtils.showError("No supplier found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btnContinue.setOnClickListener(v -> {
            if (Validate()) {
                new SaveSummary().execute();
            }
        });

        txtAddSupplier.setOnClickListener(v -> {
            startActivity(new Intent(Purchase.this, AddSupplier.class));
        });

        txtDate.setText(MyHelper.getCurrentDateForView());
        txtSerialNo.setText(String.valueOf(mPref.getSerialNumber()));
        new fetchSuppliers().execute();
    }

    class SaveSummary extends AsyncTask<Void, Void, Void> {
        long SummaryID = 0;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mSummary = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mPurchaseSummary_dao()
                        .getDuplicate(strSerialNo, SupplierId);

                if (mSummary == null) {

                    mSummary = new MPurchaseSummary();
                    mSummary.setSerialNumber(mPref.getSerialNumber());
                    mSummary.setVoucherNumber(strVoucherNo);
                    mSummary.setSupplierId(SupplierId);
                    mSummary.setPurchaseDate(MyHelper.getCurrentDateForDatabase());
                    mSummary.setBranchId(0);
                    mSummary.setServerId(0);
                    mSummary.setPosted(false);
                    mSummary.setPostedDt("");
                    mSummary.setAddedOn("");
                    mSummary.setAddedBy("");
                    mSummary.setModifiedBy("N");
                    mSummary.setModifiedOn("No");
                    mSummary.setPostedDt("");
                    mSummary.setDeleted(false);
                    mSummary.setDeletedBy("true");
                    mSummary.setDeletedOn("");

                    SummaryID = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .mPurchaseSummary_dao()
                            .insert(mSummary);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (SummaryID > 0) {
                mPref.incrementPurchaseSerial();
                mUtils.showSuccess("Purchase summary saved");
                Intent OpenItem = new Intent(Purchase.this, PurchaseDetails.class);
                OpenItem.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(SummaryID));
                startActivity(OpenItem);
                finish();
            } else {
                mUtils.showError("Error while saving");
            }
        }
    }

    boolean Validate() {
        boolean ReturnValue = true;

        strVoucherNo = Objects.requireNonNull(txtVoucherNo.getText()).toString().trim();
        if (strVoucherNo.length() < 1) {
            txtVoucherNo.setError("Invalid voucher number");
            ReturnValue = false;
        }

        if (SupplierSpinnerAdapter.getCount() > 0) {
            int intPosition = spSupplier.getSelectedItemPosition();
            SupplierId = arrSupplierId[intPosition];
        } else {
            mUtils.showError("No supplier found please add supplier to continue");
            return false;
        }

        if (SupplierId < 1) {
            mUtils.showError("Invalid supplier");
            return false;
        }

        return ReturnValue;
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
                mUtils.showInfo("No suppliers Found please add before continue");
                btnContinue.setEnabled(false);
            } else {
                int ListSize = mSupplierList.size();
                String[] items = new String[ListSize];
                arrSupplierId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MSupplier row = mSupplierList.get(i);
                    arrSupplierId[i] = (int) row.getSupplierId();
                    items[i] = String.valueOf(row.getName());
                }

                SupplierSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spSupplier.setAdapter(SupplierSpinnerAdapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        new fetchSuppliers().execute();
    }
}