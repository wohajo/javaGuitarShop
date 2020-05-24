package com.guitarShop.java.models.objects;

public class Bridge {
    String manufacturer;
    int manufacturerID;
    int bridgeID;
    String name;

    public Bridge(int bridgeID, String name, String manufacturer, int manufacturerID) {
        this.bridgeID = bridgeID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.manufacturerID = manufacturerID;
    }

    public int getBridgeID() {
        return bridgeID;
    }

    public void setBridgeID(int bridgeID) {
        this.bridgeID = bridgeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }
}
