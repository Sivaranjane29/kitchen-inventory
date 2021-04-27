/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.cat.AddCategory;
import com.example.kitcheninventory.activity.recipe.RecipeList;
import com.example.kitcheninventory.adaptor.MenuRecipeAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MCategory;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.db.master.menu.MMenuDetails;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMenu extends AppCompatActivity {
    TextInputEditText txtMenuName, txtDescription;
    String strDescription, strMenuName, strRecName;
    private CommonUtils mUtils;
    MaterialButton btnAdd, btnSaveMenu, btnSaveMenuSummary;
    private int intCatId, intRecId;
    long mMenuID = 0, lngRowId;
    boolean IsSupplierNameExists;
    Spinner spRecipe;
    TextView txtAddCategory, txtAddRecipe;
    MMenu mMenu;
    List<MMenuRecipeList> mMenuRecipeLists = new ArrayList();
    MMenuRecipeList mMenuRecipe;
    MMenuDetails mMenuDetails;

    List<MCategory> mCategoryList = new ArrayList<>();
    int[] arrCatId;
    ArrayAdapter categorySpinnerAdapter;
    int intCategoryPosition;

    int[] arrRecipeId;
    ArrayAdapter RecipeSpinnerAdapter;
    List<MRecipeSummary> mRecipeSummaryList = new ArrayList<>();

    RecyclerView mRecycleView;
    MenuRecipeAdaptor mAdapter;
    boolean IsSummarySaved = false;
    LinearLayout linearRecipe, linearSave, LinearMenuSummary;
    boolean IsAdded = false;
    String strSearchName;
    SearchView src;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        mUtils = new CommonUtils(AddMenu.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        txtMenuName = findViewById(R.id.txtMenuName);
        txtDescription = findViewById(R.id.txtDescription);
        btnSaveMenu = findViewById(R.id.btnSaveMenu);
        spRecipe = findViewById(R.id.spRecipe);
        btnAdd = findViewById(R.id.btnAdd);
        txtAddCategory = findViewById(R.id.txtAddCategory);
        txtAddRecipe = findViewById(R.id.txtAddRecipe);
        btnSaveMenuSummary = findViewById(R.id.btnSaveMenuSummary);
        linearRecipe = findViewById(R.id.linearRecipe);
        linearSave = findViewById(R.id.linearSave);
        LinearMenuSummary = findViewById(R.id.LinearMenuSummary);

        mRecycleView = findViewById(R.id.recycleRecipe);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        LoadRecipeList(mMenuRecipeLists);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mMenuID = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            mMenuID = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (mMenuID > 0) {
            IsSummarySaved = true;
            getMenu();
            getSupportActionBar().setTitle("Edit Menu");
        } else {
            getSupportActionBar().setTitle("Add Menu");
        }

        spRecipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (RecipeSpinnerAdapter.getCount() > 0) {
                    int intCatPosition = spRecipe.getSelectedItemPosition();
                    intRecId = arrRecipeId[intCatPosition];
                } else {
                    mUtils.showError("No recipe found please add to continue");
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (IsSummarySaved) {
            linearSave.setVisibility(View.VISIBLE);
            linearRecipe.setVisibility(View.VISIBLE);
            new fetchRecipe().execute();
        } else {
            linearSave.setVisibility(View.GONE);
            linearRecipe.setVisibility(View.GONE);
        }

        btnSaveMenuSummary.setOnClickListener(v -> {
            if (Validate()) {
                SaveMenu();
            }
        });

        btnAdd.setOnClickListener(v -> {
            strRecName = String.valueOf(spRecipe.getSelectedItem().toString().trim());
            AddRecipeList();
        });

        btnSaveMenu.setOnClickListener(v -> {
            if (mMenuRecipeLists.size() > 0) {
                new SaveMenuDetails().execute();
            } else {
                mUtils.showError("No Recipes selected");
            }
        });

        txtAddCategory.setOnClickListener(v -> {
            startActivity(new Intent(AddMenu.this, AddCategory.class));
        });

        txtAddRecipe.setOnClickListener(v -> {
            startActivity(new Intent(AddMenu.this, RecipeList.class));
        });
    }

    private void AddRecipeList() {
        int pos = -1;
        mMenuRecipe = new MMenuRecipeList();
        mMenuRecipe.setMenuSummaryId(mMenuID);
        mMenuRecipe.setRecipeId(intRecId);
        mMenuRecipe.setName(strRecName);

        if (mMenuRecipeLists.size() == 0) {
            pos = -1;
        } else {
            for (int i = 0; i < mMenuRecipeLists.size(); i++) {
                if (mMenuRecipeLists.get(i).getRecipeId() == mMenuRecipe.getRecipeId()) {
                    pos = i;
                }
            }
        }
        if (pos != -1) {
            mUtils.showError("Already added");
        } else {
            mMenuRecipeLists.add(mMenuRecipe);
        }

        LoadRecipeList(mMenuRecipeLists);
    }


    public void RemoveItem(int intPosition) {
        lngRowId = mMenuRecipeLists.get(intPosition).RecipeId;
        mMenuRecipeLists.remove(intPosition);
        new DeleteRow().execute();
        mAdapter.notifyItemRemoved(intPosition);
    }

    private void LoadRecipeList(List<MMenuRecipeList> mRecipeSummaryList) {
        mAdapter = new MenuRecipeAdaptor(AddMenu.this, mRecipeSummaryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddMenu.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    private boolean Validate() {
        boolean ReturnValue = true;

        strMenuName = Objects.requireNonNull(txtMenuName.getText()).toString().trim();
        if (strMenuName.length() < 1) {
            txtMenuName.setError("Invalid menu name");
            ReturnValue = false;
        }

        strDescription = Objects.requireNonNull(txtDescription.getText()).toString().trim();
        if (strDescription.length() < 1) {
            strDescription = "";
        }
        return ReturnValue;
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
                btnSaveMenuSummary.setEnabled(false);
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
            }
        }
    }

    class fetchRecipe extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mRecipeSummaryList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeSummary_dao()
                        .getAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mRecipeSummaryList.size() == 0) {
                btnAdd.setEnabled(false);
                btnSaveMenu.setEnabled(false);
                mUtils.showInfo("No recipe Found");
            } else {
                int ListSize = mRecipeSummaryList.size();
                String[] items = new String[ListSize];
                arrRecipeId = new int[ListSize];

                for (int i = 0; i < ListSize; i++) {
                    MRecipeSummary row = mRecipeSummaryList.get(i);
                    arrRecipeId[i] = (int) row.getRecipeSummaryId();
                    items[i] = String.valueOf(row.getName());
                }

                RecipeSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_textview, items);
                spRecipe.setAdapter(RecipeSpinnerAdapter);
            }
        }
    }


    private void getMenu() {
        class FetchMenu extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    mMenu = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mMenu_dao()
                            .getById(mMenuID);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (mMenu != null) {
                    txtMenuName.setText(mMenu.getMenuName());
                    txtDescription.setText(mMenu.getDescription());
                    intCatId = mMenu.getCategoryId();
                }
                new FetchMenuRecipe().execute();
            }
        }
        new FetchMenu().execute();
    }

    class FetchMenuRecipe extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mMenuRecipeLists = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mMenuDetails_dao()
                        .getAllItems(mMenuID);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mMenuRecipeLists.size() > 0) {
                LoadRecipeList(mMenuRecipeLists);
            }

        }
    }

    private void SaveMenu() {
        class SaveMenu extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    if (mMenuID == 0) {
                        mMenu = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mMenu_dao()
                                .getByName(strMenuName);
                    } else {
                        mMenu = DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mMenu_dao()
                                .getNameOtherThanId(strMenuName, mMenuID);
                    }

                    if (mMenu == null) {

                        mMenu = new MMenu();
                        mMenu.setMenuName(strMenuName);
                        mMenu.setDescription(strDescription);
                        mMenu.setCategoryId(intCatId);
                        mMenu.setServerId(0);
                        mMenu.setBranchId(0);
                        mMenu.setPosted(false);
                        mMenu.setPostedDt("");
                        mMenu.setActive(true);
                        mMenu.setAddedOn("");
                        mMenu.setAddedBy("");
                        mMenu.setModifiedOn("");
                        mMenu.setModifiedBy("");
                        mMenu.setDeleted(false);
                        mMenu.setDeletedOn("");
                        mMenu.setDeletedBy("");

                        if (mMenuID == 0) {
                            mMenuID = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mMenu_dao()
                                    .insert(mMenu);
                            IsAdded = true;
                        } else {
                            mMenu.setMenuId(mMenuID);
                            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                    .mMenu_dao()
                                    .update(mMenu);
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
                    mUtils.showError("Menu name already exists, Try with another name");
                    IsSupplierNameExists = false;
                } else {
                    if (!IsAdded) {
                        mUtils.showSuccess("Menu updated");
                    } else {
                        mUtils.showSuccess("Menu added");
                    }

                    linearSave.setVisibility(View.VISIBLE);
                    linearRecipe.setVisibility(View.VISIBLE);
                    new fetchRecipe().execute();
                }
            }
        }
        new SaveMenu().execute();
    }

    class DeleteRow extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mMenuDetails = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mMenuDetails_dao()
                        .getRowById(mMenuID, lngRowId);

                if (mMenuDetails != null) {
                    DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mMenuDetails_dao()
                            .rowDelete(mMenuID, lngRowId);
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

    class SaveMenuDetails extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                for (int i = 0; i < mMenuRecipeLists.size(); i++) {
                    lngRowId = mMenuRecipeLists.get(i).getRecipeId();
                    mMenuDetails = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .mMenuDetails_dao()
                            .getRowById(mMenuID, lngRowId);

                    if (mMenuDetails == null) {
                        mMenuDetails = new MMenuDetails();
                        mMenuDetails.setMenuSummaryId(mMenuRecipeLists.get(i).getMenuSummaryId());
                        mMenuDetails.setRecipeId(mMenuRecipeLists.get(i).getRecipeId());
                        mMenuDetails.setBranchId(0);
                        mMenuDetails.setServerId(0);
                        mMenuDetails.setPosted(false);
                        mMenuDetails.setPostedDt("");
                        mMenuDetails.setAddedOn("");
                        mMenuDetails.setAddedBy("");
                        mMenuDetails.setModifiedBy("");
                        mMenuDetails.setModifiedOn("");
                        mMenuDetails.setDeleted(false);
                        mMenuDetails.setDeletedBy("");
                        mMenuDetails.setDeletedOn("");
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getAppDatabase()
                                .mMenuDetails_dao()
                                .insert(mMenuDetails);
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
                mUtils.showSuccess("Success");
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
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        new fetchRecipe().execute();
    }
}