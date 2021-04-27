/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor.recipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.AddNewRecipe;
import com.example.kitcheninventory.activity.recipe.RecipeGridList;
import com.example.kitcheninventory.activity.recipe.RecipeView;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

public class RecipeGridAdaptor extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<MMenuRecipeList> nameList;

    public RecipeGridAdaptor(RecipeGridList activity, List<MMenuRecipeList> nameList) {
        this.context = activity;
        this.nameList = nameList;
        inflater = (LayoutInflater.from(activity));
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.row_crd_recipe, null); // inflate the layout
        TextView txtName, txtTime;

        txtName = convertView.findViewById(R.id.txtName);
        txtTime = convertView.findViewById(R.id.txtTime);


        txtName.setText(nameList.get(position).getName());
        txtTime.setText(nameList.get(position).getTime() + "Min");


        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeView.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(nameList.get(position).RecipeId));
            intent.putExtra(GlobalConstants.ROW_DB_NAME, nameList.get(position).getName());
            context.startActivity(intent);
        });

        return convertView;
    }
}