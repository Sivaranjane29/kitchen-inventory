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
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MenuRecipeAdaptor extends RecyclerView.Adapter<MenuRecipeAdaptor.HallViewHolder> {
    Context mContext;
    List<MMenuRecipeList> mRecipeSummaryList;


    public MenuRecipeAdaptor(AddMenu addMenu, List<MMenuRecipeList> mRecipeSummaryList) {
        this.mContext = addMenu;
        this.mRecipeSummaryList = mRecipeSummaryList;
    }

    @NonNull
    @Override
    public MenuRecipeAdaptor.HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_menu_recipe, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecipeAdaptor.HallViewHolder holder, int position) {
        MMenuRecipeList mRow = mRecipeSummaryList.get(position);
        holder.txtRecipeName.setText(mRow.getName());

        holder.btnRemove.setOnClickListener(v -> {
            AddMenu mActivity = (AddMenu) mContext;
            mActivity.RemoveItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeSummaryList.size();
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
