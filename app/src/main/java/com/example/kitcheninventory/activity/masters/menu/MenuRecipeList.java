/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.menu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.IngredientsList;
import com.example.kitcheninventory.activity.recipe.RecipeIngredient;
import com.example.kitcheninventory.activity.recipe.RecipeList;
import com.example.kitcheninventory.adaptor.MenuRecipeListAdaptor;
import com.example.kitcheninventory.adaptor.RecipeListAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class MenuRecipeList extends AppCompatActivity {
    List<MMenuRecipeList> recipeSummaryList;
    RecyclerView recycleRecipeList;
    CommonUtils mUtils;
    MenuRecipeListAdaptor mAdapter;
    long mMenuID;
    FloatingActionButton fabRecipeAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_summary);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Recipe list");

        mUtils = new CommonUtils(MenuRecipeList.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mMenuID = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            mMenuID = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        fabRecipeAdd = findViewById(R.id.fabRecipeAdd);
        recycleRecipeList = findViewById(R.id.recycleRecipeList);
        recycleRecipeList.setHasFixedSize(true);
        recycleRecipeList.setLayoutManager(new LinearLayoutManager(this));

        fabRecipeAdd.setVisibility(View.GONE);

        new FetchRecipeList().execute();
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
                LoadRecipe(recipeSummaryList);
            } else {
                mUtils.showError("No recipe found");
            }
        }

        private void LoadRecipe(List<MMenuRecipeList> recipeSummaryList) {
            mAdapter = new MenuRecipeListAdaptor(MenuRecipeList.this, recipeSummaryList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MenuRecipeList.this);
            recycleRecipeList.setLayoutManager(layoutManager);
            recycleRecipeList.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.edit:
                Intent intent = new Intent(MenuRecipeList.this, AddMenu.class);
                intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mMenuID));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchRecipeList().execute();
    }
}