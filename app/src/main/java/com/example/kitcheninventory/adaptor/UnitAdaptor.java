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
import com.example.kitcheninventory.activity.masters.unit.AddUnit;
import com.example.kitcheninventory.activity.masters.unit.UnitList;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.db.master.MUnit;

import java.util.List;

public class UnitAdaptor extends RecyclerView.Adapter<UnitAdaptor.HallViewHolder> {
    Context mContext;
    List<MUnit> mList;

    public UnitAdaptor(UnitList unitList, List<MUnit> mList) {
        this.mContext = unitList;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_unit, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        MUnit mRow = mList.get(position);
        holder.txtUnitName.setText(mRow.getUnitName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddUnit.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getUnitId()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtUnitName;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtUnitName = itemView.findViewById(R.id.txtUnitName);
        }
    }
}
