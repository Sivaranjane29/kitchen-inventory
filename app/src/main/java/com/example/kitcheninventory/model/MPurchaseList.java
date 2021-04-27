/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;


public class MPurchaseList {

    public long ItemId;
    public long UnitId;
    public String ItemName;
    public String CategoryName;
    public double Calorie;
    public String UnitName;

    public long getUnitId() {
        return UnitId;
    }

    public void setUnitId(long unitId) {
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

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }
}
