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
import com.example.kitcheninventory.activity.masters.sup.AddSupplier;
import com.example.kitcheninventory.activity.masters.sup.SupplierList;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.db.master.MSupplier;

import java.util.List;

public class SupplierAdaptor extends RecyclerView.Adapter<SupplierAdaptor.HallViewHolder> {
    Context mContext;
    List<MSupplier> mList;

    public SupplierAdaptor(SupplierList supplierList, List<MSupplier> mList) {
        this.mContext = supplierList;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SupplierAdaptor.HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_supplier, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdaptor.HallViewHolder holder, int position) {
        MSupplier mRow = mList.get(position);
        holder.txtName.setText(mRow.getName());
        holder.txtAddress.setText(mRow.getAddress());
        holder.txtEmail.setText(mRow.getEmail());
        holder.txtPhone1.setText(mRow.getPhoneOne());
        holder.txtPhone2.setText(mRow.getPhoneTwo());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddSupplier.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getSupplierId()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress, txtPhone1, txtEmail, txtPhone2;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhone1 = itemView.findViewById(R.id.txtPhone1);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone2 = itemView.findViewById(R.id.txtPhone2);
        }
    }
}
