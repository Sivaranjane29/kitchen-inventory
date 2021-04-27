/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;


public class DemoRecyclerViewAdapter extends RecyclerView.Adapter<DemoRecyclerViewAdapter.ViewHolder> {

    private int count;
    private final int itemWidth;

    public DemoRecyclerViewAdapter(int count, int itemWidth) {
        this.count = count;
        this.itemWidth = itemWidth;
    }

    void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_appoinment_row, parent, false);
        view.getLayoutParams().width = itemWidth;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        final TextView title;

        ViewHolder(View itemView) {
            super(itemView);
//            title = itemView.findViewById(R.id.demo_page_label);
        }
    }
}
