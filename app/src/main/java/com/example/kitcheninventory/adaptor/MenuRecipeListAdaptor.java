/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.masters.menu.AddMenu;
import com.example.kitcheninventory.activity.masters.menu.MenuRecipeList;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MenuRecipeListAdaptor extends RecyclerView.Adapter<MenuRecipeListAdaptor.HallViewHolder> {
    Context mContext;
    List<MMenuRecipeList> recipeSummaryList;

    public MenuRecipeListAdaptor(MenuRecipeList menuRecipeList, List<MMenuRecipeList> recipeSummaryList) {
        this.mContext = menuRecipeList;
        this.recipeSummaryList = recipeSummaryList;
    }


    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_menu_recipe, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        holder.btnRemove.setVisibility(View.GONE);
        MMenuRecipeList mRow = recipeSummaryList.get(position);
        holder.txtRecipeName.setText(mRow.getName());

    }

    @Override
    public int getItemCount() {
        return recipeSummaryList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeName;
        MaterialButton btnRemove;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
