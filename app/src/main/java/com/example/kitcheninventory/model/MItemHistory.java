/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;

public class MItemHistory {

    private String ItemName;
    private double Quantity;
    public boolean IsIncrease;
    public boolean IsDecrease;
    public boolean IsRecipe;
    private String AddedOn;
    private long RecipeId;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
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

    public boolean isRecipe() {
        return IsRecipe;
    }

    public void setRecipe(boolean recipe) {
        IsRecipe = recipe;
    }

    public String getAddedOn() {
        return AddedOn;
    }

    public void setAddedOn(String addedOn) {
        AddedOn = addedOn;
    }

    public long getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(long recipeId) {
        RecipeId = recipeId;
    }
}
