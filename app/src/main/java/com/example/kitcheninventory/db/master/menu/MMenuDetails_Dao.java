/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.menu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.model.MRecipeStock;

import java.util.List;

@Dao
public interface MMenuDetails_Dao {
    @Query("SELECT * FROM MMenuDetails order by RecipeId")
    List<MMenuDetails> getAll();

    @Insert
    void insert(MMenuDetails mUnit);

    @Insert
    void insertList(List<MMenuDetails> mUnit);

    @Delete
    void delete(MMenuDetails mUnit);

    @Update
    void update(MMenuDetails mUnit);

    @Query("Delete from mUnit")
    void runDelete();

    @Query("SELECT a.MenuSummaryId, a.RecipeId, b.Name ,b.Time FROM MMenuDetails a " +
            "inner join MRecipeSummary b on a.RecipeId = b.RecipeSummaryId where MenuSummaryId=:lngMenuId order by Name")
    List<MMenuRecipeList> getAllItems(long lngMenuId);

    @Query("delete  FROM MMenuDetails where MenuSummaryId=:lngMenuId and RecipeId = :RecipeId")
    void rowDelete(long lngMenuId, long RecipeId);

    @Query("SELECT * FROM MMenuDetails where MenuSummaryId=:lngMenuId and RecipeId = :RecipeId")
    MMenuDetails getRowById(long lngMenuId, long RecipeId);

    @Query("SELECT count(MenuSummaryId) FROM MMenuDetails where MenuSummaryId=:lngMenuId")
    int getCount(long lngMenuId);

}
