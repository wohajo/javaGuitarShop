package com.guitarShop.java.models.objects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Address extends RecursiveTreeObject<Address> {
    int addressID;
    String city;
    String postcode;
    String street;
    int buildingNumber;
    String flatNumber;

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Address(int addressID, String city, String postcode, String street, int buildingNumber, String flatNumber) {
        this.addressID = addressID;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
    }

    @Override
    public String toString() {
        String string = city + " " + postcode + ", \n"
        + street + " " + buildingNumber;
        if(!flatNumber.equals("null"))
            string = string + "/" + buildingNumber;

        return string;
    }
}
