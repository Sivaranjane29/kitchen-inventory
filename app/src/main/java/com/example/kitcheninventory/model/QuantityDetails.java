/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;

public class QuantityDetails {

    public long MenuId;
    public long RecipeSummaryId;
    public long ItemId;
    public String ItemName;
    public double Calorie;
    public double Qty;
    public double GivenQty;
    public double AvailableQty;
    public double NotAvailableQty;
    public double ReqQty;
    public String UnitName;

    public double getNotAvailableQty() {
        return NotAvailableQty;
    }

    public void setNotAvailableQty(double notAvailableQty) {
        NotAvailableQty = notAvailableQty;
    }

    public double getGivenQty() {
        return GivenQty;
    }

    public void setGivenQty(double givenQty) {
        GivenQty = givenQty;
    }

    public long getItemId() {
        return ItemId;
    }

    public void setItemId(long itemId) {
        ItemId = itemId;
    }

    public long getMenuId() {
        return MenuId;
    }

    public void setMenuId(long menuId) {
        MenuId = menuId;
    }

    public long getRecipeSummaryId() {
        return RecipeSummaryId;
    }

    public void setRecipeSummaryId(long recipeSummaryId) {
        RecipeSummaryId = recipeSummaryId;
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

    public double getAvailableQty() {
        return AvailableQty;
    }

    public void setAvailableQty(double availableQty) {
        AvailableQty = availableQty;
    }

    public double getReqQty() {
        return ReqQty;
    }

    public void setReqQty(double reqQty) {
        ReqQty = reqQty;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }
}
