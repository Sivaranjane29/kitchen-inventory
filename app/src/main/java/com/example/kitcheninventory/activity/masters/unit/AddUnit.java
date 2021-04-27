/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.unit;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MUnit;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddUnit extends AppCompatActivity {
    TextInputEditText txtUnitName;
    MaterialButton btnClear, btnSave;
    String strUnitName;
    private CommonUtils mUtils;
    private int intUnitId;
    MUnit mUnit;
    boolean IsSupplierNameExists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        mUtils = new CommonUtils(AddUnit.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        txtUnitName = findViewById(R.id.txtUnitName);

        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                intUnitId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            intUnitId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if (intUnitId > 0) {
            getUnit();
            getSupportActionBar().setTitle("Edit unit");
        } else {
            getSupportActionBar().setTitle("Add unit");
        }

        btnSave.setOnClickListener(v -> {
            if (Validate()) {
                SaveUnit();
            }
        });

        btnClear.setOnClickListener(v -> {
            txtUnitName.setText("");
        });

    }

    private void SaveUnit() {
        class SaveUnit extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    if (intUnitId == 0) {
                        mUnit = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mUnit_dao()
                                .getByName(strUnitName);
                    } else {
                        mUnit = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mUnit_dao()
                                .getNameOtherThanId(strUnitName, intUnitId);
                    }

                    if (mUnit == null) {

                        mUnit = new MUnit();
                        mUnit.setUnitName(strUnitName);
                        mUnit.setServerId(0);
                        mUnit.setBranchId(0);
                        mUnit.setPosted(false);
                        mUnit.setPostedDt("");
                        mUnit.setActive(true);
                        mUnit.setAddedOn("");
                        mUnit.setAddedBy("");
                        mUnit.setModifiedOn("");
                        mUnit.setModifiedBy("");
                        mUnit.setDeleted(false);
                        mUnit.setDeletedOn("");
                        mUnit.setDeletedBy("");


//                        if (IsActive) {
//                            mSupplier.setActive(true);
//                        } else {
//                            mSupplier.setActive(true);
//                        }

                        if (intUnitId == 0) {
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mUnit_dao()
                                    .insert(mUnit);
                        } else {
                            mUnit.setUnitId(intUnitId);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mUnit_dao()
                                    .update(mUnit);
                        }

                    } else {
                        IsSupplierNameExists = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (IsSupplierNameExists) {
                    mUtils.showError("Unit name already exists, Try with another name");
                    IsSupplierNameExists = false;
                } else {
                    if (intUnitId > 0) {
                        mUtils.showSuccess("Unit updated");
                        intUnitId = 0;
                        finish();
                    } else {
                        mUtils.showSuccess("Unit added");
                    }

                    txtUnitName.setText("");
                }
            }
        }
        new SaveUnit().execute();
    }

    private void getUnit() {
        class FetchUnit extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    mUnit = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mUnit_dao()
                            .getById(intUnitId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                txtUnitName.setText(mUnit.getUnitName());

//                if (mSupplier.getActive() == 1) {
//                    cbActive.setChecked(true);
//                    IsActive = true;
//                } else {
//                    cbActive.setChecked(false);
//                    IsActive = false;
//                }
            }
        }
        new FetchUnit().execute();

    }

    private boolean Validate() {
        boolean ReturnValue = true;

        strUnitName = Objects.requireNonNull(txtUnitName.getText()).toString().trim();
        if (strUnitName.length() < 1) {
            txtUnitName.setError("Invalid unit name");
            ReturnValue = false;
        }
        return ReturnValue;
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
