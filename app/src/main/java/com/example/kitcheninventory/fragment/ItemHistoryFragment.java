/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
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

public class ItemHistoryFragment extends Fragment {
    RecyclerView mRecycleView;
    List<MItemHistory> mItemHistoryList = new ArrayList<>();
    CommonUtils mUtils;
    ItemHistoryAdaptor mAdapter;
    LinearLayoutManager mLayoutManager;
    Calendar mCalendarDt = Calendar.getInstance();
    int mYear1, mMonth1, mDay1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_history, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView = view.findViewById(R.id.recycleItemHistory);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(mLayoutManager);
        mUtils = new CommonUtils(getActivity());


        new fetchItemHistory().execute();
        return view;
    }



    class fetchItemHistory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItemHistoryList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mHistory_dao()
                        .getItemHistory();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mItemHistoryList.size() > 0) {
                LoadReport(mItemHistoryList);
            } else {
                mUtils.showInfo("No History Found");
            }

        }
    }

    private void LoadReport(List<MItemHistory> mItemHistoryList) {
        mAdapter = new ItemHistoryAdaptor(getActivity(), mItemHistoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }
}
