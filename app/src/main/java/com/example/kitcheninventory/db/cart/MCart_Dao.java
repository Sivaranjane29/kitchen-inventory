/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface MCart_Dao {

    @Query("SELECT * FROM MCart")
    List<MCart> getAll();

    @Insert
    void insert(MCart mCart);

    @Insert
    void insertList(List<MCart> mCartList);

    @Delete
    void delete(MCart mCart);

    @Update
    void update(MCart mCart);

    @Query("Delete from mCart")
    void runDelete();

    @Query("SELECT * FROM MCart where ItemName=:strName")
    MCart getRowById(String strName);

}
