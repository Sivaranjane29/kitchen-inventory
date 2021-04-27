/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.daily.DailyRecipe;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.cart.MCart;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.model.QuantityDetails;
import com.example.kitcheninventory.utils.CommonUtils;

import java.util.List;

public class DailyRecipeAdaptor extends RecyclerView.Adapter<DailyRecipeAdaptor.HallViewHolder> {
    Context mContext;
    List<QuantityDetails> mStockQtyList;
    MCart mCart;
    CommonUtils mUtils;

    public DailyRecipeAdaptor(DailyRecipe dailyRecipe, List<QuantityDetails> mStockQtyList) {
        this.mContext = dailyRecipe;
        this.mStockQtyList = mStockQtyList;
        mUtils = new CommonUtils(mContext);
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_qty, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        QuantityDetails mRow = mStockQtyList.get(position);

        holder.txtName.setText(mRow.getItemName());
        holder.txtAvailableQty.setText(String.valueOf(mRow.getAvailableQty()));
        holder.txtReqQty.setText(String.valueOf(mRow.getReqQty()));
        holder.txtQty.setText(String.valueOf(mRow.getQty()));
        if (mRow.getNotAvailableQty() != 0) {
//            holder.txtNotAvailableQty.setText(String.valueOf(mRow.getNotAvailableQty()));
            holder.txtNotAvailableQty.setText("Add to Cart");
        } else {
            holder.txtNotAvailableQty.setText("-");
            holder.txtNotAvailableQty.setEnabled(false);
        }

        holder.txtNotAvailableQty.setOnClickListener(v -> {
            new saveCart(mRow).execute();
        });


    }

    @Override
    public int getItemCount() {
        return mStockQtyList.size();
    }

    class saveCart extends AsyncTask<Void, Void, Void> {
        QuantityDetails mRow;

        public saveCart(QuantityDetails mRow) {
            this.mRow = mRow;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mCart = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .mCart_dao()
                        .getRowById(mRow.getItemName());

                if (mCart == null) {
                    mCart = new MCart();
                    mCart.setItemName(mRow.getItemName());

                    DatabaseClient
                            .getInstance(mContext)
                            .getAppDatabase()
                            .mCart_dao()
                            .insert(mCart);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mUtils.showSuccess("Added to cart");

        }
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtQty, txtReqQty, txtAvailableQty, txtNotAvailableQty;

        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtReqQty = itemView.findViewById(R.id.txtReqQty);
            txtAvailableQty = itemView.findViewById(R.id.txtAvailableQty);
            txtNotAvailableQty = itemView.findViewById(R.id.txtNotAvailableQty);

        }
    }
}
