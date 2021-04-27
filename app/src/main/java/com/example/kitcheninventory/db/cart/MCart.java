/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.cart;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MCart {
    /*
   CartId
   ItemName
  */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CartId")
    private long CartId;

    @ColumnInfo(name = "ItemName")
    private String ItemName;

    public long getCartId() {
        return CartId;
    }

    public void setCartId(long cartId) {
        CartId = cartId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }
}
