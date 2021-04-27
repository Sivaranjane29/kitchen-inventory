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
import com.example.kitcheninventory.activity.masters.shop.ShopAdd;
import com.example.kitcheninventory.activity.masters.unit.AddUnit;
import com.example.kitcheninventory.activity.shopthings.MarketList;
import com.example.kitcheninventory.db.master.shop.MShop;
import com.example.kitcheninventory.utils.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

public class MarketAdaptor extends RecyclerView.Adapter<MarketAdaptor.HallViewHolder> {
    Context mContext;
    List<MShop> marketnammeList;

    public MarketAdaptor(MarketList marketList, List<MShop> marketnammeList) {
        this.mContext = marketList;
        this.marketnammeList = marketnammeList;
    }


    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_shop, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {

        holder.txtShopName.setText(marketnammeList.get(position).getShopName());
        holder.txtAddress.setText(marketnammeList.get(position).getAddress());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ShopAdd.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(marketnammeList.get(position).getShopID()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return marketnammeList.size();
    }


    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtShopName, txtAddress;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtShopName = itemView.findViewById(R.id.txtShopName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
