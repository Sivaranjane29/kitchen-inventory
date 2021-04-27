/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.cat;

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
import com.example.kitcheninventory.db.master.MCategory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddCategory extends AppCompatActivity {

    TextInputEditText txtCategoryName, txtCategoryDescription;
    String strCategoryName, strDescription;
    private CommonUtils mUtils;
    MaterialButton btnClear, btnSave;
    private int CategoryID = 0;
    MCategory mCategory;
    boolean IsSupplierNameExists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mUtils = new CommonUtils(AddCategory.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtCategoryDescription = findViewById(R.id.txtCategoryDescription);
        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                CategoryID = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            CategoryID = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if (CategoryID > 0) {
            GetCategory();
            getSupportActionBar().setTitle("Edit category");
        } else {
            getSupportActionBar().setTitle("Add category");
        }

        btnSave.setOnClickListener(v -> {
            if (Validate()) {
                SaveCategory();
            }
        });

        btnClear.setOnClickListener(v -> {
            txtCategoryName.setText("");
            txtCategoryDescription.setText("");
        });
    }

    private void SaveCategory() {
        class SaveCategory extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    if (CategoryID == 0) {
                        mCategory = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mCategory_dao()
                                .getByName(strCategoryName);
                    } else {
                        mCategory = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mCategory_dao()
                                .getNameOtherThanId(strCategoryName, CategoryID);
                    }

                    if (mCategory == null) {

                        mCategory = new MCategory();
                        mCategory.setCategoryName(strCategoryName);
                        mCategory.setDescription(strDescription);
                        mCategory.setServerId(0);
                        mCategory.setBranchId(0);
                        mCategory.setPosted(false);
                        mCategory.setPostedDt("");
                        mCategory.setActive(true);
                        mCategory.setAddedOn("");
                        mCategory.setAddedBy("");
                        mCategory.setModifiedOn("");
                        mCategory.setModifiedBy("");
                        mCategory.setDeleted(false);
                        mCategory.setDeletedOn("");
                        mCategory.setDeletedBy("");

//                        if (IsActive) {
//                            mSupplier.setActive(true);
//                        } else {
//                            mSupplier.setActive(true);
//                        }

                        if (CategoryID == 0) {
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mCategory_dao()
                                    .insert(mCategory);
                        } else {
                            mCategory.setCategoryId(CategoryID);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mCategory_dao()
                                    .update(mCategory);
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
                    mUtils.showError("Category name already exists, Try with another name");
                    IsSupplierNameExists = false;
                } else {
                    if (CategoryID > 0) {
                        mUtils.showSuccess("Category updated");
                        CategoryID = 0;
                        finish();
                    } else {
                        mUtils.showSuccess("Category added");
                    }

                    txtCategoryName.setText("");
                    txtCategoryDescription.setText("");
                }
            }
        }
        SaveCategory st = new SaveCategory();
        st.execute();
    }

    private boolean Validate() {
        boolean ReturnValue = true;

        strCategoryName = Objects.requireNonNull(txtCategoryName.getText()).toString().trim();
        if (strCategoryName.length() < 1) {
            txtCategoryName.setError("Invalid category name");
            ReturnValue = false;
        }

        strDescription = Objects.requireNonNull(txtCategoryDescription.getText()).toString().trim();
        if (strDescription.length() < 1) {
            strDescription = "";
        }

        return ReturnValue;
    }

    private void GetCategory() {
        class FetchCategory extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    mCategory = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mCategory_dao()
                            .getById(CategoryID);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                txtCategoryDescription.setText(mCategory.getDescription());
                txtCategoryName.setText(mCategory.getCategoryName());

//                if (mSupplier.getActive() == 1) {
//                    cbActive.setChecked(true);
//                    IsActive = true;
//                } else {
//                    cbActive.setChecked(false);
//                    IsActive = false;
//                }
            }
        }
        new FetchCategory().execute();
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
