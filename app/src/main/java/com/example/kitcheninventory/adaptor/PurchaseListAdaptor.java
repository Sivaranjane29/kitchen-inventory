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
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.activity.purchase.PurchaseDetails;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class PurchaseListAdaptor extends RecyclerView.Adapter<PurchaseListAdaptor.HallViewHolder> {
    Context mContext;
    List<MPurchaseRow> mRowItemList;

    public PurchaseListAdaptor(PurchaseDetails purchaseDetails, List<MPurchaseRow> mRowItemList) {
        this.mContext = purchaseDetails;
        this.mRowItemList = mRowItemList;
    }


    @NonNull
    @Override
    public PurchaseListAdaptor.HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_purchase_item, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseListAdaptor.HallViewHolder holder, int position) {
        MPurchaseRow mRow = mRowItemList.get(position);

        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie());
        holder.txtQty.setText("Qty : " + mRow.getQty());
        holder.txtUnitName.setText("Unit : " + mRow.getUnitName());
        holder.txtName.setText(mRow.getItemName());

        holder.btnRemove.setOnClickListener(v -> {
            PurchaseDetails mActivity = (PurchaseDetails) mContext;
            mActivity.RemoveItem(position);
        });

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
            txtUnitName = itemView.findViewById(R.id.txtUnitName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            txtCategory = itemView.findViewById(R.id.txtCategory);

        }
    }
}
