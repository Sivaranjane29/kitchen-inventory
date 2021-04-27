/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.adaptor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.purchase.PurchaseDetails;
import com.example.kitcheninventory.activity.report.Stock;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.model.StockRowList;
import com.example.kitcheninventory.utils.MyHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.transition.Hold;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

public class StockAdaptor extends RecyclerView.Adapter<StockAdaptor.HallViewHolder> {
    Context mContext;
    List<StockRowList> mStockList;
    Stock mActivity;
    Calendar mCalendarExpiryDt = Calendar.getInstance();
    int mYear1, mMonth1, mDay1;
    MStock mStock;
    String strTxtExpiry = "", strQty, dbDate, FormattedDate;
    double dblQty, UpdateStockQty;
    long lngStockId;
    boolean IsAdded;
    MItem mItem;

    public StockAdaptor(Stock stock, List<StockRowList> mStockList) {
        this.mContext = stock;
        this.mStockList = mStockList;
        mActivity = (Stock) mContext;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_stock, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdaptor.HallViewHolder holder, int position) {
        StockRowList mRow = mStockList.get(position);

        holder.txtCalorie.setText("Calorie : " + mRow.getCalorie());
        holder.txtQty.setText("Qty : " + mRow.getQuantity());
        holder.txtUnitName.setText("Unit : " + mRow.getUnitName());
        holder.txtName.setText(mRow.getItemName());

        if (mRow.getExpiryDate() != null) {
            if (mRow.IsExpireCheck) {
                holder.txtExpiryDate.setText("Expiry : " + mRow.getExpiryDate());
            } else {
                holder.txtExpiryDate.setVisibility(View.GONE);
            }
        } else {
            holder.txtExpiryDate.setVisibility(View.GONE);
        }

        if (mRow.getExpiryDate() != null) {
            Calendar mCalendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            FormattedDate = format.format(mCalendar.getTime());

            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date1 = dates.parse(MyHelper.getCurrentDateForView());
                date2 = dates.parse(mRow.getExpiryDate());
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
//            String dayDifference = Long.toString(differenceDates);

                if (differenceDates <= 2) {
                    holder.txtAlert.setVisibility(View.VISIBLE);
                } else {
                    holder.txtAlert.setVisibility(View.GONE);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.txtAlert.setVisibility(View.GONE);
        }


        holder.txtAddQty.setOnClickListener(v -> {
            IsAdded = true;
            ShowBottomSheet(mRow);
        });

        holder.txtMinusQty.setOnClickListener(v -> {
            IsAdded = false;
            ShowBottomSheet(mRow);
        });
    }

    private void ShowBottomSheet(StockRowList mRow) {
        TextView txtNewQty, txtItemName, txtExpiryDate;
        MaterialButton btnUpdate;
        LinearLayout linearExpireDate;

        final BottomSheetDialog bt = new BottomSheetDialog(mContext, R.style.CustomBottomSheetDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet, null);

        txtNewQty = view.findViewById(R.id.txtNewQty);
        txtItemName = view.findViewById(R.id.txtItemName);
        txtExpiryDate = view.findViewById(R.id.txtExpiryDate);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        linearExpireDate = view.findViewById(R.id.linearExpireDate);
        txtItemName.setText(mRow.getItemName());
        txtNewQty.setText("");

        strTxtExpiry = mRow.getExpiryDate();

        if (mRow.isExpireCheck()) {
            txtExpiryDate.setEnabled(true);
            linearExpireDate.setVisibility(View.VISIBLE);
            txtExpiryDate.setText(mRow.getExpiryDate());
        } else {
            linearExpireDate.setVisibility(View.GONE);
            txtExpiryDate.setVisibility(View.GONE);
            if (!IsAdded) {
                linearExpireDate.setVisibility(View.GONE);
            }
        }

        txtExpiryDate.setOnClickListener(v -> {
            mYear1 = mCalendarExpiryDt.get(Calendar.YEAR);
            mMonth1 = mCalendarExpiryDt.get(Calendar.MONTH);
            mDay1 = mCalendarExpiryDt.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        mCalendarExpiryDt.set(year, monthOfYear, dayOfMonth);
                        strTxtExpiry = MyHelper.getDateForViewCalendar(mCalendarExpiryDt);
                        txtExpiryDate.setText(strTxtExpiry);
                        dbDate = MyHelper.getDateForDatabase(mCalendarExpiryDt);
                    }, mYear1, mMonth1, mDay1);
            datePickerDialog.show();
        });

        btnUpdate.setOnClickListener(v -> {
            strQty = txtNewQty.getText().toString().trim();
            if (strQty.trim().length() == 0) {
                dblQty = 0;
            } else {
                dblQty = Double.parseDouble(strQty);
            }

            if (strQty.trim().equals("")) {
                txtNewQty.setError("Invalid Entry");
                txtNewQty.requestFocus();
            } else {
                new StockUpdate(strTxtExpiry, dblQty, mRow).execute();
                bt.dismiss();
            }
        });

        bt.setContentView(view);
        bt.show();
    }

    class StockUpdate extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";
        String exDate;
        StockRowList mRow;
        double dblQty;

        public StockUpdate(String exDate, double dblQty, StockRowList mRow) {
            this.exDate = exDate;
            this.dblQty = dblQty;
            this.mRow = mRow;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mStock = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .mStock_dao()
                        .getById(mRow.getItemId());

                if (IsAdded) {
                    UpdateStockQty = mStock.getQuantity() + dblQty;
                } else {
                    UpdateStockQty = mStock.getQuantity() - dblQty;
                }
                lngStockId = mStock.getStockId();

                mStock = new MStock();
                mStock.setItemId(mRow.getItemId());
                if (UpdateStockQty > 0) {
                    mStock.setQuantity(UpdateStockQty);
                } else {
                    mStock.setQuantity(0);
                }
                mStock.setBranchId(0);
                mStock.setServerId(0);
                mStock.setExpiryDate(exDate);
                mStock.setPosted(false);
                mStock.setPostedDt("");
                mStock.setAddedOn("");
                mStock.setAddedBy("");
                mStock.setModifiedBy("");
                mStock.setModifiedOn("");
                mStock.setDeleted(false);
                mStock.setDeletedBy("");
                mStock.setDeletedOn("");

                mStock.setStockId(lngStockId);
                DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .mStock_dao()
                        .update(mStock);

                if (UpdateStockQty < 0) {
                    UpdateStockQty = 0;
                }

                DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .mItem_dao()
                        .updateCount(UpdateStockQty, mRow.getItemId());

            } catch (Exception e) {
                IsError = true;
                strError = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mActivity.txtListener.setText(".");
        }
    }


    @Override
    public int getItemCount() {
        return mStockList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCalorie, txtQty, txtUnitName, txtExpiryDate, txtAlert;
        MaterialTextView txtMinusQty, txtAddQty;


        public HallViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCalorie = itemView.findViewById(R.id.txtCalorie);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtUnitName = itemView.findViewById(R.id.txtUnitName);
            txtExpiryDate = itemView.findViewById(R.id.txtExpiryDate);
            txtMinusQty = itemView.findViewById(R.id.txtMinusQty);
            txtAddQty = itemView.findViewById(R.id.txtAddQty);
            txtAlert = itemView.findViewById(R.id.txtAlert);

        }
    }
}
