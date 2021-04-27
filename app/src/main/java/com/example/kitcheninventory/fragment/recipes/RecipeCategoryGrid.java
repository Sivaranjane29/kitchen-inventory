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
import androidx.viewpager.widget.ViewPager;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.ViewPagerAdapter;
import com.example.kitcheninventory.fragment.ItemHistoryFragment;
import com.example.kitcheninventory.fragment.RecipeHistoryFragment;
import com.google.android.material.tabs.TabLayout;

public class RecipeCategoryGrid extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter mAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipes, container, false);

        tabLayout = root.findViewById(R.id.tabs);
        viewPager = root.findViewById(R.id.viewpager);
        mAdaptor = new ViewPagerAdapter(getChildFragmentManager());
        mAdaptor.addFragment(new Category_Grid(), "");
        viewPager.setAdapter(mAdaptor);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }
}