/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.model;

public class MPurchaseSummaryList {

    public long SerialNumber;
    public long PurchaseSummaryId;
    public long VoucherNumber;
    public String PurchaseDate;
    public String Name;

    public long getPurchaseSummaryId() {
        return PurchaseSummaryId;
    }

    public void setPurchaseSummaryId(long purchaseSummaryId) {
        PurchaseSummaryId = purchaseSummaryId;
    }

    public long getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        SerialNumber = serialNumber;
    }

    public long getVoucherNumber() {
        return VoucherNumber;
    }

    public void setVoucherNumber(long voucherNumber) {
        VoucherNumber = voucherNumber;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
