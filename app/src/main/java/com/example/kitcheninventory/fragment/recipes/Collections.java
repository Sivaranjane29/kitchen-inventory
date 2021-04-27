/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.fragment.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.recipe.CollectionsAdaptor;

import java.util.ArrayList;


public class Collections extends Fragment {
    RecyclerView mRecycleView;
    CollectionsAdaptor mAdapter;
    ArrayList<String> tittleList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collections, container, false);

        mRecycleView = root.findViewById(R.id.recycler_collections);

        tittleList.add("Healthy Recipes");
        tittleList.add("Slow Cooker");
        tittleList.add("Healthy Recipes");
        tittleList.add("Slow Cooker");
        tittleList.add("Healthy Recipes");
        tittleList.add("Slow Cooker");

        LoadCollections(tittleList);
        return root;
    }

    private void LoadCollections(ArrayList<String> tittleList) {
        mAdapter = new CollectionsAdaptor(getActivity(), tittleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }
}