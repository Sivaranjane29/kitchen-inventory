/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;


public class MPurchaseRow {

    public long ItemId;
    public long UnitId;
    public String ItemName;
    public double Calorie;
    public double Qty;
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

    public double getCalorie() {
        return Calorie;
    }

    public void setCalorie(double calorie) {
        Calorie = calorie;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }
}
