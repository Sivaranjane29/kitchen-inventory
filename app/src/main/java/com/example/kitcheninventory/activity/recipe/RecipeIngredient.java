/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.recipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.item.AddItem;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.model.MPurchaseList;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.model.MRecipeStock;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.adaptor.RecipeItemListAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.recipe.MRecipeDetails;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeIngredient extends AppCompatActivity {
    Spinner spItem;
    CommonUtils mUtils;

    int[] arrItemId;
    ArrayAdapter ItemSpinnerAdapter;
    private int intItemId;
    String strQty, strItemName, strUnitName, strCategoryName, strPrepare, strCooking, strServe, strCalorie, strTime;
    long lngSummaryId = 0, lngUnitId, lngRowId;
    RecyclerView mRecycleView;
    EditText txtUnitName, txtQty;
    MaterialButton btnAdd, btnSave;
    RecipeItemListAdaptor mAdapter;
    double dblQty, dblCalorie;
    List<MPurchaseList> mItemList = new ArrayList<>();
    MPurchaseRow mRowItem;
    List<MPurchaseRow> mRowItemList = new ArrayList<>();
    MRecipeDetails mRecipeDetails;
    TextView txtAddItem;
    TextInputEditText txtRecName, txtDescription, txtPrepare, txtCooking, txtServe, txtCalorie, txtTime;
    MaterialButton btnCRecipe;
    String strName, strDescription;
    MRecipeSummary mSummary;
    LinearLayout LinearMaster, LinearIngredients, linearSave;
    boolean IsSummarySaved = false;
    boolean IsSupplierNameExists;
    boolean IsAdded = false;
    List<MRecipeStock> IngredientList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_items);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mUtils = new CommonUtils(RecipeIngredient.this);

        txtUnitName = findViewById(R.id.txtUnitName);
        txtQty = findViewById(R.id.txtQty);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        spItem = findViewById(R.id.spItem);
        txtAddItem = findViewById(R.id.txtAddItem);
        LinearMaster = findViewById(R.id.LinearMaster);
        LinearIngredients = findViewById(R.id.LinearIngredients);
        linearSave = findViewById(R.id.linearSave);

        txtPrepare = findViewById(R.id.txtPrepare);
        txtCooking = findViewById(R.id.txtCooking);
        txtServe = findViewById(R.id.txtServe);
        txtCalorie = findViewById(R.id.txtCalorie);
        txtTime = findViewById(R.id.txtTime);
        txtRecName = findViewById(R.id.txtRecName);
        txtDescription = findViewById(R.id.txtDescription);
        btnCRecipe = findViewById(R.id.btnCRecipe);

        mRecycleView = findViewById(R.id.recycleItems);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                lngSummaryId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            lngSummaryId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        if (lngSummaryId > 0) {
            IsSummarySaved = true;
            new FetchRecipe().execute();
            getSupportActionBar().setTitle("Edit Recipe");
        } else {
            getSupportActionBar().setTitle("Add Recipe");
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
                    txtUnitName.setText(mItemList.get(intPosition).UnitName);
                    strCategoryName = mItemList.get(intPosition).getCategoryName();
                    lngUnitId = mItemList.get(intPosition).getUnitId();
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

        btnCRecipe.setOnClickListener(v -> {
            if (ValidateSummary()) {
                SaveReipeSummary();
            }
        });

        btnSave.setOnClickListener(v -> {
            if (mRowItemList.size() > 0) {
                new SavePurchase().execute();
            } else {
                mUtils.showError("Empty list");
            }
        });

        txtAddItem.setOnClickListener(v -> {
            startActivity(new Intent(RecipeIngredient.this, AddItem.class));
        });

        if (IsSummarySaved) {
            LinearIngredients.setVisibility(View.VISIBLE);
            LinearMaster.setVisibility(View.VISIBLE);
            linearSave.setVisibility(View.VISIBLE);
        } else {
            LinearIngredients.setVisibility(View.GONE);
            linearSave.setVisibility(View.GONE);
        }

        new fetchItems().execute();
        LoadItemList(mRowItemList);
    }

    private boolean ValidateSummary() {
        boolean ReturnValue = true;
        strName = Objects.requireNonNull(txtRecName.getText()).toString().trim();
        if (strName.length() < 1) {
            txtRecName.setError("Invalid Name");
            ReturnValue = false;
        }
        strDescription = Objects.requireNonNull(txtDescription.getText()).toString().trim();
        if (strDescription.length() < 1) {
            txtDescription.setError("Invalid");
            ReturnValue = false;
        }
        strPrepare = Objects.requireNonNull(txtPrepare.getText()).toString().trim();
        if (strPrepare.length() < 1) {
            txtPrepare.setError("Invalid");
            ReturnValue = false;
        }

        strCooking = Objects.requireNonNull(txtCooking.getText()).toString().trim();
        if (strCooking.length() < 1) {
            txtCooking.setError("Invalid");
            ReturnValue = false;
        }
        strServe = Objects.requireNonNull(txtServe.getText()).toString().trim();
        if (strServe.length() < 1) {
            txtServe.setError("Invalid");
            ReturnValue = false;
        }
        strCalorie = Objects.requireNonNull(txtCalorie.getText()).toString().trim();
        if (strCalorie.length() < 1) {
            txtCalorie.setError("Invalid");
            ReturnValue = false;
        }

        strTime = Objects.requireNonNull(txtTime.getText()).toString().trim();
        if (strTime.length() < 1) {
            txtTime.setError("Invalid");
            ReturnValue = false;
        }

        return ReturnValue;
    }

    private void LoadItemList(List<MPurchaseRow> mRowItemList) {
        mAdapter = new RecipeItemListAdaptor(RecipeIngredient.this, mRowItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeIngredient.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }


    class SavePurchase extends AsyncTask<Void, Void, Void> {
        long lngRowId = 0;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for (int i = 0; i < mRowItemList.size(); i++) {
                    lngRowId = mRowItemList.get(i).getItemId();

                    mRecipeDetails = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mRecipeDetails_dao()
                            .getRowById(lngSummaryId, lngRowId);

                    if (mRecipeDetails == null) {
                        mRecipeDetails = new MRecipeDetails();
                        mRecipeDetails.setRecipeSummaryId(lngSummaryId);
                        mRecipeDetails.setItemId(mRowItemList.get(i).getItemId());
                        mRecipeDetails.setQty(mRowItemList.get(i).getQty());
                        mRecipeDetails.setUnitId(mRowItemList.get(i).getUnitId());
                        mRecipeDetails.setBranchId(0);
                        mRecipeDetails.setServerId(0);
                        mRecipeDetails.setPosted(false);
                        mRecipeDetails.setPostedDt("");
                        mRecipeDetails.setAddedOn("");
                        mRecipeDetails.setAddedBy("");
                        mRecipeDetails.setModifiedBy("");
                        mRecipeDetails.setModifiedOn("");
                        mRecipeDetails.setDeleted(false);
                        mRecipeDetails.setDeletedBy("");
                        mRecipeDetails.setDeletedOn("");
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mRecipeDetails_dao()
                                .insert(mRecipeDetails);
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mUtils.showSuccess("Recipe saved");
            finish();
        }

    }

    private boolean Validate() {
        boolean ReturnValue = true;

        strQty = Objects.requireNonNull(txtQty.getText()).toString();
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
                mUtils.showInfo("No items Found");
                btnSave.setEnabled(false);
                btnAdd.setEnabled(false);
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
                dblCalorie = mItemList.get(intPosition).getCalorie();
                strUnitName = mItemList.get(intPosition).getUnitName();
                txtUnitName.setText(mItemList.get(intPosition).UnitName);
                strCategoryName = mItemList.get(intPosition).getCategoryName();
                lngUnitId = mItemList.get(intPosition).getUnitId();
            }
        }

    }

    private void AddPurchaseRow() {
        int pos = -1;
        mRowItem = new MPurchaseRow();
        mRowItem.setUnitName(strUnitName);
        mRowItem.setUnitId(lngUnitId);
        mRowItem.setItemId(intItemId);
        mRowItem.setItemName(strItemName);
        mRowItem.setCalorie(dblCalorie);
        mRowItem.setQty(dblQty);

        if (mRowItemList.size() == 0) {
            pos = -1;
        } else {
            for (int i = 0; i < mRowItemList.size(); i++) {
                if (mRowItemList.get(i).getItemId() == mRowItem.getItemId()) {
                    pos = i;
                }
            }
        }
        if (pos != -1) {
            mUtils.showError("Already added");
        } else {
            mRowItemList.add(mRowItem);
        }
        txtQty.setText("");
        LoadItemList(mRowItemList);
    }

    public void RemoveItem(int intPosition) {
        lngRowId = mRowItemList.get(intPosition).getItemId();
        mRowItemList.remove(intPosition);
        new DeleteRow().execute();
        mAdapter.notifyItemRemoved(intPosition);
    }

    class DeleteRow extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mRecipeDetails = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeDetails_dao()
                        .getRowById(lngSummaryId, lngRowId);

                if (mRecipeDetails != null) {
                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mRecipeDetails_dao()
                            .rowDelete(lngSummaryId, lngRowId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private void SaveReipeSummary() {
        class SaveSummary extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    if (lngSummaryId == 0) {
                        mSummary = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mRecipeSummary_dao()
                                .getDuplicate(strName);
                    } else {
                        mSummary = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mRecipeSummary_dao()
                                .getNameOtherThanId(strName, lngSummaryId);
                    }

                    if (mSummary == null) {
                        mSummary = new MRecipeSummary();
                        mSummary.setName(strName);
                        mSummary.setDescription(strDescription);
                        mSummary.setBranchId(0);
                        mSummary.setServerId(0);
                        mSummary.setPosted(false);
                        mSummary.setPostedDt("");
                        mSummary.setAddedOn("");
                        mSummary.setPrepare(strPrepare);
                        mSummary.setStartCooking(strCooking);
                        mSummary.setServe(strServe);
                        mSummary.setCalorie(strCalorie);
                        mSummary.setTime(strTime);
                        mSummary.setAddedBy("");
                        mSummary.setModifiedBy("");
                        mSummary.setModifiedOn("");
                        mSummary.setPostedDt("");
                        mSummary.setDeleted(false);
                        mSummary.setDeletedBy("");
                        mSummary.setDeletedOn("");

                        if (lngSummaryId == 0) {
                            IsAdded = true;
                            lngSummaryId = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mRecipeSummary_dao()
                                    .insert(mSummary);
                        } else {
                            mSummary.setRecipeSummaryId(lngSummaryId);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mRecipeSummary_dao()
                                    .update(mSummary);
                            IsAdded = false;
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
                    mUtils.showError("Recipe name already exists, Try with another name");
                    IsSupplierNameExists = false;
                } else {
                    if (!IsAdded) {
                        mUtils.showSuccess("Recipe updated");
                    } else {
                        mUtils.showSuccess("Recipe added");
                    }

                    LinearIngredients.setVisibility(View.VISIBLE);
                    linearSave.setVisibility(View.VISIBLE);

                }
            }
        }
        new SaveSummary().execute();
    }

    class FetchRecipe extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mSummary = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeSummary_dao()
                        .getByID(lngSummaryId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mSummary != null) {
                txtRecName.setText(mSummary.getName());
                txtDescription.setText(mSummary.getDescription());
                txtPrepare.setText(mSummary.getPrepare());
                txtCooking.setText(mSummary.getStartCooking());
                txtServe.setText(mSummary.getServe());
                txtCalorie.setText(mSummary.getCalorie());
                txtTime.setText(mSummary.getTime());

                new fetchRecipeDetails().execute();
            }
        }
    }

    class fetchRecipeDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mRowItemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeDetails_dao()
                        .getSavedIngredients(lngSummaryId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mRowItemList.size() > 0) {
                LoadItemList(mRowItemList);
            } else {
                mUtils.showError("No ingredients please add items");
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
        new fetchItems().execute();
    }
}
