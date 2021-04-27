/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.report.RolReport;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.cart.MCart;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.utils.CommonUtils;


import java.util.ArrayList;
import java.util.List;

public class FeedAdaptor extends RecyclerView.Adapter<FeedAdaptor.RolViewHolder> {

    Context mContext;
    List<MItem> mItemList;
    CommonUtils mUtils;
    MCart mCart;

    public FeedAdaptor(FragmentActivity activity, List<MItem> mItemList) {
        this.mContext = activity;
        this.mItemList = mItemList;
        mUtils = new CommonUtils(mContext);
    }

    @NonNull
    @Override
    public RolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_rol, parent, false);
        return new RolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RolViewHolder holder, int position) {
        MItem mRow = mItemList.get(position);

        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie());
        holder.txtQty.setText("Qty : " + mRow.getCurrentStock());
        holder.txtRol.setText("ROL : " + mRow.getROL());
        holder.txtName.setText(mRow.getItemName());

        holder.txtAddCart.setVisibility(View.GONE);

        holder.txtAddCart.setOnClickListener(v -> {
//            new saveCart(mRow).execute();
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class saveCart extends AsyncTask<Void, Void, Void> {
        MItem mRow;

        public saveCart(MItem mRow) {
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

    class RolViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCalorie, txtQty, txtUnitName, txtExpiryDate, txtRol, txtAddCart;

        public RolViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCalorie = itemView.findViewById(R.id.txtCalorie);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtUnitName = itemView.findViewById(R.id.txtUnitName);
            txtExpiryDate = itemView.findViewById(R.id.txtExpiryDate);
            txtRol = itemView.findViewById(R.id.txtRol);
            txtAddCart = itemView.findViewById(R.id.txtAddCart);
        }
    }
}
