/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment.favourite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.activity.recipe.AddNewRecipe;
import com.example.kitcheninventory.activity.recipe.RecipeIngredient;
import com.example.kitcheninventory.adaptor.favourite.FavouriteAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Favourite extends Fragment {
    GridView FavourGridView;
    FavouriteAdaptor mAdapter;
    List<MRecipeSummary> mRecipeSummaryList = new ArrayList<>();
    LinearLayout lnrLayoutEmpty;
    MaterialButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        FavourGridView = root.findViewById(R.id.FavourGridView);
        lnrLayoutEmpty = root.findViewById(R.id.lnrLayoutEmpty);
        btnAdd = root.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RecipeIngredient.class));
        });

        new FetchRecipeList().execute();
        return root;
    }

    private void LoadRecipe(List<MRecipeSummary> tittleList) {

        if (tittleList.size() > 0) {
            lnrLayoutEmpty.setVisibility(View.GONE);
            FavourGridView.setVisibility(View.VISIBLE);
            mAdapter = new FavouriteAdaptor(getActivity(), tittleList);
            FavourGridView.setAdapter(mAdapter);
        } else {
            lnrLayoutEmpty.setVisibility(View.VISIBLE);
            FavourGridView.setVisibility(View.GONE);
        }
    }

    class FetchRecipeList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mRecipeSummaryList = DatabaseClient
                        .getInstance(getActivity())
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
            LoadRecipe(mRecipeSummaryList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new FetchRecipeList().execute();
    }
}