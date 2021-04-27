/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MCategory_Dao {

    @Query("SELECT * FROM MCategory order by CategoryName")
    List<MCategory> getAll();

    @Insert
    void insert(MCategory mCategory);

    @Insert
    void insertList(List<MCategory> mCategory);

    @Delete
    void delete(MCategory mCategory);

    @Update
    void update(MCategory mCategory);

    @Query("Delete from mCategory")
    void runDelete();

    @Query("SELECT * FROM MCategory where CategoryName like :strName order by CategoryName")
    List<MCategory> getAll(String strName);

//    @Query("SELECT * FROM MSupplier where Active = 1")
//    List<MSupplier> getActiveSuppliers();

    @Query("SELECT * FROM MCategory where CategoryName = :strName LIMIT 1")
    MCategory getByName(String strName);

    @Query("SELECT * FROM MCategory where CategoryId = :intCategoryID LIMIT 1")
    MCategory getById(int intCategoryID);

    @Query("SELECT * FROM MCategory where CategoryName = :strCategoryName and CategoryId <> :intCategoryID LIMIT 1")
    MCategory getNameOtherThanId(String strCategoryName, int intCategoryID);
}
