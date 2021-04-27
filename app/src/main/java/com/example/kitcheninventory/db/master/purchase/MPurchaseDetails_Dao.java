/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.purchase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kitcheninventory.db.stock.MStock;
import com.example.kitcheninventory.model.MPurchaseList;
import com.example.kitcheninventory.model.MRePurchaseDetails;

import java.util.List;

@Dao
public interface MPurchaseDetails_Dao {
    @Query("SELECT * FROM MPurchaseDetails order by PurchasedDetailsId")
    List<MPurchaseDetails> getAll();

    @Insert
    void insert(MPurchaseDetails mPurchaseDetails);

    @Insert
    void insertList(List<MPurchaseDetails> mPurchaseDetails);

    @Delete
    void delete(MPurchaseDetails mPurchaseDetails);

    @Update
    void update(MPurchaseDetails mPurchaseDetails);

    @Query("Delete from mPurchaseDetails")
    void runDelete();

    @Query("SELECT * FROM MPurchaseDetails where PurchaseSummaryId = :lngSummaryId")
    List<MPurchaseDetails> getById(long lngSummaryId);

    @Query("SELECT a.Quantity,b.ItemName,b.Calorie,c.UnitName FROM MPurchaseDetails a " +
            "inner join MItem b on a.ItemId = b.ItemId " +
            "inner join MUnit c on b.UnitId = c.UnitId where PurchaseSummaryId=:lngSummaryId order by ItemName")
    List<MRePurchaseDetails> getPurchaseDetailsById(long lngSummaryId);
}
