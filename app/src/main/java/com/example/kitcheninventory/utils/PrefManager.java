/*
 * *
 *  * Created by PromptTech Middle East Pvt Ltd. on 21/1/20 5:22 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 14/1/20 2:51 PM
 *
 */

package com.example.kitcheninventory.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private SharedPreferences sharedPreferences;


    public PrefManager(Context mCtx) {
        sharedPreferences = mCtx.getSharedPreferences("com.example.kitcheninventory_preferences", Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getString(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public String getStringDefault(String key, String strDefaultValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, strDefaultValue);
        }
        return strDefaultValue;
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public boolean getBoolean(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putLong(key, value);
        prefsEditor.apply();
    }

    public long getLong(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, 0);
        }
        return 0;
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public int getInt(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }

    public long getSerialNumber() {
        long intSerialNumber;
        intSerialNumber = sharedPreferences.getLong(GlobalConstants.PURCHASE_SERIAL, 1);
        return intSerialNumber;
    }

    public void incrementPurchaseSerial() {
        long intSerialNumber;
        intSerialNumber = sharedPreferences.getLong(GlobalConstants.PURCHASE_SERIAL, 1);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        long intNewNumber = intSerialNumber + 1;
        prefsEditor.putLong(GlobalConstants.PURCHASE_SERIAL, intNewNumber);
        prefsEditor.apply();
    }
}
