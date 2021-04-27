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
import com.example.kitcheninventory.model.MItemHistory;

import java.util.List;

public class ItemHistoryAdaptor extends RecyclerView.Adapter<ItemHistoryAdaptor.HallViewHolder> {
    Context mContext;
    List<MItemHistory> mItemHistoryList;

    public ItemHistoryAdaptor(FragmentActivity activity, List<MItemHistory> mItemHistoryList) {
        this.mContext = activity;
        this.mItemHistoryList = mItemHistoryList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_history, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MItemHistory mRow = mItemHistoryList.get(position);
        holder.txtItemName.setText(mRow.getItemName());
        holder.txtDate.setText(mRow.getAddedOn());
        if (mRow.isRecipe()) {
            holder.txtFromRecipe.setText("Yes");
        } else {
            holder.txtFromRecipe.setText("No");
        }

        if (mRow.isIncrease()) {
            holder.txtQuantity.setText("+" + mRow.getQuantity());
            holder.txtQuantity.setTextColor(Color.parseColor("#03ad53"));
        } else {
            holder.txtQuantity.setText("-" + mRow.getQuantity());
            holder.txtQuantity.setTextColor(Color.parseColor("#E53935"));
        }

    }

    @Override
    public int getItemCount() {
        return mItemHistoryList.size();
    }


    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtDate, txtFromRecipe, txtQuantity;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtFromRecipe = itemView.findViewById(R.id.txtFromRecipe);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}
