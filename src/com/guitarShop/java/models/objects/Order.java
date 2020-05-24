package com.guitarShop.java.models.objects;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private int orderID;
    private LocalDate date;
    private int sellerID;
    private ArrayList<Integer> guitarIDs;

    public Order(int orderID, LocalDate date, int sellerID, ArrayList<Integer> guitarIDs) {
        this.orderID = orderID;
        this.date = date;
        this.sellerID = sellerID;
        this.guitarIDs = guitarIDs;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public ArrayList<Integer> getGuitarIDs() {
        return guitarIDs;
    }

    public void setGuitarIDs(ArrayList<Integer> guitarIDs) {
        this.guitarIDs = guitarIDs;
    }
}
