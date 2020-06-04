package com.guitarShop.java.models.objects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Seller extends RecursiveTreeObject<Seller> {

    private int sellerID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;
    private String email;
    private int addressID;

    public Seller(int sellerID, String name, String surname, String phoneNumber, String pesel, String email, int addressID) {
        this.sellerID = sellerID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.email = email;
        this.addressID = addressID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }
}
