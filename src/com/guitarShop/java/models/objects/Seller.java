package com.guitarShop.java.models.objects;

public class Seller {

    int sellerID;
    String name;
    String surname;
    String phoneNumber;
    String pesel;
    String email;

    public Seller(int sellerID, String name, String surname, String phoneNumber, String pesel, String email) {
        this.sellerID = sellerID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.email = email;
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
}