/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.kitcheninventory.db.cart.MCart;
import com.example.kitcheninventory.db.cart.MCart_Dao;
import com.example.kitcheninventory.db.daily.MDailyItem;
import com.example.kitcheninventory.db.daily.MDailyItem_Dao;
import com.example.kitcheninventory.db.daily.MDailyRecipe;
import com.example.kitcheninventory.db.daily.MDailyRecipe_Dao;
import com.example.kitcheninventory.db.history.MHistory;
import com.example.kitcheninventory.db.history.MHistory_Dao;
import com.example.kitcheninventory.db.history.MRecipeHistory;
import com.example.kitcheninventory.db.history.MRecipeHistory_Dao;
import com.example.kitcheninventory.db.master.MCategory;
import com.example.kitcheninventory.db.master.MCategory_Dao;
import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.db.master.MItem_Dao;
import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.db.master.menu.MMenu_Dao;
import com.example.kitcheninventory.db.master.MSupplier;
import com.example.kitcheninventory.db.master.MSupplier_Dao;
import com.example.kitcheninventory.db.master.MUnit;
import com.example.kitcheninventory.db.master.MUnit_Dao;
import com.example.kitcheninventory.db.master.menu.MMenuDetails;
import com.example.kitcheninventory.db.master.menu.MMenuDetails_Dao;
import com.example.kitcheninventory.db.master.purchase.MPurchaseDetails;
import com.example.kitcheninventory.db.master.purchase.MPurchaseDetails_Dao;
import com.example.kitcheninventory.db.master.purchase.MPurchaseSummary;
import com.example.kitcheninventory.db.master.purchase.MPurchaseSummary_Dao;
import com.example.kitcheninventory.db.master.recipe.MRecipeDetails;
import com.example.kitcheninventory.db.master.recipe.MRecipeDetails_Dao;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary_Dao;
import com.example.kitcheninventory.db.master.recipe.MRecipeSummary;
import com.example.kitcheninventory.db.master.shop.MShop;
import com.example.kitcheninventory.db.master.shop.MShop_Dao;
import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.db.stock.MStock_Dao;

@Database(entities =
        {
                MCategory.class,
                MItem.class,
                MSupplier.class,
                MUnit.class,
                MPurchaseSummary.class,
                MPurchaseDetails.class,
                MRecipeSummary.class,
                MRecipeDetails.class,
                MStock.class,
                MMenu.class,
                MMenuDetails.class,
                MDailyItem.class,
                MDailyRecipe.class,
                MHistory.class,
                MRecipeHistory.class,
                MShop.class,
                MCart.class

        }, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract MCategory_Dao mCategory_dao();

    public abstract MItem_Dao mItem_dao();

    public abstract MSupplier_Dao mSupplier_dao();

    public abstract MUnit_Dao mUnit_dao();

    public abstract MPurchaseSummary_Dao mPurchaseSummary_dao();

    public abstract MPurchaseDetails_Dao mPurchaseDetails_dao();

    public abstract MRecipeSummary_Dao mRecipeSummary_dao();

    public abstract MRecipeDetails_Dao mRecipeDetails_dao();

    public abstract MStock_Dao mStock_dao();

    public abstract MMenu_Dao mMenu_dao();

    public abstract MMenuDetails_Dao mMenuDetails_dao();

    public abstract MDailyItem_Dao mDailyItem_dao();

    public abstract MDailyRecipe_Dao mDailyRecipe_dao();

    public abstract MHistory_Dao mHistory_dao();

    public abstract MRecipeHistory_Dao mRecipeHistory_dao();

    public abstract MCart_Dao mCart_dao();

    public abstract MShop_Dao mShop_dao();

}