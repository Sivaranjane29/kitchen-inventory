/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.purchase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.db.history.MHistory;
import com.example.kitcheninventory.model.MPurchaseList;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.adaptor.PurchaseListAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.purchase.MPurchaseDetails;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.utils.MyHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseDetails extends AppCompatActivity {
    Spinner spItem;
    CommonUtils mUtils;

    int[] arrItemId;
    ArrayAdapter ItemSpinnerAdapter;
    private int intItemId, intSummmaryId;
    String strQty, strItemName, strUnitName, strCategoryName;

    double dblQty, dblCalorie, UpdateStockQty = 0;
    RecyclerView mRecycleView;
    EditText txtUnitName, txtQty;
    long lngStockId = 0;
    MaterialButton btnAdd, btnSave;
    PurchaseListAdaptor mAdapter;
    List<MPurchaseList> mItemList = new ArrayList<>();
    MPurchaseRow mRowItem;
    List<MPurchaseRow> mRowItemList = new ArrayList<>();
    MPurchaseDetails mPurchaseDetails;
    List<MPurchaseDetails> mAllPurchaseList = new ArrayList<>();
    MStock mStock;
    MHistory mHistory;
    long lngItemId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Purchase Details");

        mUtils = new CommonUtils(PurchaseDetails.this);

        txtUnitName = findViewById(R.id.txtUnitName);
        txtQty = findViewById(R.id.txtQty);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        spItem = findViewById(R.id.spItem);

        mRecycleView = findViewById(R.id.recycleItems);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                intSummmaryId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            intSummmaryId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }


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
                SavePurchase();
            } else {
                mUtils.showError("Empty list");
            }
        });

        LoadItemList(mRowItemList);
        new fetchItems().execute();
    }

    private void LoadItemList(List<MPurchaseRow> mRowItemList) {
        mAdapter = new PurchaseListAdaptor(PurchaseDetails.this, mRowItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PurchaseDetails.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    private void SavePurchase() {
        class SavePurchase extends AsyncTask<Void, Void, Void> {
            boolean IsError = false;
            String strError = "";

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    mPurchaseDetails = new MPurchaseDetails();
                    for (int i = 0; i < mRowItemList.size(); i++) {
                        mPurchaseDetails.setPurchaseSummaryId(intSummmaryId);
                        mPurchaseDetails.setItemId(mRowItemList.get(i).getItemId());
                        mPurchaseDetails.setQuantity(mRowItemList.get(i).getQty());
                        mPurchaseDetails.setBranchId(0);
                        mPurchaseDetails.setServerId(0);
                        mPurchaseDetails.setPosted(false);
                        mPurchaseDetails.setPostedDt("");
                        mPurchaseDetails.setAddedOn("");
                        mPurchaseDetails.setAddedBy("");
                        mPurchaseDetails.setModifiedBy("");
                        mPurchaseDetails.setModifiedOn("");
                        mPurchaseDetails.setDeleted(false);
                        mPurchaseDetails.setDeletedBy("");
                        mPurchaseDetails.setDeletedOn("");

                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mPurchaseDetails_dao()
                                .insert(mPurchaseDetails);
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
                    new StockUpdate().execute();
                    mUtils.showSuccess("Purchase saved");
                } else {
                    mUtils.showError(strError);
                }
            }
        }

        new SavePurchase().execute();
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

    class StockUpdate extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mAllPurchaseList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mPurchaseDetails_dao()
                        .getById(intSummmaryId);

                for (int i = 0; i < mAllPurchaseList.size(); i++) {
                    mStock = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mStock_dao()
                            .getById(mAllPurchaseList.get(i).getItemId());

                    if (mStock == null) {
                        UpdateStockQty = mAllPurchaseList.get(i).getQuantity();
                        lngStockId = 0;
                    } else {
                        UpdateStockQty = mStock.getQuantity() + mAllPurchaseList.get(i).getQuantity();
                        lngStockId = mStock.getStockId();
                    }
                    mStock = new MStock();
                    mStock.setItemId(mAllPurchaseList.get(i).getItemId());
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


                    if (lngStockId == 0) {
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mStock_dao()
                                .insert(mStock);
                    } else {
                        mStock.setStockId(lngStockId);
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mStock_dao()
                                .update(mStock);
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
                new SaveHistory().execute();
            } else {
                mUtils.showError(strError);
            }

        }

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

    class SaveHistory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for (int i = 0; i < mAllPurchaseList.size(); i++) {
                    mHistory = new MHistory();

                    mHistory.setItemId(mAllPurchaseList.get(i).getItemId());
                    mHistory.setQuantity(mAllPurchaseList.get(i).getQuantity());
                    mHistory.setIncrease(true);
                    mHistory.setDecrease(false);
                    mHistory.setRecipe(false);
                    mHistory.setRecipeId(0);
                    mHistory.setBranchId(0);
                    mHistory.setServerId(0);
                    mHistory.setPosted(false);
                    mHistory.setPostedDt("");
                    mHistory.setAddedOn(MyHelper.getCurrentDateForView());
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
            finish();
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
