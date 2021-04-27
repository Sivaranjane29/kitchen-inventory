/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment.feed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.activity.report.RolReport;
import com.example.kitcheninventory.adaptor.FeedAdaptor;
import com.example.kitcheninventory.adaptor.Re_ROL_Adapter;
import com.example.kitcheninventory.adaptor.recipe.CollectionsAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class Fragment_feed extends Fragment {
    RecyclerView mRecycleView;
    FeedAdaptor mAdapter;
    List<MItem> mItemList;
    LinearLayout lnrLayoutEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_rol, container, false);

        mRecycleView = root.findViewById(R.id.recycleReport);
        lnrLayoutEmpty = root.findViewById(R.id.lnrLayoutEmpty);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new fetchItemRol().execute();
        return root;
    }

    class fetchItemRol extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mItemList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mItem_dao()
                        .getRolAlert();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mItemList.size() > 0) {
                lnrLayoutEmpty.setVisibility(View.GONE);
                mRecycleView.setVisibility(View.VISIBLE);
                loadDataList(mItemList);
            } else {
                lnrLayoutEmpty.setVisibility(View.VISIBLE);
                mRecycleView.setVisibility(View.GONE);
            }

        }
    }

    private void loadDataList(List<MItem> mItemList) {
        mAdapter = new FeedAdaptor(getActivity(), mItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        new fetchItemRol().execute();
    }
}