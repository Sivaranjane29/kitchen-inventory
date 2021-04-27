/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.shop;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.shop.MShop;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.utils.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ShopAdd extends AppCompatActivity {

    TextInputEditText txtShopName, txtAddress;
    MaterialButton btnClear, btnSave;
    CommonUtils mUtils;
    int intShopID = 0;
    MShop mShop;
    String strShopName = "", strAddress = "";
    boolean IsShopNameExists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        mUtils = new CommonUtils(ShopAdd.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        txtShopName = findViewById(R.id.txtShopName);
        txtAddress = findViewById(R.id.txtAddress);

        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                intShopID = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            intShopID = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if (intShopID > 0) {
            new FetchShop().execute();
            getSupportActionBar().setTitle("Edit Shop");
        } else {
            getSupportActionBar().setTitle("Add Shop");
        }

        btnSave.setOnClickListener(v -> {
            if (Validate()) {
                new SaveShop().execute();
            }
        });

        btnClear.setOnClickListener(v -> {
            txtAddress.setText("");
            txtShopName.setText("");
        });

    }

    class SaveShop extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                if (intShopID == 0) {
                    mShop = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mShop_dao()
                            .getByName(strShopName);
                } else {
                    mShop = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mShop_dao()
                            .getNameOtherThanId(strShopName, intShopID);
                }

                if (mShop == null) {

                    mShop = new MShop();
                    mShop.setShopName(strShopName);
                    mShop.setAddress(strAddress);
                    mShop.setServerId(0);
                    mShop.setBranchId(0);
                    mShop.setPosted(false);
                    mShop.setPostedDt("");
                    mShop.setActive(true);
                    mShop.setAddedOn(MyHelper.getCurrentDateForDatabase());
                    mShop.setAddedBy("");
                    mShop.setModifiedOn(MyHelper.getCurrentDateForDatabase());
                    mShop.setModifiedBy("");
                    mShop.setDeleted(false);
                    mShop.setDeletedOn(MyHelper.getCurrentDateForDatabase());
                    mShop.setDeletedBy("");

                    if (intShopID == 0) {
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .mShop_dao()
                                .insert(mShop);
                    } else {
                        mShop.setShopID(intShopID);
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                .mShop_dao()
                                .update(mShop);
                    }

                } else {
                    IsShopNameExists = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (IsShopNameExists) {
                mUtils.showError("Shop name already exists, Try with another name");
                IsShopNameExists = false;
            } else {
                if (intShopID > 0) {
                    mUtils.showSuccess("Shop updated");
                    intShopID = 0;
                    finish();
                } else {
                    mUtils.showSuccess("Shop added");
                }

                txtShopName.setText("");
                txtAddress.setText("");
            }
        }
    }

    class FetchShop extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                mShop = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mShop_dao()
                        .getById(intShopID);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            txtAddress.setText(mShop.getAddress());
            txtShopName.setText(mShop.getShopName());

//                if (mSupplier.getActive() == 1) {
//                    cbActive.setChecked(true);
//                    IsActive = true;
//                } else {
//                    cbActive.setChecked(false);
//                    IsActive = false;
//                }
        }
    }

    private boolean Validate() {
        strShopName = Objects.requireNonNull(txtShopName.getText()).toString().trim();
        if (strShopName.length() < 1) {
            txtShopName.setError("Invalid shop name");
            return false;
        }

        strAddress = Objects.requireNonNull(txtAddress.getText()).toString().trim();
        if (strShopName.length() == 0) {
            strAddress = "";
        }
        return true;
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

}
