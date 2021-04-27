/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.report.DailyReport;
import com.example.kitcheninventory.adaptor.ItemHistoryAdaptor;
import com.example.kitcheninventory.adaptor.ItemReportAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MItemHistory;
import com.example.kitcheninventory.model.MItemReport;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.MyHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemFragment extends Fragment {
    RecyclerView mRecycleView;
    List<MItemReport> mItemReportList = new ArrayList<>();
    CommonUtils mUtils;
    ItemReportAdaptor mAdapter;
    LinearLayoutManager mLayoutManager;
    DailyReport mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_re, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView = v.findViewById(R.id.recycleItemRe);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(mLayoutManager);
        mUtils = new CommonUtils(getActivity());
        mActivity = (DailyReport) getContext();

        mActivity.txt_reload.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new fetchItemReport().execute();
            }

            @Override
            public void afterTextChanged(Editable s) {
                new fetchItemReport().execute();
            }
        });

        new fetchItemReport().execute();

        return v;
    }

    class fetchItemReport extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItemReportList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mDailyItem_dao()
                        .getAllItemReport(mActivity.strFromDt, mActivity.strToDt);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mItemReportList.size() == 0) {
                mUtils.showInfo("No History Found");
            }
            LoadReport(mItemReportList);
        }
    }

    private void LoadReport(List<MItemReport> mItemReportList) {
        mAdapter = new ItemReportAdaptor(getActivity(), mItemReportList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

}