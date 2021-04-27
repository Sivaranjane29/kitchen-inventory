/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master.purchase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.kitcheninventory.model.MItemHistory;
import com.example.kitcheninventory.model.MPurchaseSummaryList;

import java.util.List;

@Dao
public interface MPurchaseSummary_Dao {

    @Query("SELECT * FROM MPurchaseSummary order by SerialNumber")
    List<MPurchaseSummary> getAll();

    @Insert
    long insert(MPurchaseSummary mPurchaseSummary);

    @Insert
    void insertList(List<MPurchaseSummary> mPurchaseSummary);

    @Delete
    void delete(MPurchaseSummary mPurchaseSummary);

    @Update
    void update(MPurchaseSummary mPurchaseSummary);

    @Query("Delete from mPurchaseSummary")
    void runDelete();

    @Query("SELECT * FROM MPurchaseSummary where SerialNumber = :strSlNo and SupplierId = :intSupId limit 1")
    MPurchaseSummary getDuplicate(String strSlNo, int intSupId);

    @Query("SELECT * FROM MPurchaseSummary where SerialNumber =:strSlNo ")
    MPurchaseSummary getByID(String strSlNo);

    @Query("SELECT count(PurchaseSummaryId) FROM MPurchaseSummary where date(PurchaseDate) =:strDate")
    int getCount(String strDate);

    @Query("SELECT a.SerialNumber, a.VoucherNumber, a.PurchaseDate, a.PurchaseSummaryId, b.Name FROM MPurchaseSummary a " +
            "inner join MSupplier b on a.SupplierId = b.SupplierId where date(PurchaseDate) between :strFromMDt and :strToDt")
    List<MPurchaseSummaryList> getPurchaseSummary(String strFromMDt, String strToDt);

}
