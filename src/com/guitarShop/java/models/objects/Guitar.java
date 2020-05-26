package com.guitarShop.java.models.objects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Guitar extends RecursiveTreeObject<Guitar> {

    private int guitarID;
    private String manufacturer;
    private int manufacturerID;
    private int guitarPrice;
    private String guitarType;
    private int guitarTypeID;
    private String pickupsType;
    private int pickupsTypeID;
    private String bridgeType;
    private int bridgeTypeID;
    private String lockingTuners;
    private int numberOfGuitars;
    private String model;
    private String modelDescription;
    private int numberOfStrings;

    public Guitar(int guitarID, String manufacturer, int manufacturerID, int guitarPrice, String guitarType, int guitarTypeID,
                  String pickupsType, int pickupsTypeID, String bridgeType, int bridgeTypeID, String lockingTuners,
                  int numberOfGuitars, String model, String modelDescription, int numberOfStrings) {
        this.guitarID = guitarID;
        this.manufacturer = manufacturer;
        this.manufacturerID = manufacturerID;
        this.guitarPrice = guitarPrice;
        this.guitarType = guitarType;
        this.guitarTypeID = guitarTypeID;
        this.pickupsType = pickupsType;
        this.pickupsTypeID = pickupsTypeID;
        this.bridgeType = bridgeType;
        this.bridgeTypeID = bridgeTypeID;
        this.lockingTuners = lockingTuners;
        this.numberOfGuitars = numberOfGuitars;
        this.model = model;
        this.modelDescription = modelDescription;
        this.numberOfStrings = numberOfStrings;
    }

    public int getGuitarID() {
        return guitarID;
    }

    public void setGuitarID(int guitarID) {
        this.guitarID = guitarID;
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

    public int getGuitarPrice() {
        return guitarPrice;
    }

    public void setGuitarPrice(int guitarPrice) {
        this.guitarPrice = guitarPrice;
    }

    public String getGuitarType() {
        return guitarType;
    }

    public void setGuitarType(String guitarType) {
        this.guitarType = guitarType;
    }

    public int getGuitarTypeID() {
        return guitarTypeID;
    }

    public void setGuitarTypeID(int guitarTypeID) {
        this.guitarTypeID = guitarTypeID;
    }

    public String getPickupsType() {
        return pickupsType;
    }

    public void setPickupsType(String pickupsType) {
        this.pickupsType = pickupsType;
    }

    public int getPickupsTypeID() {
        return pickupsTypeID;
    }

    public void setPickupsTypeID(int pickupsTypeID) {
        this.pickupsTypeID = pickupsTypeID;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public int getBridgeTypeID() {
        return bridgeTypeID;
    }

    public void setBridgeTypeID(int bridgeTypeID) {
        this.bridgeTypeID = bridgeTypeID;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    public String getLockingTuners() {
        return lockingTuners;
    }

    public void setLockingTuners(String lockingTuners) {
        this.lockingTuners = lockingTuners;
    }

    public int getNumberOfGuitars() {
        return numberOfGuitars;
    }

    public void setNumberOfGuitars(int numberOfGuitars) {
        this.numberOfGuitars = numberOfGuitars;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public int getNumberOfStrings() {
        return numberOfStrings;
    }

    public void setNumberOfStrings(int numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }
}
