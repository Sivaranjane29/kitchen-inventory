/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.recipe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.menu.MenuRecipeList;
import com.example.kitcheninventory.adaptor.recipe.RecipeGridAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeGridList extends AppCompatActivity {
    RecipeGridAdaptor mAdaptor;
    GridView RecipeGridView;
    long mMenuID;
    List<MMenuRecipeList> recipeSummaryList;
    CommonUtils mUtils;
    String strMenuName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_grid_list);

        mUtils = new CommonUtils(RecipeGridList.this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mMenuID = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
                strMenuName = extras.getString(GlobalConstants.ROW_DB_NAME);
            }
        } else {
            mMenuID = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
            strMenuName = String.valueOf(savedInstanceState.getSerializable(GlobalConstants.ROW_DB_NAME));
        }

        getSupportActionBar().setTitle(strMenuName);
        RecipeGridView = findViewById(R.id.RecipeGridView);

        new FetchRecipeList().execute();


    }

    private void LoadCategory(List<MMenuRecipeList> nameList) {
        mAdaptor = new RecipeGridAdaptor(RecipeGridList.this, nameList);
        RecipeGridView.setAdapter(mAdaptor);
    }


    class FetchRecipeList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                recipeSummaryList = DatabaseClient
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

            if (recipeSummaryList.size() > 0) {
                LoadCategory(recipeSummaryList);
            } else {
                mUtils.showError("No recipe found");
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
}
