/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;

public class StockRowList {
    public long ItemId;
    public String ItemName;
    public String CategoryName;
    public double Calorie;
    public double Quantity;
    public String UnitName;
    public String ExpiryDate;
    public boolean IsExpireCheck;

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

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public double getCalorie() {
        return Calorie;
    }

    public void setCalorie(double calorie) {
        Calorie = calorie;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }
}
