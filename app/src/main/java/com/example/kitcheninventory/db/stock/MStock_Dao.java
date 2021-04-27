/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.stock;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.db.master.MItem;
import com.example.kitcheninventory.model.StockRowList;

import java.util.List;

@Dao
public interface MStock_Dao {
    @Query("SELECT * FROM MStock order by StockId")
    List<MStock> getAll();

    @Insert
    void insert(MStock mStock);

    @Insert
    void insertList(List<MStock> mStockList);

    @Delete
    void delete(MStock mStock);

    @Update
    void update(MStock mStock);

    @Query("Delete from mStock")
    void runDelete();

    @Query("SELECT * FROM MStock where ItemId = :lngItemId LIMIT 1")
    MStock getById(long lngItemId);

    @Query("SELECT a.Quantity,a.ItemId, a.ExpiryDate, b.ItemName,b.UnitId,b.Calorie,b.CategoryId,c.UnitName,d.CategoryName,b.IsExpireCheck FROM MStock a " +
            "inner join MItem b on a.ItemId = b.ItemId " +
            "inner join MUnit c on b.UnitId = c.UnitId " +
            "inner join MCategory d on b .CategoryId = d.CategoryId order by ItemName")
    List<StockRowList> getAllStock();

    @Query("Update MStock set Quantity = :dblQty where ItemId=:lngItemId")
    void updateStatus(long lngItemId, double dblQty);

    @Query("SELECT count(StockId) FROM MStock where Quantity=:dblQty")
    int getCount(double dblQty);

}
