/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.recipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.RecipeListAdaptor;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class RecipeList extends AppCompatActivity {
    List<MRecipeSummary> recipeSummaryList;
    RecyclerView recycleRecipeList;
    CommonUtils mUtils;
    RecipeListAdaptor mAdapter;
    FloatingActionButton fabAddRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_summary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Recipes");

        mUtils = new CommonUtils(RecipeList.this);

        recycleRecipeList = findViewById(R.id.recycleRecipeList);
        recycleRecipeList.setHasFixedSize(true);
        recycleRecipeList.setLayoutManager(new LinearLayoutManager(this));

        fabAddRecipe = findViewById(R.id.fabRecipeAdd);
        fabAddRecipe.setOnClickListener(v -> {
            startActivity(new Intent(RecipeList.this, RecipeIngredient.class));
        });

        new FetchRecipeList().execute();

    }

    class FetchRecipeList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                recipeSummaryList = DatabaseClient
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

            if (recipeSummaryList.size() > 0) {
                LoadRecipe(recipeSummaryList);
            } else {
                mUtils.showError("No recipe found");
            }
        }

        private void LoadRecipe(List<MRecipeSummary> recipeSummaryList) {
            mAdapter = new RecipeListAdaptor(RecipeList.this, recipeSummaryList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeList.this);
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
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchRecipeList().execute();
    }
}
