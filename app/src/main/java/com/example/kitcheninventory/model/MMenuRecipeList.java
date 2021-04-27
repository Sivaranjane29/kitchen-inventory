/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;

public class MMenuRecipeList {

    public long MenuSummaryId;
    public long RecipeId;
    public String Name;
    public String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public long getMenuSummaryId() {
        return MenuSummaryId;
    }

    public void setMenuSummaryId(long menuSummaryId) {
        MenuSummaryId = menuSummaryId;
    }

    public long getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(long recipeId) {
        RecipeId = recipeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
