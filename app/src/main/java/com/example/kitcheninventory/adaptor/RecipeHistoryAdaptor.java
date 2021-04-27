/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.model.MRecipeHistoryList;

import java.util.List;

public class RecipeHistoryAdaptor extends RecyclerView.Adapter<RecipeHistoryAdaptor.HallViewHolder> {
    Context mContext;
    List<MRecipeHistoryList> mRecipeHistoryList;

    public RecipeHistoryAdaptor(FragmentActivity activity, List<MRecipeHistoryList> mRecipeHistoryList) {
        this.mContext = activity;
        this.mRecipeHistoryList = mRecipeHistoryList;
    }


    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recipe_history, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MRecipeHistoryList mRow = mRecipeHistoryList.get(position);
        holder.txtItemName.setText(mRow.getName());
        holder.txtDate.setText(mRow.getAddedOn());

        holder.txtQuantity.setText("-" + mRow.getQuantity());
        holder.txtQuantity.setTextColor(Color.parseColor("#E53935"));

    }

    @Override
    public int getItemCount() {
        return mRecipeHistoryList.size();
    }


    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtDate, txtQuantity;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}
