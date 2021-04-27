/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.activity.masters.menu.MenuList;
import com.example.kitcheninventory.activity.masters.menu.MenuRecipeList;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.List;

public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.HallViewHolder> {
    Context mContext;
    List<MMenu> mMenuList;

    public MenuAdaptor(MenuList menuList, List<MMenu> mMenuList) {
        this.mContext = menuList;
        this.mMenuList = mMenuList;
    }

    @NonNull
    @Override
    public MenuAdaptor.HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_menu, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdaptor.HallViewHolder holder, int position) {
        MMenu mRow = mMenuList.get(position);
        holder.txtDescription.setText(mRow.getDescription());
        holder.txtMenuName.setText(mRow.getMenuName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MenuRecipeList.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getMenuId()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtDescription,txtMenuName;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtMenuName = itemView.findViewById(R.id.txtMenuName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
