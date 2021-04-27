/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.kitcheninventory.activity.daily.DailyRecipe;
import com.example.kitcheninventory.activity.dailyconsumption.DailyConsumption;
import com.example.kitcheninventory.activity.masters.cat.CategoryList;
import com.example.kitcheninventory.activity.masters.item.ItemList;
import com.example.kitcheninventory.activity.masters.menu.MenuList;
import com.example.kitcheninventory.activity.masters.sup.SupplierList;
import com.example.kitcheninventory.activity.masters.unit.UnitList;
import com.example.kitcheninventory.activity.myprofile.MyProfile;
import com.example.kitcheninventory.activity.purchase.Purchase;
import com.example.kitcheninventory.activity.recipe.RecipeList;
import com.example.kitcheninventory.activity.report.DailyReport;
import com.example.kitcheninventory.activity.report.RolReport;
import com.example.kitcheninventory.activity.report.Stock;
import com.example.kitcheninventory.activity.report.purchase.PurchaseSummary;
import com.example.kitcheninventory.activity.search.Search;
import com.example.kitcheninventory.activity.shopthings.MarketList;
import com.example.kitcheninventory.activity.shopthings.ShopItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;


public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navView = findViewById(R.id.nav_bottom_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_for_you, R.id.navigation_feed, R.id.navigation_recipe, R.id.navigation_favourite)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        View hView = navigationView.getHeaderView(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            startActivity(new Intent(DashBoard.this, Search.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_Menu) {
            startActivity(new Intent(DashBoard.this, MenuList.class));
        } else if (id == R.id.nav_Unit) {
            startActivity(new Intent(DashBoard.this, UnitList.class));
        } else if (id == R.id.nav_Category) {
            startActivity(new Intent(DashBoard.this, CategoryList.class));
        } else if (id == R.id.nav_Supplier) {
            startActivity(new Intent(DashBoard.this, SupplierList.class));
        } else if (id == R.id.nav_Item) {
            startActivity(new Intent(DashBoard.this, ItemList.class));
        } else if (id == R.id.nav_Recipe) {
            startActivity(new Intent(DashBoard.this, RecipeList.class));
        } else if (id == R.id.nav_Purchase) {
            startActivity(new Intent(DashBoard.this, Purchase.class));
        } else if (id == R.id.nav_Inventory) {
            startActivity(new Intent(DashBoard.this, Stock.class));
        } else if (id == R.id.nav_ROL) {
            startActivity(new Intent(DashBoard.this, RolReport.class));
        } else if (id == R.id.nav_Daily) {
            startActivity(new Intent(DashBoard.this, DailyRecipe.class));
        } else if (id == R.id.nav_Daily_Item) {
            startActivity(new Intent(DashBoard.this, DailyConsumption.class));
        } else if (id == R.id.nav_Daily_Report) {
            startActivity(new Intent(DashBoard.this, DailyReport.class));
        } else if (id == R.id.nav_Purchase_Report) {
            startActivity(new Intent(DashBoard.this, PurchaseSummary.class));
        } else if (id == R.id.nav_shoplist) {
            startActivity(new Intent(DashBoard.this, MarketList.class));
        } else if (id == R.id.nav_cartList) {
            startActivity(new Intent(DashBoard.this, ShopItem.class));
        } else if (id == R.id.nav_Profile) {
            startActivity(new Intent(DashBoard.this, MyProfile.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}