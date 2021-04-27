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
public interface MSupplier_Dao {

    @Query("SELECT * FROM MSupplier order by Name")
    List<MSupplier> getAll();

    @Insert
    void insert(MSupplier mSupplier);

    @Insert
    void insertList(List<MSupplier> mSupplier);

    @Delete
    void delete(MSupplier mSupplier);

    @Update
    void update(MSupplier mSupplier);

    @Query("Delete from mSupplier")
    void runDelete();

    @Query("SELECT * FROM MSupplier where Name like :strName order by Name")
    List<MSupplier> getAll(String strName);

//    @Query("SELECT * FROM MSupplier where Active = 1")
//    List<MSupplier> getActiveSuppliers();

    @Query("SELECT * FROM MSupplier where Name = :strName LIMIT 1")
    MSupplier getByName(String strName);

    @Query("SELECT * FROM MSupplier where SupplierID = :intSupplierID LIMIT 1")
    MSupplier getById(int intSupplierID);

    @Query("SELECT * FROM MSupplier where Name = :strSupplierName and SupplierID <> :intSupplierID LIMIT 1")
    MSupplier getNameOtherThanId(String strSupplierName, int intSupplierID);

}
