/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.dailyconsumption;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.item.AddItem;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.activity.masters.menu.MenuList;
import com.example.kitcheninventory.activity.purchase.PurchaseDetails;
import com.example.kitcheninventory.adaptor.ConsumptionAdaptor;
import com.example.kitcheninventory.adaptor.PurchaseListAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.daily.MDailyItem;
import com.example.kitcheninventory.db.history.MHistory;
import com.example.kitcheninventory.db.master.purchase.MPurchaseDetails;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.model.MPurchaseList;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.model.MRecipeStock;
import com.example.kitcheninventory.model.QuantityDetails;
import com.example.kitcheninventory.model.StockConsumption;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.utils.MyHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DailyConsumption extends AppCompatActivity {
    Spinner spItem;
    CommonUtils mUtils;

    int[] arrItemId;
    ArrayAdapter ItemSpinnerAdapter;
    private int intItemId;
    String strQty, strItemName, strUnitName, strCategoryName;

    double dblQty, dblCalorie, UpdateStockQty = 0;
    RecyclerView mRecycleView;
    EditText txtUnitName, txtQty;
    MaterialButton btnAdd, btnSave;
    ConsumptionAdaptor mAdapter;
    List<MPurchaseList> mItemList = new ArrayList<>();
    MPurchaseRow mRowItem;
    List<MPurchaseRow> mRowItemList = new ArrayList<>();
    MStock mStock;
    MHistory mHistory;
    StockConsumption mItemStock;
    MDailyItem mDailyItem;
    TextView txtAddItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quick Consumption");

        mUtils = new CommonUtils(DailyConsumption.this);

        txtUnitName = findViewById(R.id.txtUnitName);
        txtQty = findViewById(R.id.txtQty);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        spItem = findViewById(R.id.spItem);
        txtAddItem = findViewById(R.id.txtAddItem);

        mRecycleView = findViewById(R.id.recycleItems);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));


        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (ItemSpinnerAdapter.getCount() > 0) {
                    int intPosition = spItem.getSelectedItemPosition();
                    intItemId = arrItemId[intPosition];
                    strItemName = spItem.getSelectedItem().toString();
                    dblCalorie = mItemList.get(intPosition).getCalorie();
                    strUnitName = mItemList.get(intPosition).getUnitName();
                    strCategoryName = mItemList.get(intPosition).getCategoryName();
                    txtUnitName.setText(mItemList.get(intPosition).UnitName);
                } else {
                    mUtils.showError("No item found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btnAdd.setOnClickListener(v -> {
            if (Validate()) {
                AddPurchaseRow();
            }
        });

        btnSave.setOnClickListener(v -> {
            if (mRowItemList.size() > 0) {
                new UpdateStock().execute();
                new UpdateItemReport().execute();
            } else {
                mUtils.showError("Empty list");
            }
        });

        txtAddItem.setOnClickListener(v -> {
            startActivity(new Intent(DailyConsumption.this, AddItem.class));
        });

        LoadItemList(mRowItemList);
        new fetchItems().execute();
    }

    private void LoadItemList(List<MPurchaseRow> mRowItemList) {
        mAdapter = new ConsumptionAdaptor(DailyConsumption.this, mRowItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DailyConsumption.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }


    private boolean Validate() {
        boolean ReturnValue = true;

        strQty = Objects.requireNonNull(txtQty.getText()).toString().trim();
        if (strQty.length() == 0) {
            txtQty.setError("Enter Qty");
            ReturnValue = false;
        } else {
            dblQty = Double.parseDouble(strQty);
        }

        if (ItemSpinnerAdapter.getCount() > 0) {
            int intPosition = spItem.getSelectedItemPosition();
            intItemId = arrItemId[intPosition];
        } else {
            mUtils.showError("No item found please add item to continue");
            return false;
        }

        if (intItemId < 1) {
            mUtils.showError("Invalid item");
            return false;
        }

        return ReturnValue;
    }


    class fetchItems extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mItem_dao()
                        .getAllItemsForPurchase();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mItemList.size() == 0) {
                btnAdd.setEnabled(false);
                btnSave.setEnabled(false);
                mUtils.showInfo("No items Found");
            } else {
                int ListSize = mItemList.size();
                String[] items = new String[ListSize];
                arrItemId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MPurchaseList row = mItemList.get(i);
                    arrItemId[i] = (int) row.getItemId();
                    items[i] = String.valueOf(row.getItemName());
                }

                ItemSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spItem.setAdapter(ItemSpinnerAdapter);
                int intPosition = spItem.getSelectedItemPosition();
                strItemName = spItem.getSelectedItem().toString();
                dblCalorie = mItemList.get(intPosition).getCalorie();
                strUnitName = mItemList.get(intPosition).getUnitName();
                txtUnitName.setText(mItemList.get(intPosition).UnitName);
                strCategoryName = mItemList.get(intPosition).getCategoryName();
            }
        }

    }

    private void AddPurchaseRow() {
        mRowItem = new MPurchaseRow();
        mRowItem.setUnitName(strUnitName);
        mRowItem.setItemName(strItemName);
        mRowItem.setItemId(intItemId);
        mRowItem.setCalorie(dblCalorie);
        mRowItem.setQty(dblQty);

        mRowItemList.add(mRowItem);
        txtQty.setText("");
        mAdapter.notifyDataSetChanged();
    }

    public void RemoveItem(int intPosition) {
        mRowItemList.remove(intPosition);
        mAdapter.notifyItemRemoved(intPosition);
    }

    class UpdateStock extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for (int i = 0; i < mRowItemList.size(); i++) {
                    mItemStock = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mItem_dao()
                            .getItemStock(mRowItemList.get(i).getItemId());

                    UpdateStockQty = mItemStock.getQuantity() - mRowItemList.get(i).getQty();

                    mStock = new MStock();
                    mStock.setItemId(mItemStock.getItemId());
                    mStock.setQuantity(UpdateStockQty);
                    mStock.setBranchId(0);
                    mStock.setServerId(0);
                    mStock.setPosted(false);
                    mStock.setPostedDt("");
                    mStock.setAddedOn("");
                    mStock.setAddedBy("");
                    mStock.setModifiedBy("");
                    mStock.setModifiedOn("");
                    mStock.setDeleted(false);
                    mStock.setDeletedBy("");
                    mStock.setDeletedOn("");

                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mStock_dao()
                            .updateStatus(mStock.getItemId(), mStock.getQuantity());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new SaveHistory().execute();
        }

    }

    class SaveHistory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for (int i = 0; i < mRowItemList.size(); i++) {

                    mHistory = new MHistory();
                    mHistory.setItemId(mRowItemList.get(i).getItemId());
                    mHistory.setQuantity(mRowItemList.get(i).getQty());
                    mHistory.setRecipeId(0);
                    mHistory.setIncrease(false);
                    mHistory.setDecrease(true);
                    mHistory.setRecipe(false);
                    mHistory.setBranchId(0);
                    mHistory.setServerId(0);
                    mHistory.setPosted(false);
                    mHistory.setPostedDt("");
                    mHistory.setAddedOn(MyHelper.getCurrentDateForDatabase());
                    mHistory.setAddedBy("");
                    mHistory.setModifiedBy("");
                    mHistory.setModifiedOn("");
                    mHistory.setDeleted(false);
                    mHistory.setDeletedBy("");
                    mHistory.setDeletedOn("");

                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mHistory_dao()
                            .insert(mHistory);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mUtils.showSuccess("Stock Update");
        }
    }

    class UpdateItemReport extends AsyncTask<Void, Void, Void> {
        double dblRItemQty = 0, UpdateDailyQty = 0;
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                for (int i = 0; i < mRowItemList.size(); i++) {
                    mDailyItem = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mDailyItem_dao()
                            .getById(mRowItemList.get(i).getItemId(), MyHelper.getCurrentDateForDatabase());

                    if (mDailyItem == null) {
                        mDailyItem = new MDailyItem();
                        mDailyItem.setItemId(mRowItemList.get(i).getItemId());
                        mDailyItem.setRecipeId(0);
                        mDailyItem.setQuantity(mRowItemList.get(i).getQty());
                        mDailyItem.setRecipe(false);
                        mDailyItem.setBranchId(0);
                        mDailyItem.setServerId(0);
                        mDailyItem.setPosted(false);
                        mDailyItem.setPostedDt("");
                        mDailyItem.setAddedOn(MyHelper.getCurrentDateForDatabase());
                        mDailyItem.setAddedBy("");
                        mDailyItem.setModifiedBy("");
                        mDailyItem.setModifiedOn("");
                        mDailyItem.setDeleted(false);
                        mDailyItem.setDeletedBy("");
                        mDailyItem.setDeletedOn("");

                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mDailyItem_dao()
                                .insert(mDailyItem);

                    } else {
                        dblRItemQty = mDailyItem.getQuantity();
                        UpdateDailyQty = dblRItemQty + mRowItemList.get(i).getQty();
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mDailyItem_dao()
                                .UpdateById(mRowItemList.get(i).getItemId(), MyHelper.getCurrentDateForDatabase(), UpdateDailyQty);
                    }
                }

            } catch (Exception e) {
                IsError = true;
                strError = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!IsError) {
                mUtils.showSuccess("Save item report");
                finish();
            } else {
                mUtils.showError(strError);
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

    @Override
    protected void onResume() {
        super.onResume();
        new fetchItems().execute();
    }
}
