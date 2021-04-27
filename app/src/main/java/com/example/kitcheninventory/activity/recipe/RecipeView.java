/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.RecipeItemListAdaptor;
import com.example.kitcheninventory.adaptor.recipe.IngredientsAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeView extends AppCompatActivity {

    long lngRecipeId;
    MRecipeSummary mSummary;
    CommonUtils mUtils;
    List<MPurchaseRow> mRowItemList = new ArrayList<>();
    TextView txtRecName, txtPrepare, txtCooking, txtServe, txtCalorie;
    IngredientsAdaptor mAdapter;
    RecyclerView Recycler_ingredients;
    TextView Add;
    String strMenuName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mUtils = new CommonUtils(RecipeView.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                lngRecipeId = Integer.parseInt(extras.getString(GlobalConstants.ROW_DB_ID));
                strMenuName = extras.getString(GlobalConstants.ROW_DB_NAME);
            }
        } else {
            lngRecipeId = (int) savedInstanceState.getSerializable(GlobalConstants.ROW_DB_ID);
            strMenuName = String.valueOf(savedInstanceState.getSerializable(GlobalConstants.ROW_DB_NAME));
        }

        getSupportActionBar().setTitle(strMenuName);

        Recycler_ingredients = findViewById(R.id.Recycler_ingredients);
        txtRecName = findViewById(R.id.txtRecName);
        txtPrepare = findViewById(R.id.txtPrepare);
        txtCooking = findViewById(R.id.txtCooking);
        txtServe = findViewById(R.id.txtServe);
        txtCalorie = findViewById(R.id.txtCalorie);
        Add = findViewById(R.id.Add);

        Add.setOnClickListener(v -> {
            mUtils.showSuccess("Added to cart list");
            finish();
        });

        new FetchRecipe().execute();

    }

    class FetchRecipe extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mSummary = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mRecipeSummary_dao()
                        .getByID(lngRecipeId);

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
                txtPrepare.setText(mSummary.getPrepare());
                txtCooking.setText(mSummary.getStartCooking());
                txtServe.setText(mSummary.getServe());
                txtCalorie.setText(mSummary.getCalorie());

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
                        .getSavedIngredients(lngRecipeId);

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

    private void LoadItemList(List<MPurchaseRow> mRowItemList) {
        mAdapter = new IngredientsAdaptor(RecipeView.this, mRowItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeView.this);
        Recycler_ingredients.setLayoutManager(layoutManager);
        Recycler_ingredients.setAdapter(mAdapter);
    }

}