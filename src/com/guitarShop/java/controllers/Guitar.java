package com.guitarShop.java.controllers;

public class Guitar {

    private int guitarID;
    private String manufacturer;
    private double guitarPrice;
    private String guitarType;
    private String pickupsType;
    private String bridgeType;
    private Boolean lockingTuners;
    private int numberOfGuitars;
    private String model;
    private String modelDescription;
    private int numberOfStrings;

    public Guitar(int guitarID, String manufacturer, double guitarPrice, String guitarType, String pickupsType, String bridgeType, Boolean lockingTuners, int numberOfGuitars, String model, String modelDescription, int numberOfStrings) {
        this.guitarID = guitarID;
        this.manufacturer = manufacturer;
        this.guitarPrice = guitarPrice;
        this.guitarType = guitarType;
        this.pickupsType = pickupsType;
        this.bridgeType = bridgeType;
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

    public double getGuitarPrice() {
        return guitarPrice;
    }

    public void setGuitarPrice(double guitarPrice) {
        this.guitarPrice = guitarPrice;
    }

    public String getGuitarType() {
        return guitarType;
    }

    public void setGuitarType(String guitarType) {
        this.guitarType = guitarType;
    }

    public String getPickupsType() {
        return pickupsType;
    }

    public void setPickupsType(String pickupsType) {
        this.pickupsType = pickupsType;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    public Boolean getLockingTuners() {
        return lockingTuners;
    }

    public void setLockingTuners(Boolean lockingTuners) {
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
