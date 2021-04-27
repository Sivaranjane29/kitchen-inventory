/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.item;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.cat.AddCategory;
import com.example.kitcheninventory.activity.masters.unit.AddUnit;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MCategory;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.db.master.MUnit;
import com.example.kitcheninventory.utils.MyHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddItem extends AppCompatActivity {

    List<MCategory> mCategoryList = new ArrayList<>();
    int[] arrCatId;
    ArrayAdapter categorySpinnerAdapter;
    int intCategoryPosition;

    List<MUnit> mUnitList = new ArrayList<>();
    int[] arrUnitId;
    ArrayAdapter UnitSpinnerAdapter;
    int intUnitPosition;

    Spinner spCategory, spUnit;
    private CommonUtils mUtils;
    private int ItemId, intCatId, intUnitId;
    long lngItemId;
    MItem mItem;
    TextView txtAddUnit, txtAddCategory;
    MaterialButton btnClear, btnSave;
    TextInputEditText txtItemName, txtDescription, txtCalorie, txtStock, txtRol;
    String strIteName = "", strDescription = "", strCalorie = "", strStock = "", strRol = "";
    boolean IsSupplierNameExists;
    int intRol = 0;
    MStock mGetStock;
    double dblStock = 0;
    CheckBox cbExpireDate;
    boolean blnExpireDate = false;
    Calendar mCalendarExpiryDt = Calendar.getInstance();
    int mYear1, mMonth1, mDay1;
    String strTxtExpiry, dbDate, FormattedDate;
    TextView txtExpiryDate;
    LinearLayout linearExpireDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mUtils = new CommonUtils(AddItem.this);

        txtItemName = findViewById(R.id.txtItemName);
        txtDescription = findViewById(R.id.txtDescription);
        txtCalorie = findViewById(R.id.txtCalorie);
        spCategory = findViewById(R.id.spCategory);
        spUnit = findViewById(R.id.spUnit);

        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        txtAddUnit = findViewById(R.id.txtAddUnit);
        txtAddCategory = findViewById(R.id.txtAddCategory);
        txtStock = findViewById(R.id.txtStock);
        txtRol = findViewById(R.id.txtRol);
        cbExpireDate = findViewById(R.id.cbExpireDate);
        txtExpiryDate = findViewById(R.id.txtExpiryDate);
        linearExpireDate = findViewById(R.id.linearExpireDate);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                ItemId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            ItemId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (ItemId > 0) {
            new FetchItem().execute();
            getSupportActionBar().setTitle("Edit Item");
        } else {
            getSupportActionBar().setTitle("Add Item");
        }

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (categorySpinnerAdapter.getCount() > 0) {
                    int intCatPosition = spCategory.getSelectedItemPosition();
                    intCatId = arrCatId[intCatPosition];
                } else {
                    mUtils.showError("No category found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (UnitSpinnerAdapter.getCount() > 0) {
                    int intUnitPosition = spUnit.getSelectedItemPosition();
                    intUnitId = arrUnitId[intUnitPosition];
                } else {
                    mUtils.showError("No unit found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        cbExpireDate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                linearExpireDate.setVisibility(View.VISIBLE);
            } else {
                linearExpireDate.setVisibility(View.GONE);
                dbDate = "";
            }
        });

        btnSave.setOnClickListener(v -> {
            if (Validate()) {
                SaveItem();
            }
        });

        btnClear.setOnClickListener(v -> {
            txtCalorie.setText("");
            txtDescription.setText("");
            txtItemName.setText("");
        });

        txtAddUnit.setOnClickListener(v -> {
            startActivity(new Intent(AddItem.this, AddUnit.class));
        });

        txtAddCategory.setOnClickListener(v -> {
            startActivity(new Intent(AddItem.this, AddCategory.class));
        });

        txtExpiryDate.setOnClickListener(v -> {
            mYear1 = mCalendarExpiryDt.get(Calendar.YEAR);
            mMonth1 = mCalendarExpiryDt.get(Calendar.MONTH);
            mDay1 = mCalendarExpiryDt.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddItem.this,
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        mCalendarExpiryDt.set(year, monthOfYear, dayOfMonth);
                        strTxtExpiry = MyHelper.getDateForViewCalendar(mCalendarExpiryDt);
                        txtExpiryDate.setText(strTxtExpiry);
                        dbDate = MyHelper.getDateForDatabase(mCalendarExpiryDt);
                    }, mYear1, mMonth1, mDay1);
            datePickerDialog.show();
        });

        txtExpiryDate.setText(MyHelper.getCurrentDateForView());

        new fetchCategory().execute();
        new fetchUnit().execute();
    }

    private void SaveItem() {

        class SaveItem extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    if (ItemId == 0) {
                        mItem = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mItem_dao()
                                .getByName(strIteName);
                    } else {
                        mItem = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mItem_dao()
                                .getNameOtherThanId(strIteName, ItemId);
                    }

                    if (mItem == null) {
                        mItem = new MItem();
                        mItem.setItemName(strIteName);
                        mItem.setDescription(strDescription);
                        mItem.setCalorie(Double.parseDouble(strCalorie));
                        mItem.setUnitId(intUnitId);
                        mItem.setCategoryId(intCatId);
                        mItem.setCurrentStock(dblStock);
                        mItem.setExpiryDate(dbDate);
                        mItem.setROL(intRol);
                        mItem.setServerId(0);
                        mItem.setBranchId(0);
                        mItem.setPosted(false);
                        mItem.setPostedDt("");
                        mItem.setActive(true);
                        mItem.setAddedOn("");
                        mItem.setAddedBy("");
                        mItem.setModifiedOn("");
                        mItem.setModifiedBy("");
                        mItem.setExpireCheck(blnExpireDate);
                        mItem.setDeleted(false);
                        mItem.setDeletedOn("");
                        mItem.setDeletedBy("");

                        if (ItemId == 0) {
                            lngItemId = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mItem_dao()
                                    .insert(mItem);
                        } else {
                            lngItemId = ItemId;
                            mItem.setItemId(ItemId);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mItem_dao()
                                    .update(mItem);
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
                    mUtils.showError("item name already exists, Try with another name");
                    IsSupplierNameExists = false;
                } else {
                    new fetchAllItems().execute();

                    txtCalorie.setText("");
                    txtDescription.setText("");
                    txtItemName.setText("");
                    spCategory.setSelection(0);
                    spUnit.setSelection(0);
                    txtRol.setText("0");
                    txtStock.setText("0");
                }
            }
        }
        new SaveItem().execute();
    }

    class fetchAllItems extends AsyncTask<Void, Void, Void> {
        boolean IsAdded = false;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItem = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mItem_dao()
                        .getById(lngItemId);

                mGetStock = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mStock_dao()
                        .getById(lngItemId);

                MStock mStock = new MStock();
                mStock.setItemId(mItem.getItemId());
                mStock.setQuantity(mItem.getCurrentStock());
                mStock.setBranchId(0);
                mStock.setServerId(0);
                mStock.setPosted(false);
                mStock.setPostedDt("");
                mStock.setAddedOn("");
                mStock.setAddedBy("");
                mStock.setModifiedBy("");
                mStock.setExpiryDate(mItem.getExpiryDate());
                mStock.setModifiedOn("");
                mStock.setDeleted(false);
                mStock.setExpireCheck(mItem.IsExpireCheck);
                mStock.setDeletedBy("");
                mStock.setDeletedOn("");

                if (mGetStock == null) {
                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mStock_dao()
                            .insert(mStock);
                    IsAdded = true;
                } else {
                    mStock.setStockId(mGetStock.getStockId());
                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mStock_dao()
                            .update(mStock);
                    IsAdded = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (IsAdded) {
                mUtils.showSuccess("Item added");
            } else {
                mUtils.showSuccess("Stock updated");
            }

            ItemId = 0;
            finish();
            super.onPostExecute(aVoid);
        }
    }

    class fetchCategory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mCategoryList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mCategory_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mCategoryList.size() == 0) {
                btnSave.setEnabled(false);
                mUtils.showInfo("No category Found");
            } else {
                int ListSize = mCategoryList.size();
                String[] items = new String[ListSize];
                arrCatId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MCategory row = mCategoryList.get(i);
                    arrCatId[i] = (int) row.getCategoryId();
                    items[i] = String.valueOf(row.getCategoryName());
                }
                if (intCatId > 0) {
                    for (int i = 0; i < ListSize; i++) {
                        if (intCatId == mCategoryList.get(i).getCategoryId()) {
                            intCategoryPosition = i;
                        }
                    }
                }

                categorySpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spCategory.setAdapter(categorySpinnerAdapter);
                spCategory.setSelection(intCategoryPosition);

            }
        }
    }

    class fetchUnit extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mUnitList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mUnit_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mUnitList.size() == 0) {
                btnSave.setEnabled(false);
                mUtils.showInfo("No unit Found");
            } else {
                int ListSize = mUnitList.size();
                String[] items = new String[ListSize];
                arrUnitId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MUnit rowUnit = mUnitList.get(i);
                    arrUnitId[i] = (int) rowUnit.getUnitId();
                    items[i] = String.valueOf(rowUnit.getUnitName());
                }
                if (intUnitId > 0) {
                    for (int i = 0; i < ListSize; i++) {
                        if (intUnitId == mUnitList.get(i).getUnitId()) {
                            intUnitPosition = i;
                        }
                    }
                }

                UnitSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spUnit.setAdapter(UnitSpinnerAdapter);
                spUnit.setSelection(intUnitPosition);

            }
        }
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

        strIteName = Objects.requireNonNull(txtItemName.getText()).toString().trim();
        if (strIteName.length() < 1) {
            txtItemName.setError("Invalid supplier name");
            ReturnValue = false;
        }

        strDescription = Objects.requireNonNull(txtDescription.getText()).toString().trim();
        if (strDescription.length() < 1) {
            strDescription = "";
        }

        strCalorie = Objects.requireNonNull(txtCalorie.getText()).toString().trim();
        if (strCalorie.length() == 0) {
            txtCalorie.setError("Invalid calorie");
            ReturnValue = false;
        }

        strStock = Objects.requireNonNull(txtStock.getText()).toString().trim();
        if (strStock.equals("")) {
            dblStock = 0;
        } else {
            dblStock = Double.parseDouble(strStock);
        }

        strRol = Objects.requireNonNull(txtRol.getText()).toString().trim();
        if (strRol.equals("")) {
            intRol = 0;
        } else {
            intRol = Integer.parseInt(strRol);
        }

        if (categorySpinnerAdapter.getCount() > 0) {
            int intCategoryPosition = spCategory.getSelectedItemPosition();
            intCatId = arrCatId[intCategoryPosition];
        } else {
            mUtils.showError("No category found please add category to continue");
            return false;
        }

        if (intCatId < 1) {
            mUtils.showError("Invalid Category");
            return false;
        }

        if (UnitSpinnerAdapter.getCount() > 0) {
            int intUnitPosition = spUnit.getSelectedItemPosition();
            intUnitId = arrUnitId[intUnitPosition];
        } else {
            mUtils.showError("No unit found please add unit to continue");
            return false;
        }

        if (intUnitId < 1) {
            mUtils.showError("Invalid Unit");
            return false;
        }

        blnExpireDate = cbExpireDate.isChecked();

        return ReturnValue;
    }

    class FetchItem extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItem = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mItem_dao()
                        .getById(ItemId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            txtItemName.setText(mItem.getItemName());
            txtDescription.setText(mItem.getDescription());
            txtCalorie.setText(String.valueOf(mItem.getCalorie()));
            txtStock.setText(String.valueOf(mItem.getCurrentStock()));
            txtRol.setText(String.valueOf(mItem.getROL()));
            cbExpireDate.setChecked(mItem.IsExpireCheck);
            txtExpiryDate.setText(mItem.getExpiryDate());

            intUnitId = mItem.getUnitId();
            intCatId = mItem.getCategoryId();

            new fetchCategory().execute();
            new fetchUnit().execute();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new fetchCategory().execute();
        new fetchUnit().execute();
    }
}
