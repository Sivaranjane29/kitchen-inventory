/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor.discover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.db.cart.MCart;

import java.util.List;

public class ChipAdaptor extends RecyclerView.Adapter<ChipAdaptor.HallViewHolder> {

    Context mContext;
    List<MCart> chipList;

    public ChipAdaptor(FragmentActivity activity, List<MCart> chipList) {
        this.mContext = activity;
        this.chipList = chipList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_menu, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MCart mRow = chipList.get(position);
        holder.txtCategory.setVisibility(View.GONE);
        holder.txtDescription.setVisibility(View.GONE);
        holder.txtMenuName.setText(mRow.getItemName());
    }

    @Override
    public int getItemCount() {
        return chipList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtDescription, txtMenuName;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtMenuName = itemView.findViewById(R.id.txtMenuName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
