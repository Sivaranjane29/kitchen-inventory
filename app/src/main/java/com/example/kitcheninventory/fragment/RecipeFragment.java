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
import com.example.kitcheninventory.activity.masters.unit.UnitList;
import com.example.kitcheninventory.activity.report.DailyReport;
import com.example.kitcheninventory.activity.report.Stock;
import com.example.kitcheninventory.adaptor.RecipeReportAdaptor;
import com.example.kitcheninventory.adaptor.UnitAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MRecipeReport;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.MyHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecipeFragment extends Fragment {
    RecyclerView mRecycleView;
    List<MRecipeReport> mRecipeReportList = new ArrayList<>();
    CommonUtils mUtils;
    RecipeReportAdaptor mAdapter;
    LinearLayoutManager mLayoutManager;
    DailyReport mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView = v.findViewById(R.id.recycleRecipeRe);
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
                new fetchRecipeReport().execute();
            }

            @Override
            public void afterTextChanged(Editable s) {
                new fetchRecipeReport().execute();
            }
        });

        new fetchRecipeReport().execute();
        return v;
    }

    class fetchRecipeReport extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mRecipeReportList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mDailyRecipe_dao()
                        .getAllRecipeReport(mActivity.strFromDt, mActivity.strToDt);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mRecipeReportList.size() == 0) {
                mUtils.showInfo("No Report Found");
            }
            LoadReport(mRecipeReportList);

        }
    }

    private void LoadReport(List<MRecipeReport> mRecipeReportList) {
        mAdapter = new RecipeReportAdaptor(getActivity(), mRecipeReportList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }
}