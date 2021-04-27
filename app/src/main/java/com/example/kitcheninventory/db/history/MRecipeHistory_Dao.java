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
import com.example.kitcheninventory.model.MRecipeHistoryList;

import java.util.List;

@Dao
public interface MRecipeHistory_Dao {

    @Query("SELECT * FROM MRecipeHistory")
    List<MRecipeHistory> getAll();

    @Insert
    void insert(MRecipeHistory mRecipeHistory);

    @Insert
    void insertList(List<MRecipeHistory> mRecipeHistoryList);

    @Delete
    void delete(MRecipeHistory mRecipeHistory);

    @Update
    void update(MRecipeHistory mRecipeHistory);

    @Query("Delete from MRecipeHistory")
    void runDelete();

    @Query("SELECT a.RecipeId,a.Quantity,a.AddedOn,b.Name FROM MRecipeHistory a " +
            "inner join MRecipeSummary b on a.RecipeId = b.RecipeSummaryId order by RecipeHistoryId DESC")
    List<MRecipeHistoryList> getRecipeHistory();

}
