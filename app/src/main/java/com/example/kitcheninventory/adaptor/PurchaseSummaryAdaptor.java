/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.purchase.Purchase;
import com.example.kitcheninventory.activity.purchase.PurchaseDetails;
import com.example.kitcheninventory.activity.report.purchase.PurchaseSummary;
import com.example.kitcheninventory.activity.report.purchase.Re_PurchaseDetails;
import com.example.kitcheninventory.model.MPurchaseSummaryList;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.List;

public class PurchaseSummaryAdaptor extends RecyclerView.Adapter<PurchaseSummaryAdaptor.HallViewHolder> {
    Context mContext;
    List<MPurchaseSummaryList> mSummaryList;


    public PurchaseSummaryAdaptor(PurchaseSummary purchaseSummary, List<MPurchaseSummaryList> mSummaryList) {
        this.mContext = purchaseSummary;
        this.mSummaryList = mSummaryList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_purchase_summary, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MPurchaseSummaryList mRow = mSummaryList.get(position);
        holder.txtDate.setText(mRow.getPurchaseDate());
        holder.txtSerial.setText("S" + mRow.getSerialNumber());
        holder.txtSupName.setText(mRow.getName());
        holder.txtVoucherNo.setText("Voucher No :" + mRow.getVoucherNumber());

        holder.itemView.setOnClickListener(v -> {
            Intent OpenItem = new Intent(mContext, Re_PurchaseDetails.class);
            OpenItem.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getPurchaseSummaryId()));
            mContext.startActivity(OpenItem);
        });

    }

    @Override
    public int getItemCount() {
        return mSummaryList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtSerial, txtVoucherNo, txtDate, txtSupName;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtSerial = itemView.findViewById(R.id.txtSerial);
            txtVoucherNo = itemView.findViewById(R.id.txtVoucherNo);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtSupName = itemView.findViewById(R.id.txtSupName);

        }
    }
}
