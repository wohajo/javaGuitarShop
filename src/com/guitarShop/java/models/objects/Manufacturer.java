package com.guitarShop.java.models.objects;

public class Manufacturer {
    private String ManufacturerName;
    private int addressID;
    private int ManufacturerID;

    public Manufacturer(int manufacturerID, String manufacturerName, int addressID) {
        ManufacturerID = manufacturerID;
        ManufacturerName = manufacturerName;
        this.addressID = addressID;
    }

    public int getManufacturerID() {
        return ManufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        ManufacturerID = manufacturerID;
    }

    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        ManufacturerName = manufacturerName;
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
