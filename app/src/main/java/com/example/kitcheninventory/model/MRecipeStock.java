/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;

public class MRecipeStock {

    public String ItemName;
    public long RecipeSummaryId;
    public long ItemId;
    public long UnitId;
    public double Calorie;
    public double Qty;
    public double Quantity;
    public String UnitName;

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public long getUnitId() {
        return UnitId;
    }

    public void setUnitId(long unitId) {
        UnitId = unitId;
    }

    public long getRecipeSummaryId() {
        return RecipeSummaryId;
    }

    public void setRecipeSummaryId(long recipeSummaryId) {
        RecipeSummaryId = recipeSummaryId;
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

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }
}
