/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment.discover;

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
import com.example.kitcheninventory.adaptor.discover.ChipAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.cart.MCart;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.utils.CommonUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class For_U extends Fragment {

    RecyclerView mRecycleView;
    ChipAdaptor mAdapter;
    List<MCart> cartList = new ArrayList<>();
    MaterialButton btnRemindMe;
    CommonUtils mUtils;
    LinearLayout lnrLayoutEmpty;
    List<MItem> mItemList;
    FloatingActionButton fabShopAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_shop_item, container, false);

        mUtils = new CommonUtils(getActivity());

        btnRemindMe = v.findViewById(R.id.btnRemindMe);
        fabShopAdd = v.findViewById(R.id.fabShopAdd);
        lnrLayoutEmpty = v.findViewById(R.id.lnrLayoutEmpty);
        mRecycleView = v.findViewById(R.id.recycleShopItemList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabShopAdd.setVisibility(View.GONE);

        btnRemindMe.setOnClickListener(v1 -> {
            mUtils.showSuccess("Set Reminder");
        });

        new FetchCart().execute();

        return v;
    }


    private void loadDataList(List<MCart> LIST) {
        if (LIST.size() > 0) {
            lnrLayoutEmpty.setVisibility(View.GONE);
            btnRemindMe.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.VISIBLE);

            mAdapter = new ChipAdaptor(getActivity(), LIST);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecycleView.setLayoutManager(layoutManager);
            mRecycleView.setAdapter(mAdapter);
        } else {
            mRecycleView.setVisibility(View.GONE);
            lnrLayoutEmpty.setVisibility(View.VISIBLE);
            btnRemindMe.setVisibility(View.GONE);
        }
    }

    class FetchCart extends AsyncTask<Void, Void, Void> {
        boolean IsError = false;
        String strError = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mItemList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mItem_dao()
                        .getRolAlert();

                DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mCart_dao()
                        .runDelete();

                if (mItemList.size() > 0) {
                    for (int i = 0; i < mItemList.size(); i++) {
                        MCart mCart = new MCart();
                        mCart.setItemName(mItemList.get(i).getItemName());

                        DatabaseClient
                                .getInstance(getActivity())
                                .getAppDatabase()
                                .mCart_dao()
                                .insert(mCart);
                    }

                }

                cartList = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .mCart_dao()
                        .getAll();

            } catch (Exception e) {
                IsError = true;
                strError = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (cartList.size() > 0) {
                loadDataList(cartList);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new FetchCart().execute();
    }
}