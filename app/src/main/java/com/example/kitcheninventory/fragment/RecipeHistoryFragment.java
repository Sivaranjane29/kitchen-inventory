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
import com.example.kitcheninventory.adaptor.RecipeHistoryAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.model.MRecipeHistoryList;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.MyHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecipeHistoryFragment extends Fragment {
    RecyclerView mRecycleView;
    List<MRecipeHistoryList> mRecipeHistoryList = new ArrayList<>();
    CommonUtils mUtils;
    RecipeHistoryAdaptor mAdapter;
    LinearLayoutManager mLayoutManager;
    Calendar mCalendarDt = Calendar.getInstance();
    int mYear1, mMonth1, mDay1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_history, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView = view.findViewById(R.id.recycleRecipeHistory);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(mLayoutManager);
        mUtils = new CommonUtils(getActivity());


        new fetchRecipeHistory().execute();
        return view;
    }


    class fetchRecipeHistory extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mRecipeHistoryList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mRecipeHistory_dao()
                        .getRecipeHistory();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mRecipeHistoryList.size() > 0) {
                LoadHistory(mRecipeHistoryList);
            } else {
                mUtils.showInfo("No History Found");
            }

        }
    }

    private void LoadHistory(List<MRecipeHistoryList> mRecipeHistoryList) {
        mAdapter = new RecipeHistoryAdaptor(getActivity(), mRecipeHistoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }
}
