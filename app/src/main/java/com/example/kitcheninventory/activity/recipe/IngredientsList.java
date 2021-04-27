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
import com.example.kitcheninventory.adaptor.SavedRecipeItemAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.model.MRecipeStock;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.List;
import java.util.Objects;

public class IngredientsList extends AppCompatActivity {
    List<MPurchaseRow> IngredientList;
    RecyclerView mRecyclerView;
    CommonUtils mUtils;
    SavedRecipeItemAdaptor mAdapter;
    long lngSummaryId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingridents_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ingredients");

        mUtils = new CommonUtils(IngredientsList.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                lngSummaryId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
            }
        } else {
            lngSummaryId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recycleIngredientsList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new fetchRecipeDetails().execute();
    }

    class fetchRecipeDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                IngredientList = DatabaseClient
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

            if (IngredientList.size() > 0) {
                LoadItems(IngredientList);
            } else {
                mUtils.showError("No ingredients please add items");
            }
        }
    }

    private void LoadItems(List<MPurchaseRow> ingredientList) {
        mAdapter = new SavedRecipeItemAdaptor(IngredientsList.this, ingredientList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IngredientsList.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.edit:
                Intent intent = new Intent(IngredientsList.this, RecipeIngredient.class);
                intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(lngSummaryId));
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
        new fetchRecipeDetails().execute();
    }
}
