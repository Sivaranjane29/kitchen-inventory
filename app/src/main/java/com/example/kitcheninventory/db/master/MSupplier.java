/*
 * Copyright (c) 2021.
 */

package com.example.kitcheninventory.db.master;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MSupplier {
   /*
   SupplierId
   Name
   PhoneOne
   PhoneTwo
   Email
   Address
   Location
   Latitude
   Longitude
   ServerId
   BranchId
   IsPosted
   PostedDate
   IsActive
   AddedOn
   AddedBy
   ModifiedOn
   ModifiedBy
   IsDeleted
   DeletedOn
   DeletedBy
    */


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SupplierId")
    private long SupplierId;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "PhoneOne")
    private String PhoneOne;

    @ColumnInfo(name = "PhoneTwo")
    private String PhoneTwo;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "Address")
    private String Address;

    @ColumnInfo(name = "Location")
    private String Location;

    @ColumnInfo(name = "Latitude")
    private String Latitude;

    @ColumnInfo(name = "Longitude")
    private String Longitude;

    @ColumnInfo(name = "ServerId")
    private int ServerId;

    @ColumnInfo(name = "BranchId")
    private int BranchId;

    @ColumnInfo(name = "IsPosted")
    public boolean IsPosted;

    @ColumnInfo(name = "PostedDt")
    private String PostedDt;

    @ColumnInfo(name = "IsActive")
    public boolean IsActive;

    @ColumnInfo(name = "AddedOn")
    private String AddedOn;

    @ColumnInfo(name = "AddedBy")
    private String AddedBy;

    @ColumnInfo(name = "ModifiedOn")
    private String ModifiedOn;

    @ColumnInfo(name = "ModifiedBy")
    private String ModifiedBy;

    @ColumnInfo(name = "IsDeleted")
    public boolean IsDeleted;

    @ColumnInfo(name = "DeletedOn")
    private String DeletedOn;

    @ColumnInfo(name = "DeletedBy")
    private String DeletedBy;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public long getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(long supplierId) {
        SupplierId = supplierId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneOne() {
        return PhoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        PhoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return PhoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        PhoneTwo = phoneTwo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public int getServerId() {
        return ServerId;
    }

    public void setServerId(int serverId) {
        ServerId = serverId;
    }

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int branchId) {
        BranchId = branchId;
    }

    public boolean isPosted() {
        return IsPosted;
    }


    public void setPosted(boolean posted) {
        IsPosted = posted;
    }

    public String getPostedDt() {
        return PostedDt;
    }

    public void setPostedDt(String postedDt) {
        PostedDt = postedDt;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getAddedOn() {
        return AddedOn;
    }

    public void setAddedOn(String addedOn) {
        AddedOn = addedOn;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public void setAddedBy(String addedBy) {
        AddedBy = addedBy;
    }

    public String getModifiedOn() {
        return ModifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        ModifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getDeletedOn() {
        return DeletedOn;
    }

    public void setDeletedOn(String deletedOn) {
        DeletedOn = deletedOn;
    }

    public String getDeletedBy() {
        return DeletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        DeletedBy = deletedBy;
    }
}
