/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.menu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface MMenu_Dao {
    @Query("SELECT * FROM MMenu order by MenuName")
    List<MMenu> getAll();

    @Insert
    long insert(MMenu mMenu);

    @Insert
    void insertList(List<MMenu> mMenu);

    @Delete
    void delete(MMenu mMenu);

    @Update
    void update(MMenu mMenu);

    @Query("Delete from mMenu")
    void runDelete();

    @Query("SELECT * FROM MMenu where MenuId = :lngMenuId LIMIT 1")
    MMenu getById(long lngMenuId);

    @Query("SELECT * FROM MMenu where MenuId = :intMenuId ")
    List<MMenu> getMenuById(int intMenuId);

    @Query("SELECT * FROM MMenu where MenuName = :strMenuName LIMIT 1")
    MMenu getByName(String strMenuName);

    @Query("SELECT * FROM MMenu where MenuName = :strMenuName and MenuId <> :lngMenuId LIMIT 1")
    MMenu getNameOtherThanId(String strMenuName, long lngMenuId);

    @Query("SELECT count(MenuId) FROM MMenu")
    int getCount();

    @Query("SELECT * FROM MMenu where MenuName like :strName  order by MenuName")
    List<MMenu> getAllFilter(String strName);

}
