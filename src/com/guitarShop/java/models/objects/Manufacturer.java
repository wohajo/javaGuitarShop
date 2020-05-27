package com.guitarShop.java.models.objects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Manufacturer extends RecursiveTreeObject<Manufacturer> {
    private String manufacturerName;
    private int addressID;
    private int manufacturerID;

    public Manufacturer(int manufacturerID, String manufacturerName, int addressID) {
        this.manufacturerID = manufacturerID;
        this.manufacturerName = manufacturerName;
        this.addressID = addressID;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    @Override
    public String toString() {
        return getManufacturerName();
    }
}
