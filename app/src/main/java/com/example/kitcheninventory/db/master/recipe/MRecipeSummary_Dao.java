/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.recipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.db.master.menu.MMenu;
import com.example.kitcheninventory.db.master.purchase.MPurchaseSummary;

import java.util.List;

@Dao
public interface MRecipeSummary_Dao {
    @Query("SELECT * FROM MRecipeSummary order by Name")
    List<MRecipeSummary> getAll();

    @Insert
    long insert(MRecipeSummary mRecipeSummary);

    @Insert
    void insertList(List<MRecipeSummary> mRecipeSummary);

    @Delete
    void delete(MRecipeSummary mRecipeSummary);

    @Update
    void update(MRecipeSummary mRecipeSummary);

    @Query("Delete from MRecipeSummary")
    void runDelete();


    @Query("SELECT * FROM MRecipeSummary where Name = :strName limit 1")
    MRecipeSummary getDuplicate(String strName);

    @Query("SELECT * FROM MRecipeSummary where RecipeSummaryId =:LngSummaryId limit 1")
    MRecipeSummary getByID(Long LngSummaryId);

    @Query("SELECT count(RecipeSummaryId) FROM MRecipeSummary")
    int getCount();

    @Query("SELECT * FROM MRecipeSummary where Name = :strRecpeName and RecipeSummaryId <> :lngRecipeId LIMIT 1")
    MRecipeSummary getNameOtherThanId(String strRecpeName, long lngRecipeId);
}
