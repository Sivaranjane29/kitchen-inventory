/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.recipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.db.master.menu.MMenuDetails;
import com.example.kitcheninventory.model.MMenuRecipeList;
import com.example.kitcheninventory.model.MPurchaseRow;
import com.example.kitcheninventory.model.MRecipeStock;

import java.util.List;

@Dao
public interface MRecipeDetails_Dao {
    @Query("SELECT * FROM MRecipeDetails order by RecipeDetailsId")
    List<MRecipeDetails> getAll();

    @Insert
    void insert(MRecipeDetails mRecipeSummary);

    @Insert
    void insertList(List<MRecipeDetails> mRecipeSummary);

    @Delete
    void delete(MRecipeDetails mRecipeSummary);

    @Update
    void update(MRecipeDetails mRecipeSummary);

    @Query("Delete from MRecipeSummary")
    void runDelete();

    @Query("SELECT a.RecipeSummaryId,a.ItemId,a.UnitId,Qty, b.ItemName,b.Calorie,c.Quantity FROM MRecipeDetails a " +
            "inner join MItem b on a.ItemId = b.ItemId " +
            "inner join MStock c on a.ItemId = c.ItemId where RecipeSummaryId =:lngRecipeId order by ItemName")
    List<MRecipeStock> getAllIngredients(long lngRecipeId);

    @Query("SELECT a.ItemId, a.UnitId, a.Qty, b.ItemName, b.Calorie, c.UnitName FROM MRecipeDetails a " +
            "inner join MItem b on a.ItemId = b.ItemId " +
            "inner join MUnit c on a.UnitId =c.UnitId where a.RecipeSummaryId =:lngRecipeId order by ItemName")
    List<MPurchaseRow> getSavedIngredients(long lngRecipeId);

    @Query("SELECT * FROM MRecipeDetails where RecipeSummaryId=:lngSummaryId and ItemId = :lngItemId")
    MRecipeDetails getRowById(long lngSummaryId, long lngItemId);

    @Query("delete  FROM MRecipeDetails where RecipeSummaryId=:lngSummaryId and ItemId = :lngItemId")
    void rowDelete(long lngSummaryId, long lngItemId);
}
