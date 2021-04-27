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

import java.util.ArrayList;


public class For_uAdaptor extends RecyclerView.Adapter<For_uAdaptor.HallViewHolder> {
    Context mContext;
    ArrayList<String> nameList;

    public For_uAdaptor(FragmentActivity activity, ArrayList<String> nameList) {
        this.mContext = activity;
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_crd_recipe, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        holder.txtName.setText(nameList.get(position));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
