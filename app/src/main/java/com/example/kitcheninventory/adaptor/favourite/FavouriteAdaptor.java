/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor.favourite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.RecipeGridList;
import com.example.kitcheninventory.activity.recipe.RecipeView;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdaptor extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<MRecipeSummary> nameList;

    public FavouriteAdaptor(FragmentActivity activity, List<MRecipeSummary> nameList) {
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
        convertView = inflater.inflate(R.layout.row_favourite, null); // inflate the layout
        TextView txtName, txtTime;

        txtName = convertView.findViewById(R.id.txtName);
        txtTime = convertView.findViewById(R.id.txtTime);


        txtName.setText(nameList.get(position).getName());
        txtTime.setText(nameList.get(position).getTime() + "Minutes");


        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeView.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(nameList.get(position).getRecipeSummaryId()));
            intent.putExtra(GlobalConstants.ROW_DB_NAME, nameList.get(position).getName());
            context.startActivity(intent);
        });

        return convertView;
    }
}