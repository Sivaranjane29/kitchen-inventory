/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.daily;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.kitcheninventory.model.MItemReport;
import com.example.kitcheninventory.model.MRecipeReport;

import java.util.List;

@Dao
public interface MDailyItem_Dao {

    @Insert
    void insert(MDailyItem mDailyItem);

    @Insert
    void insertList(List<MDailyItem> mDailyItemList);

    @Delete
    void delete(MDailyItem mDailyItem);

    @Update
    void update(MDailyItem mDailyItem);

    @Query("SELECT * FROM MDailyItem where ItemId = :lngItemId and AddedOn=:strDate")
    MDailyItem getById(long lngItemId, String strDate);

    @Query("SELECT count(ReportId) FROM MDailyItem where date(AddedOn) =:strDate")
    int getCount(String strDate);

    @Query("Update MDailyItem set Quantity=:dblQty where ItemId = :lngItemId and AddedOn=:strDate")
    void UpdateById(long lngItemId, String strDate, double dblQty);

    @Query("SELECT a.Quantity, b.ItemName FROM MDailyItem a " +
            "inner join MItem b on a.ItemId = b.ItemId where date(a.AddedOn) between :strFromMDt and :strToDt order by ItemName")
    List<MItemReport> getAllItemReport(String strFromMDt, String strToDt);

}
