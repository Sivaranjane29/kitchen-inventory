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
public interface MUnit_Dao {
    @Query("SELECT * FROM MUnit order by UnitName")
    List<MUnit> getAll();

    @Insert
    void insert(MUnit mUnit);

    @Insert
    void insertList(List<MUnit> mUnit);

    @Delete
    void delete(MUnit mUnit);

    @Update
    void update(MUnit mUnit);

    @Query("Delete from mUnit")
    void runDelete();


    @Query("SELECT * FROM MUnit where UnitName like :strUnitName order by UnitName")
    List<MUnit> getAll(String strUnitName);

//    @Query("SELECT * FROM MSupplier where Active = 1")
//    List<MSupplier> getActiveSuppliers();

    @Query("SELECT * FROM MUnit where UnitName = :strUnitName LIMIT 1")
    MUnit getByName(String strUnitName);

    @Query("SELECT * FROM MUnit where UnitId = :intUnitId LIMIT 1")
    MUnit getById(int intUnitId);

    @Query("SELECT * FROM MUnit where UnitName = :strUnitName and UnitId <> :intUnitId LIMIT 1")
    MUnit getNameOtherThanId(String strUnitName, int intUnitId);
}
