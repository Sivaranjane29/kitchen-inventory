/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;


public class MRecipeHistoryList {

    private String Name;
    private double Quantity;
    private String AddedOn;
    private long RecipeId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
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
