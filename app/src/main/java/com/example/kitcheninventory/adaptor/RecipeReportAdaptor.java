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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.model.MRecipeReport;

import java.util.List;

public class RecipeReportAdaptor extends RecyclerView.Adapter<RecipeReportAdaptor.HallViewHolder> {
    Context mContext;
    List<MRecipeReport> mRecipeReportList;

    public RecipeReportAdaptor(FragmentActivity activity, List<MRecipeReport> mRecipeReportList) {
        this.mContext = activity;
        this.mRecipeReportList = mRecipeReportList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recipe_report, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MRecipeReport mRow = mRecipeReportList.get(position);
        holder.txtName.setText(mRow.getName());
        holder.txtItemCount.setText(String.valueOf(mRow.getItemCount()));
        holder.txtQty.setText(String.valueOf(mRow.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mRecipeReportList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtQty, txtItemCount;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
        }
    }
}
