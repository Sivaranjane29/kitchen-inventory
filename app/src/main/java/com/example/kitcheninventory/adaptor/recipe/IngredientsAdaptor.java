/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.recipe.RecipeView;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class IngredientsAdaptor extends RecyclerView.Adapter<IngredientsAdaptor.HallViewHolder> {
    Context mContext;
    List<MPurchaseRow> mRowItemList;

    public IngredientsAdaptor(RecipeView recipeView, List<MPurchaseRow> mRowItemList) {
        this.mContext = recipeView;
        this.mRowItemList = mRowItemList;
    }


    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_purchase_item, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MPurchaseRow mRow = mRowItemList.get(position);
        holder.btnRemove.setVisibility(View.GONE);

        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie());
        holder.txtQty.setText("Qty : " + mRow.getQty());
        holder.txtUnitName.setText("Unit : " + mRow.getUnitName());
        holder.txtName.setText(mRow.getItemName());

    }

    @Override
    public int getItemCount() {
        return mRowItemList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCalorie, txtQty, txtUnitName, txtCategory;
        MaterialButton btnRemove;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCalorie = itemView.findViewById(R.id.txtCalorie);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtUnitName = itemView.findViewById(R.id.txtUnitName);
            btnRemove = itemView.findViewById(R.id.btnRemove);


        }
    }
}
