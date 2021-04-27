/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.daily;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.activity.recipe.RecipeList;
import com.example.kitcheninventory.activity.shopthings.ShopItem;
import com.example.kitcheninventory.adaptor.DailyRecipeAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.daily.MDailyItem;
import com.example.kitcheninventory.db.daily.MDailyRecipe;
import com.example.kitcheninventory.db.history.MHistory;
import com.example.kitcheninventory.db.history.MRecipeHistory;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.model.MRecipeStock;
import com.example.kitcheninventory.model.QuantityDetails;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.MyHelper;
import com.example.kitcheninventory.utils.PrefManager;
import com.google.android.material.button.MaterialButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DailyRecipe extends AppCompatActivity {
    SearchableSpinner spMenu, spRecipe;
    MaterialButton btnProceedSave;
    List<MItem> lstItemNotify;
    EditText txtQty;
    double dblReqQty = 0;
    double dblGivenQty = 0;
    double UpdateStockQty = 0;

    CommonUtils mUtils;
    PrefManager mPref;
    List<MMenu> mMenuList = new ArrayList<>();
    int[] arrMenuId;
    ArrayAdapter MenuSpinnerAdapter;
    private long MenuId;

    int[] arrRecipeId;
    ArrayAdapter RecipeSpinnerAdapter;
    private long RecipeId;

    List<MMenuRecipeList> mMenuRecipeLists = new ArrayList<>();
    List<MRecipeStock> mStockLists = new ArrayList<>();
    List<QuantityDetails> mStockQtyList = new ArrayList<>();
    QuantityDetails mQuantityDetails;
    RecyclerView mRecycleView;
    DailyRecipeAdaptor mAdapter;
    MStock mStock;
    MRecipeHistory mRecipeHistory;
    MHistory mHistory;
    MDailyRecipe mDailyRecipe;
    MDailyItem mDailyItem;
    TextView txtAddRecipe, txtAddMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_recipe);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daily Recipe");

        mUtils = new CommonUtils(DailyRecipe.this);
        mPref = new PrefManager(DailyRecipe.this);

        txtQty = findViewById(R.id.txtQty);
        spMenu = findViewById(R.id.spMenu);
        spRecipe = findViewById(R.id.spRecipe);
        txtAddRecipe = findViewById(R.id.txtAddRecipe);
        txtAddMenu = findViewById(R.id.txtAddMenu);
        btnProceedSave = findViewById(R.id.btnProceedSave);
        mRecycleView = findViewById(R.id.recycleItems);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        txtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CalculateQty();

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        spMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (MenuSpinnerAdapter.getCount() > 0) {
                    int intMenuPosition = spMenu.getSelectedItemPosition();
                    MenuId = arrMenuId[intMenuPosition];
                    new fetchRecipeList().execute();
                } else {
                    mUtils.showError("No menu found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spRecipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (RecipeSpinnerAdapter.getCount() > 0) {
                    int intRecipePosition = spRecipe.getSelectedItemPosition();
                    RecipeId = arrRecipeId[intRecipePosition];
                    new fetchRecipeDetails().execute();
                } else {
                    mUtils.showError("No recipe found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btnProceedSave.setOnClickListener(v -> {
            new SaveDailyRecipe().execute();
            new StockUpdate().execute();
            new UpdateItemReport().execute();
        });

        txtAddRecipe.setOnClickListener(v -> {
            startActivity(new Intent(DailyRecipe.this, RecipeList.class));
        });
        txtAddMenu.setOnClickListener(v -> {
            startActivity(new Intent(DailyRecipe.this, AddMenu.class));
        });

        new fetchMenuList().execute();
    }

    class fetchMenuList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mMenuList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mMenu_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mMenuList.size() == 0) {
                btnProceedSave.setEnabled(false);
                mUtils.showInfo("No menu Found");
            } else {
                int ListSize = mMenuList.size();
                String[] items = new String[ListSize];
                arrMenuId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MMenu row = mMenuList.get(i);
                    arrMenuId[i] = (int) row.getMenuId();
                    items[i] = String.valueOf(row.getMenuName());
                }

                MenuSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spMenu.setAdapter(MenuSpinnerAdapter);
                MenuId = arrMenuId[0];
                new fetchRecipeList().execute();
            }
        }
    }

    class fetchRecipeList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mMenuRecipeLists = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mMenuDetails_dao()
                        .getAllItems(MenuId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mMenuRecipeLists.size() == 0) {
                btnProceedSave.setEnabled(false);
                mUtils.showInfo("No recipe Found");
            } else {
                int ListSize = mMenuRecipeLists.size();
                String[] items = new String[ListSize];
                arrRecipeId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MMenuRecipeList row = mMenuRecipeLists.get(i);
                    arrRecipeId[i] = (int) row.getRecipeId();
                    items[i] = String.valueOf(row.getName());
                }

                RecipeSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spRecipe.setAdapter(RecipeSpinnerAdapter);
            }
        }
    }

    class fetchRecipeDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mStockLists = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeDetails_dao()
                        .getAllIngredients(RecipeId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mStockLists.size() > 0) {

            } else {
                mUtils.showError("No ingredients please purchase items");
            }
        }
    }

    private void CalculateQty() {
        dblGivenQty = 0;
        double dblNotAvailableQty = 0;

        String strQty = txtQty.getText().toString();
        if (strQty.length() < 1 || strQty.equals(".") || strQty.equals("0")) {
            dblGivenQty = 0;
        } else {
            dblGivenQty = Double.parseDouble(strQty);
        }

        if (mStockLists.size() > 0) {
            mStockQtyList.clear();
            for (int i = 0; i < mStockLists.size(); i++) {
                dblReqQty = mStockLists.get(i).Qty * dblGivenQty;

                if (dblReqQty > mStockLists.get(i).getQuantity()) {
                    dblNotAvailableQty = dblReqQty - mStockLists.get(i).getQuantity();
                } else {
                    dblNotAvailableQty = 0;
                }

                mQuantityDetails = new QuantityDetails();
                mQuantityDetails.setItemName(mStockLists.get(i).getItemName());
                mQuantityDetails.setCalorie(mStockLists.get(i).getCalorie());
                mQuantityDetails.setGivenQty(dblGivenQty);
                mQuantityDetails.setNotAvailableQty(dblNotAvailableQty);
                mQuantityDetails.setRecipeSummaryId(mStockLists.get(i).getRecipeSummaryId());
                mQuantityDetails.setItemId(mStockLists.get(i).getItemId());
                mQuantityDetails.setQty(mStockLists.get(i).getQty());
                mQuantityDetails.setAvailableQty(mStockLists.get(i).getQuantity());
                mQuantityDetails.setReqQty(dblReqQty);
                mQuantityDetails.setMenuId(MenuId);
                mQuantityDetails.setRecipeSummaryId(mStockLists.get(i).getRecipeSummaryId());


                mStockQtyList.add(mQuantityDetails);
            }
            LoadRecipeDetails(mStockQtyList);
        }
    }

    private void LoadRecipeDetails(List<QuantityDetails> mStockQtyList) {
        mAdapter = new DailyRecipeAdaptor(DailyRecipe.this, mStockQtyList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DailyRecipe.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }


    class StockUpdate extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                for (int i = 0; i < mStockQtyList.size(); i++) {
                    UpdateStockQty = mStockQtyList.get(i).getAvailableQty() - mStockQtyList.get(i).getReqQty();

                    mStock = new MStock();
                    mStock.setItemId(mStockQtyList.get(i).getItemId());
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
                            .updateStatus(mStockQtyList.get(i).getItemId(), UpdateStockQty);

                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mItem_dao()
                            .updateCount(UpdateStockQty, mStockQtyList.get(i).getItemId());

                    lstItemNotify = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mItem_dao()
                            .getRolAlert();
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

            if (lstItemNotify.size() > 0) {
                if (lstItemNotify.size() == 1) {
                    showNotify(lstItemNotify.get(0).getItemName() + " Is under ROL, Please check your cart.");
                } else {
                    showNotify("There are " + lstItemNotify.size() + " Items under ROL, Please check your cart.");
                }
            }
        }
    }

    private void showNotify(String message) {
        Intent intent = new Intent(this, ShopItem.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Item ROL alert!!")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.action_search);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder.build());
    }


    class SaveHistory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for (int i = 0; i < mStockQtyList.size(); i++) {
                    mHistory = new MHistory();

                    mHistory.setItemId(mStockQtyList.get(i).getItemId());
                    mHistory.setQuantity(mStockQtyList.get(i).getReqQty());
                    mHistory.setIncrease(false);
                    mHistory.setDecrease(true);
                    mHistory.setRecipe(true);
                    mHistory.setRecipeId(mStockQtyList.get(i).getRecipeSummaryId());
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
            new SaveRecipeHistory().execute();
            mUtils.showSuccess("Stock Update");
        }
    }

    class SaveRecipeHistory extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mRecipeHistory = new MRecipeHistory();
                mRecipeHistory.setRecipeId(RecipeId);
                mRecipeHistory.setQuantity(dblGivenQty);
                mRecipeHistory.setBranchId(0);
                mRecipeHistory.setServerId(0);
                mRecipeHistory.setPosted(false);
                mRecipeHistory.setPostedDt("");
                mRecipeHistory.setAddedOn(MyHelper.getCurrentDateForView());
                mRecipeHistory.setAddedBy("");
                mRecipeHistory.setModifiedBy("");
                mRecipeHistory.setModifiedOn("");
                mRecipeHistory.setDeleted(false);
                mRecipeHistory.setDeletedBy("");
                mRecipeHistory.setDeletedOn("");

                DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeHistory_dao()
                        .insert(mRecipeHistory);


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
                mUtils.showSuccess("Stock update");
            } else {
                mUtils.showError(strError);
            }

        }
    }

    class UpdateItemReport extends AsyncTask<Void, Void, Void> {
        double dblRItemQty = 0, UpdateDailyQty = 0;
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                for (int i = 0; i < mStockQtyList.size(); i++) {
                    mDailyItem = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mDailyItem_dao()
                            .getById(mStockQtyList.get(i).getItemId(), MyHelper.getCurrentDateForDatabase());

                    if (mDailyItem == null) {
                        mDailyItem = new MDailyItem();
                        mDailyItem.setItemId(mStockQtyList.get(i).getItemId());
                        mDailyItem.setRecipeId(mStockQtyList.get(i).getRecipeSummaryId());
                        mDailyItem.setQuantity(mStockQtyList.get(i).getGivenQty());
                        mDailyItem.setRecipe(true);
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
                        UpdateDailyQty = dblRItemQty + mStockQtyList.get(i).getReqQty();
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mDailyItem_dao()
                                .UpdateById(mStockQtyList.get(i).getItemId(), MyHelper.getCurrentDateForDatabase(), UpdateDailyQty);
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

    class SaveDailyRecipe extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";
        double dblRecipeQty = 0, UpdateDailyQty = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                mDailyRecipe = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mDailyRecipe_dao()
                        .getById(RecipeId);

                if (mDailyRecipe == null) {
                    mDailyRecipe = new MDailyRecipe();
                    mDailyRecipe.setRecipeSummaryId(RecipeId);
                    mDailyRecipe.setQuantity(dblGivenQty);
                    mDailyRecipe.setItemCount(mStockLists.size());
                    mDailyRecipe.setBranchId(0);
                    mDailyRecipe.setServerId(0);
                    mDailyRecipe.setPosted(false);
                    mDailyRecipe.setPostedDt("");
                    mDailyRecipe.setAddedOn(MyHelper.getCurrentDateForDatabase());
                    mDailyRecipe.setAddedBy("");
                    mDailyRecipe.setModifiedBy("");
                    mDailyRecipe.setModifiedOn("");
                    mDailyRecipe.setDeleted(false);
                    mDailyRecipe.setDeletedBy("");
                    mDailyRecipe.setDeletedOn("");

                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mDailyRecipe_dao()
                            .insert(mDailyRecipe);

                } else {
                    dblRecipeQty = mDailyRecipe.getQuantity();
                    UpdateDailyQty = dblRecipeQty + dblGivenQty;
                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mDailyRecipe_dao()
                            .UpdateById(RecipeId, MyHelper.getCurrentDateForDatabase(), UpdateDailyQty);
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
                mUtils.showSuccess("Stock Report");
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new fetchMenuList().execute();
    }
}
