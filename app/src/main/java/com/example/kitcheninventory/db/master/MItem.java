/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MItem {
 /*
   ItemId
   ItemName
   Description
   Calorie
   CurrentStock
   ROL
   UnitId
   CategoryId
   BranchId
   ServerId
   IsPosted
   PostedDate
   ExpiryDate
   IsExpireCheck
   AddedOn
   AddedBy
   ModifiedBy
   ModifiedOn
   IsDeleted
   DeletedBy
   DeletedOn

  */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ItemId")
    private long ItemId;

    @ColumnInfo(name = "ItemName")
    private String ItemName;

    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "Calorie")
    private double Calorie;

    @ColumnInfo(name = "CurrentStock")
    private double CurrentStock;

    @ColumnInfo(name = "UnitId")
    private int UnitId;

    @ColumnInfo(name = "CategoryId")
    private int CategoryId;

    @ColumnInfo(name = "ServerId")
    private int ServerId;

    @ColumnInfo(name = "BranchId")
    private int BranchId;

    @ColumnInfo(name = "ROL")
    private int ROL;

    @ColumnInfo(name = "IsPosted")
    public boolean IsPosted;

    @ColumnInfo(name = "PostedDt")
    private String PostedDt;

    @ColumnInfo(name = "IsActive")
    public boolean IsActive;

    @ColumnInfo(name = "AddedOn")
    private String AddedOn;

    @ColumnInfo(name = "ExpiryDate")
    private String ExpiryDate;

    @ColumnInfo(name = "AddedBy")
    private String AddedBy;

    @ColumnInfo(name = "ModifiedOn")
    private String ModifiedOn;

    @ColumnInfo(name = "ModifiedBy")
    private String ModifiedBy;

    @ColumnInfo(name = "IsDeleted")
    public boolean IsDeleted;

    @ColumnInfo(name = "IsExpireCheck")
    public boolean IsExpireCheck;

    @ColumnInfo(name = "DeletedOn")
    private String DeletedOn;

    @ColumnInfo(name = "DeletedBy")
    private String DeletedBy;

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public boolean isExpireCheck() {
        return IsExpireCheck;
    }

    public void setExpireCheck(boolean expireCheck) {
        IsExpireCheck = expireCheck;
    }

    public double getCurrentStock() {
        return CurrentStock;
    }

    public void setCurrentStock(double currentStock) {
        CurrentStock = currentStock;
    }

    public int getROL() {
        return ROL;
    }

    public void setROL(int ROL) {
        this.ROL = ROL;
    }

    public int getUnitId() {
        return UnitId;
    }

    public void setUnitId(int unitId) {
        UnitId = unitId;
    }

    public long getItemId() {
        return ItemId;
    }

    public void setItemId(long itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getCalorie() {
        return Calorie;
    }

    public void setCalorie(double calorie) {
        Calorie = calorie;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
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

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
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
