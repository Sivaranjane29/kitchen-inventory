/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment.recipes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.activity.masters.menu.MenuRecipeList;
import com.example.kitcheninventory.activity.recipe.RecipeIngredient;
import com.example.kitcheninventory.adaptor.MenuRecipeListAdaptor;
import com.example.kitcheninventory.adaptor.recipe.CategoryGridAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class Category_Grid extends Fragment {
    CategoryGridAdaptor mAdaptor;
    GridView categoryGridView;
    LinearLayout lnrLayoutEmpty;
    int intCount;
    List<MMenu> mMenuList = new ArrayList<>();
    ArrayList<String> countList = new ArrayList<>();
    List<MMenuRecipeList> recipeSummaryList;
    CommonUtils mUtils;
    MaterialButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_grid, container, false);

        mUtils = new CommonUtils(getActivity());

        categoryGridView = root.findViewById(R.id.categoryGridView);
        lnrLayoutEmpty = root.findViewById(R.id.lnrLayoutEmpty);
        btnAdd = root.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddMenu.class));
        });

        new fetchMenuList().execute();
        return root;
    }

    class fetchMenuList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mMenuList = DatabaseClient
                        .getInstance(getActivity())
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
                mUtils.showInfo("No menu Found");
                lnrLayoutEmpty.setVisibility(View.VISIBLE);
                categoryGridView.setVisibility(View.GONE);
            } else {
                lnrLayoutEmpty.setVisibility(View.GONE);
                categoryGridView.setVisibility(View.VISIBLE);
                new FetchRecipeList().execute();
            }
        }
    }

    class FetchRecipeList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                for (int i = 0; i < mMenuList.size(); i++) {
                    intCount = DatabaseClient
                            .getInstance(getActivity())
                            .getAppDatabase()
                            .mMenuDetails_dao()
                            .getCount(mMenuList.get(i).getMenuId());
                    countList.add(String.valueOf(intCount));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (countList.size() > 0) {
                LoadCategory(mMenuList, countList);
            } else {
                mUtils.showError("No recipe found");
            }
        }
    }

    private void LoadCategory(List<MMenu> mMenuList, ArrayList<String> countList) {
        mAdaptor = new CategoryGridAdaptor(getActivity(), mMenuList, countList);
        categoryGridView.setAdapter(mAdaptor);
    }

    @Override
    public void onResume() {
        super.onResume();
        new fetchMenuList().execute();
    }
}