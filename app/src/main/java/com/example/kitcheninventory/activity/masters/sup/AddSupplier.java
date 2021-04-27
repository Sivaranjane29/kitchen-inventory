/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.sup;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MSupplier;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddSupplier extends AppCompatActivity {
    private CommonUtils mUtils;
    private int SupplierID;
    MSupplier mSupplier;
    MaterialButton btnClear, btnSave;
    TextInputEditText txtSupName, txtPhone1, txtPhone2, txtEmail, txtAddress, txtLocation;
    String strSupName, strPhone1, strPhone2, strEmail, strAddress, strLocation;
    boolean IsSupplierNameExists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        mUtils = new CommonUtils(AddSupplier.this);

        txtSupName = findViewById(R.id.txtSupName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone1 = findViewById(R.id.txtPhone1);
        txtPhone2 = findViewById(R.id.txtPhone2);
        txtLocation = findViewById(R.id.txtLocation);

        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                SupplierID = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            SupplierID = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (SupplierID > 0) {
            GetSupplier();
            getSupportActionBar().setTitle("Edit supplier");
        } else {
            getSupportActionBar().setTitle("Add supplier");
        }

        btnSave.setOnClickListener(v -> {
            if (Validate()) {
                AddSupplier();
            }
        });

        btnClear.setOnClickListener(v -> {
            txtSupName.setText("");
            txtPhone1.setText("");
            txtPhone2.setText("");
            txtEmail.setText("");
            txtLocation.setText("");
            txtAddress.setText("");
        });
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    boolean Validate() {
        boolean ReturnValue = true;

        strSupName = Objects.requireNonNull(txtSupName.getText()).toString().trim();
        if (strSupName.length() < 1) {
            txtSupName.setError("Invalid supplier name");
            ReturnValue = false;
        }

        strPhone1 = Objects.requireNonNull(txtPhone1.getText()).toString().trim().trim();
        if (strPhone1.length() < 1) {
            txtPhone1.setError("Invalid phone no");
            ReturnValue = false;
        }

        strPhone2 = Objects.requireNonNull(txtPhone2.getText()).toString().trim();
        if (strPhone2.length() < 1) {
            strPhone2 = "";
        }

        strEmail = Objects.requireNonNull(txtEmail.getText()).toString().trim();
        if (strEmail.length() < 1) {
            strEmail = "";
        }

        strAddress = Objects.requireNonNull(txtAddress.getText()).toString().trim();
        if (strAddress.length() < 1) {
            strAddress = "";
        }

        strLocation = Objects.requireNonNull(txtLocation.getText()).toString().trim();
        if (strLocation.length() < 1) {
            strLocation = "";
        }


        return ReturnValue;
    }

    private void AddSupplier() {

        class SaveSupplier extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    if (SupplierID == 0) {
                        mSupplier = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mSupplier_dao()
                                .getByName(strSupName);
                    } else {
                        mSupplier = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mSupplier_dao()
                                .getNameOtherThanId(strSupName, SupplierID);
                    }

                    if (mSupplier == null) {
                        mSupplier = new MSupplier();
                        mSupplier.setName(strSupName);
                        mSupplier.setPhoneOne(strPhone1);
                        mSupplier.setPhoneTwo(strPhone2);
                        mSupplier.setEmail(strEmail);
                        mSupplier.setAddress(strAddress);
                        mSupplier.setLocation(strLocation);
                        mSupplier.setLatitude("");
                        mSupplier.setLongitude("");
                        mSupplier.setServerId(0);
                        mSupplier.setBranchId(0);
                        mSupplier.setPosted(false);
                        mSupplier.setPostedDt("");
                        mSupplier.setActive(true);
                        mSupplier.setAddedOn("");
                        mSupplier.setAddedBy("");
                        mSupplier.setModifiedOn("");
                        mSupplier.setModifiedBy("");
                        mSupplier.setDeleted(false);
                        mSupplier.setDeletedOn("");
                        mSupplier.setDeletedBy("");


//                        if (IsActive) {
//                            mSupplier.setActive(true);
//                        } else {
//                            mSupplier.setActive(true);
//                        }

                        if (SupplierID == 0) {
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mSupplier_dao()
                                    .insert(mSupplier);
                        } else {
                            mSupplier.setSupplierId(SupplierID);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mSupplier_dao()
                                    .update(mSupplier);
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
                    mUtils.showError("Supplier name already exists, Try with another name");
                    IsSupplierNameExists = false;
                } else {
                    if (SupplierID > 0) {
                        mUtils.showSuccess("Supplier updated");
                        SupplierID = 0;
                        finish();
                    } else {
                        mUtils.showSuccess("Supplier added");
                    }

                    txtSupName.setText("");
                    txtPhone1.setText("");
                    txtPhone2.setText("");
                    txtEmail.setText("");
                    txtLocation.setText("");
                    txtAddress.setText("");
                }
            }
        }
        SaveSupplier st = new SaveSupplier();
        st.execute();
    }


    private void GetSupplier() {
        class FetchSupplier extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    mSupplier = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mSupplier_dao()
                            .getById(SupplierID);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                txtSupName.setText(mSupplier.getName());
                txtPhone1.setText(mSupplier.getPhoneOne());
                txtPhone2.setText(mSupplier.getPhoneTwo());
                txtEmail.setText(mSupplier.getEmail());
                txtLocation.setText(mSupplier.getLocation());
                txtAddress.setText(mSupplier.getAddress());

//                if (mSupplier.getActive() == 1) {
//                    cbActive.setChecked(true);
//                    IsActive = true;
//                } else {
//                    cbActive.setChecked(false);
//                    IsActive = false;
//                }
            }
        }
        FetchSupplier st = new FetchSupplier();
        st.execute();
    }
}


