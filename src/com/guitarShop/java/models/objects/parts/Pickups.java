package com.guitarShop.java.models.objects.parts;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Pickups extends RecursiveTreeObject<Pickups> {
    int pickupsID;
    String name;
    String manufacturer;
    int manufacturerID;

    public Pickups(int pickupsID, String name, String manufacturer, int manufacturerID) {
        this.pickupsID = pickupsID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.manufacturerID = manufacturerID;
    }

    public int getPickupsID() {
        return pickupsID;
    }

    public void setPickupsID(int pickupsID) {
        this.pickupsID = pickupsID;
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

    @Override
    public String toString() {
        return getName() + " (" + getManufacturer() + ")";
    }
}
