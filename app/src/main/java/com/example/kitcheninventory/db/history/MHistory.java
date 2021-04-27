/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.history;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class MHistory {
   /*
   HistoryId
   ItemId
   RecipeId
   Quantity
   IsIncrease
   IsDecrease
   IsRecipe
   BranchId
   ServerId
   IsPosted
   PostedDate
   AddedOn
   AddedBy
   ModifiedBy
   ModifiedOn
   IsDeleted
   DeletedBy
   DeletedOn

    */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "HistoryId")
    private long HistoryId;

    @ColumnInfo(name = "ItemId")
    private long ItemId;

    @ColumnInfo(name = "Quantity")
    private double Quantity;

    @ColumnInfo(name = "RecipeId")
    private long RecipeId;

    @ColumnInfo(name = "IsIncrease")
    public boolean IsIncrease;

    @ColumnInfo(name = "IsDecrease")
    public boolean IsDecrease;

    @ColumnInfo(name = "IsRecipe")
    public boolean IsRecipe;

    @ColumnInfo(name = "ServerId")
    private int ServerId;

    @ColumnInfo(name = "BranchId")
    private int BranchId;

    @ColumnInfo(name = "IsPosted")
    public boolean IsPosted;

    @ColumnInfo(name = "PostedDt")
    private String PostedDt;

    @ColumnInfo(name = "AddedOn")
    private String AddedOn;

    @ColumnInfo(name = "AddedBy")
    private String AddedBy;

    @ColumnInfo(name = "ModifiedOn")
    private String ModifiedOn;

    @ColumnInfo(name = "ModifiedBy")
    private String ModifiedBy;

    @ColumnInfo(name = "IsDeleted")
    public boolean IsDeleted;

    @ColumnInfo(name = "DeletedOn")
    private String DeletedOn;

    @ColumnInfo(name = "DeletedBy")
    private String DeletedBy;

    public long getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(long recipeId) {
        RecipeId = recipeId;
    }

    public boolean isRecipe() {
        return IsRecipe;
    }

    public void setRecipe(boolean recipe) {
        IsRecipe = recipe;
    }

    public long getHistoryId() {
        return HistoryId;
    }

    public void setHistoryId(long historyId) {
        HistoryId = historyId;
    }

    public long getItemId() {
        return ItemId;
    }

    public void setItemId(long itemId) {
        ItemId = itemId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public boolean isIncrease() {
        return IsIncrease;
    }

    public void setIncrease(boolean increase) {
        IsIncrease = increase;
    }

    public boolean isDecrease() {
        return IsDecrease;
    }

    public void setDecrease(boolean decrease) {
        IsDecrease = decrease;
    }

    public int getServerId() {
        return ServerId;
    }

    public void setServerId(int serverId) {
        ServerId = serverId;
    }

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int branchId) {
        BranchId = branchId;
    }

    public boolean isPosted() {
        return IsPosted;
    }

    public void setPosted(boolean posted) {
        IsPosted = posted;
    }

    public String getPostedDt() {
        return PostedDt;
    }

    public void setPostedDt(String postedDt) {
        PostedDt = postedDt;
    }

    public String getAddedOn() {
        return AddedOn;
    }

    public void setAddedOn(String addedOn) {
        AddedOn = addedOn;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public void setAddedBy(String addedBy) {
        AddedBy = addedBy;
    }

    public String getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        ModifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getDeletedOn() {
        return DeletedOn;
    }

    public void setDeletedOn(String deletedOn) {
        DeletedOn = deletedOn;
    }

    public String getDeletedBy() {
        return DeletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        DeletedBy = deletedBy;
    }
}
