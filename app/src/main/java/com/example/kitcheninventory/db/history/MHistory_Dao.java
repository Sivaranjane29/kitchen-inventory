/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.history;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.model.MItemHistory;

import java.util.List;

@Dao
public interface MHistory_Dao {

    @Query("SELECT * FROM MHistory")
    List<MHistory> getAll();

    @Insert
    void insert(MHistory mHistory);

    @Insert
    void insertList(List<MHistory> mHistoryList);

    @Delete
    void delete(MHistory mHistory);

    @Update
    void update(MHistory mHistory);

    @Query("Delete from MHistory")
    void runDelete();

    @Query("SELECT a.RecipeId,a.Quantity,a.IsDecrease, a.IsIncrease,a.IsRecipe,a.AddedOn,b.ItemName FROM MHistory a " +
            "inner join MItem b on a.ItemId = b.ItemId order by HistoryId DESC")
    List<MItemHistory> getItemHistory();

}
