/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.shop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MShop_Dao {

    @Query("SELECT * FROM MShop order by ShopName")
    List<MShop> getAll();

    @Insert
    void insert(MShop mShop);

    @Insert
    void insertList(List<MShop> mShops);

    @Delete
    void delete(MShop mShop);

    @Update
    void update(MShop mShop);

    @Query("Delete from MShop")
    void runDelete();

    @Query("SELECT * FROM MShop where ShopName like :strShopName order by ShopName")
    List<MShop> getAll(String strShopName);

//    @Query("SELECT * FROM MSupplier where Active = 1")
//    List<MSupplier> getActiveSuppliers();

    @Query("SELECT * FROM MShop where ShopName = :strShopName LIMIT 1")
    MShop getByName(String strShopName);

    @Query("SELECT * FROM MShop where ShopID = :intShopId LIMIT 1")
    MShop getById(int intShopId);

    @Query("SELECT * FROM MShop where ShopName = :strUnitName and ShopID <> :intShopId LIMIT 1")
    MShop getNameOtherThanId(String strUnitName, int intShopId);
}
