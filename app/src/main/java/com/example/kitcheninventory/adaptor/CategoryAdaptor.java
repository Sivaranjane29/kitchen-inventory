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
import com.example.kitcheninventory.activity.masters.cat.AddCategory;
import com.example.kitcheninventory.activity.masters.cat.CategoryList;
import com.example.kitcheninventory.utils.GlobalConstants;
import com.example.kitcheninventory.db.master.MCategory;

import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.HallViewHolder> {
    Context mContext;
    List<MCategory> mList;


    public CategoryAdaptor(CategoryList categoryList, List<MCategory> mList) {
        this.mContext = categoryList;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CategoryAdaptor.HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_category, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.HallViewHolder holder, int position) {
        MCategory mRow = mList.get(position);
        holder.txtCategory.setText(mRow.getCategoryName());
        holder.txtDescription.setText(mRow.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddCategory.class);
            intent.putExtra(GlobalConstants.ROW_DB_ID, String.valueOf(mRow.getCategoryId()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtDescription;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
