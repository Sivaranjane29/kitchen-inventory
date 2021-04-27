/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.model.MPurchaseList;
import com.example.kitcheninventory.model.MRecipeStock;
import com.example.kitcheninventory.model.MRowItemList;
import com.example.kitcheninventory.model.StockConsumption;

import java.util.List;

@Dao
public interface MItem_Dao {

    @Query("SELECT * FROM MItem order by ItemName")
    List<MItem> getAll();

    @Insert
    long insert(MItem mItem);

    @Insert
    void insertList(List<MItem> mItem);

    @Delete
    void delete(MItem mItem);

    @Update
    void update(MItem mItem);

    @Query("Delete from mItem")
    void runDelete();

    @Query("SELECT * FROM MItem where ItemName like :strName order by ItemName")
    List<MItem> getAll(String strName);

//    @Query("SELECT * FROM MSupplier where Active = 1")
//    List<MSupplier> getActiveSuppliers();

    @Query("SELECT * FROM MItem where ItemName = :strName  LIMIT 1")
    MItem getByName(String strName);

    @Query("SELECT * FROM MItem where ItemId = :intItemId LIMIT 1")
    MItem getById(long intItemId);

    @Query("SELECT * FROM MItem where ItemName = :strIteName and ItemId <> :intItemId LIMIT 1")
    MItem getNameOtherThanId(String strIteName, int intItemId);

    @Query("SELECT a.ItemId, a.ItemName, a.Calorie,  b.CategoryName, c.UnitName, c.UnitId FROM MItem a " +
            "inner join MCategory b on a.CategoryId = b.CategoryId " +
            "inner join MUnit c on a.UnitId = c.UnitId order by ItemName")
    List<MPurchaseList> getAllItemsForPurchase();

    @Query("SELECT a.ItemId, a.ItemName, a.Calorie, a.Description, b.CategoryName, c.UnitName FROM MItem a " +
            "inner join MCategory b on a.CategoryId = b.CategoryId " +
            "inner join MUnit c on a.UnitId = c.UnitId order by ItemName")
    List<MRowItemList> getAllItems();

    @Query("SELECT a.ItemId, a.ItemName, a.Calorie, b.Quantity FROM MItem a " +
            "inner join MStock b on a.ItemId = b.ItemId where a.ItemId =:lngItemId  LIMIT 1")
    StockConsumption getItemStock(long lngItemId);

    @Query("SELECT count(ItemId) FROM MItem")
    int getCount();

    @Query("UPDATE  MItem SET CurrentStock =:updateStockQty Where ItemId =:intItemId")
    void updateCount(double updateStockQty, long intItemId);

    @Query("SELECT * FROM  MItem Where CurrentStock <= ROL")
    List<MItem> getRolAlert();

    @Query("SELECT * FROM  MItem Where CurrentStock <= ROL and ItemId=:lngItemId LIMIT 1")
    MItem getStockNotify(long lngItemId);
}
