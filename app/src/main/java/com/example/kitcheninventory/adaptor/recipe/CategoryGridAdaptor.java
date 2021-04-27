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

import androidx.fragment.app.FragmentActivity;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.RecipeGridList;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

public class CategoryGridAdaptor extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<MMenu> mMenuList;
    ArrayList<String> countList;


    public CategoryGridAdaptor(FragmentActivity activity, List<MMenu> mMenuList, ArrayList<String> countList) {
        this.context = activity;
        this.mMenuList = mMenuList;
        this.countList = countList;
        inflater = (LayoutInflater.from(activity));
    }

    @Override
    public int getCount() {
        return mMenuList.size();
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
        convertView = inflater.inflate(R.layout.rowitem_category, null); // inflate the layout
        TextView txtName, txtCount;

        txtName = convertView.findViewById(R.id.txtName);
        txtCount = convertView.findViewById(R.id.txtCount);

        txtName.setText(mMenuList.get(position).getMenuName());
        txtCount.setText(countList.get(position));

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeGridList.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mMenuList.get(position).getMenuId()));
            intent.putExtra(GlobalConstants.ROW_DB_NAME, mMenuList.get(position).getMenuName());
            context.startActivity(intent);
        });
        return convertView;
    }
}