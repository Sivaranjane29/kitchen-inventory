/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.activity.masters.menu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitcheninventory.R;
import com.example.kitcheninventory.adaptor.MenuAdaptor;
import com.example.kitcheninventory.db.DatabaseClient;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.utils.CommonUtils;
import com.example.kitcheninventory.utils.PrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuList extends AppCompatActivity {
    private CommonUtils mUtils;
    private PrefManager mPref;
    RecyclerView mRecycleView;
    List<MMenu> mMenuList = new ArrayList<>();
    MenuAdaptor mAdapter;
    SearchView src;
    String strSearchName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setContentView(R.layout.activity_itemlist);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu");

        mUtils = new CommonUtils(MenuList.this);
        mPref = new PrefManager(MenuList.this);

        mRecycleView = findViewById(R.id.recycleItemList);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddCus = findViewById(R.id.fabItemAdd);
        fabAddCus.setOnClickListener(v -> {
            startActivity(new Intent(MenuList.this, AddMenu.class));
        });

        //fetchMenu
        new FetchMenu().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        src = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        src.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                strSearchName = newText;
                new FetchMenu().execute();
                return false;
            }
        });

        return true;
    }


    class FetchMenu extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mMenuList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .mMenu_dao()
                        .getAllFilter("%" + strSearchName + "%");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mMenuList.size() == 0) {
                mUtils.showInfo("No item Found");
            }
            loadDataList(mMenuList);
        }
    }

    private void loadDataList(List<MMenu> mMenuList) {
        mAdapter = new MenuAdaptor(MenuList.this, mMenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MenuList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchMenu().execute();
    }
}
