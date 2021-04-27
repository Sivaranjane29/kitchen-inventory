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
import com.example.kitcheninventory.activity.masters.item.AddItem;
import com.example.kitcheninventory.activity.masters.item.ItemList;
import com.example.kitcheninventory.model.MRowItemList;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.List;

public class ItemAdaptor extends RecyclerView.Adapter<ItemAdaptor.HallViewHolder> {
    Context mContext;
    List<MRowItemList> mList;

    public ItemAdaptor(ItemList itemList, List<MRowItemList> mList) {
        this.mContext = itemList;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemAdaptor.HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdaptor.HallViewHolder holder, int position) {
        MRowItemList mRow = mList.get(position);

        holder.txtItemName.setText(mRow.getItemName());
        holder.txtDescription.setText(mRow.getDescription());
        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie() + "%");
        holder.txtUnit.setText("Unit : " + mRow.getUnitName());
        holder.txtCategory.setText(mRow.getCategoryName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddItem.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getItemId()));
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtCalorie, txtDescription, txtCategory, txtUnit;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtCalorie = itemView.findViewById(R.id.txtCalorie);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtUnit = itemView.findViewById(R.id.txtUnit);

        }
    }

}
