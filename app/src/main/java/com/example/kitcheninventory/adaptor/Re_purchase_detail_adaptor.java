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
import com.example.kitcheninventory.activity.report.purchase.Re_PurchaseDetails;
import com.example.kitcheninventory.model.MRePurchaseDetails;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class Re_purchase_detail_adaptor extends RecyclerView.Adapter<Re_purchase_detail_adaptor.HallViewHolder> {
    Context mContext;
    List<MRePurchaseDetails> mDetailsList;

    public Re_purchase_detail_adaptor(Re_PurchaseDetails re_purchaseDetails, List<MRePurchaseDetails> mDetailsList) {
        this.mContext = re_purchaseDetails;
        this.mDetailsList = mDetailsList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_purchase_item, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        holder.btnRemove.setVisibility(View.GONE);

        MRePurchaseDetails mRow = mDetailsList.get(position);

        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie());
        holder.txtQty.setText("Qty : " + mRow.getQuantity());
        holder.txtUnitName.setText("Unit : " + mRow.getUnitName());
        holder.txtName.setText(mRow.getItemName());

    }

    @Override
    public int getItemCount() {
        return mDetailsList.size();
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
