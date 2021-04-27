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
import com.example.kitcheninventory.activity.recipe.IngredientsList;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.model.MRecipeStock;

import java.util.List;

public class SavedRecipeItemAdaptor extends RecyclerView.Adapter<SavedRecipeItemAdaptor.HallViewHolder> {
    Context mContext;
    List<MPurchaseRow> ingredientList;

    public SavedRecipeItemAdaptor(IngredientsList ingredientsList, List<MPurchaseRow> ingredientList) {
        this.mContext = ingredientsList;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_saved_recpitem, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MPurchaseRow mRow = ingredientList.get(position);
        holder.txtItemName.setText(mRow.getItemName());
        holder.txtQty.setText("Qty : " + mRow.getQty());
        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie() + "%");
        holder.txtUnit.setText("Unit : " + mRow.getUnitName());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtUnit, txtQty, txtCalorie;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtUnit = itemView.findViewById(R.id.txtUnit);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtCalorie = itemView.findViewById(R.id.txtCalorie);
        }
    }
}
