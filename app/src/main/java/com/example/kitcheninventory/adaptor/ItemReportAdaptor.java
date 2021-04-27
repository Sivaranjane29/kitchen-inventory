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
import com.example.kitcheninventory.model.MItemReport;

import java.util.List;

public class ItemReportAdaptor extends RecyclerView.Adapter<ItemReportAdaptor.HallViewHolder> {
    Context mContext;
    List<MItemReport> mItemReportList;

    public ItemReportAdaptor(FragmentActivity activity, List<MItemReport> mItemReportList) {
        this.mContext = activity;
        this.mItemReportList = mItemReportList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_report, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MItemReport mRow = mItemReportList.get(position);
        holder.txtName.setText(mRow.getItemName());
        holder.txtQty.setText(String.valueOf(mRow.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mItemReportList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtQty;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtQty = itemView.findViewById(R.id.txtQty);
        }
    }
}
