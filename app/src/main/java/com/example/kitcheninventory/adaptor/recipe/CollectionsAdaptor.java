/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;

import java.util.ArrayList;

public class CollectionsAdaptor extends RecyclerView.Adapter<CollectionsAdaptor.HallViewHolder> {
    Context mContext;
    ArrayList<String> tittleList;

    public CollectionsAdaptor(FragmentActivity activity, ArrayList<String> tittleList) {
        this.mContext = activity;
        this.tittleList = tittleList;
    }


    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_collections, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        holder.txtTittle.setText(tittleList.get(position));
    }

    @Override
    public int getItemCount() {
        return tittleList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtTittle;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtTittle = itemView.findViewById(R.id.txtTittle);
        }
    }
}
