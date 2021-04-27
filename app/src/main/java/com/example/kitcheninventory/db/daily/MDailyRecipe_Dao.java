/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.daily;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.kitcheninventory.model.MPurchaseSummaryList;
import com.example.kitcheninventory.model.MRecipeReport;
import com.example.kitcheninventory.model.MRowItemList;

import java.util.List;

@Dao
public interface MDailyRecipe_Dao {
    @Insert
    void insert(MDailyRecipe mDailyRecipe);

    @Insert
    void insertList(List<MDailyRecipe> mDailyRecipeList);

    @Delete
    void delete(MDailyRecipe mDailyRecipe);

    @Update
    void update(MDailyRecipe mDailyRecipe);

    @Query("SELECT * FROM MDailyRecipe where RecipeSummaryId = :lngRecipeId")
    MDailyRecipe getById(long lngRecipeId);

    @Query("SELECT count(RecipeReportId) FROM MDailyRecipe where date(AddedOn) =:strDate")
    int getCount(String strDate);

    @Query("Update MDailyRecipe set Quantity=:dblQty where RecipeSummaryId = :lngRecipeId and AddedOn=:strDate")
    void UpdateById(long lngRecipeId, String strDate, double dblQty);

    @Query("SELECT a.Quantity, a.AddedOn, a.ItemCount, b.Name FROM MDailyRecipe a " +
            "inner join MRecipeSummary b on a.RecipeSummaryId = b.RecipeSummaryId  where date(a.AddedOn) between :strFromMDt and :strToDt order by Name")
    List<MRecipeReport> getAllRecipeReport(String strFromMDt, String strToDt);


}
