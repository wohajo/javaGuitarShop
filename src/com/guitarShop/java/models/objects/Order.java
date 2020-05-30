package com.guitarShop.java.models.objects;

import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Order {
    private int orderID;
    private LocalDate date;
    private int sellerID;
    private int clientID;
    private LinkedHashMap<Guitar, Integer> guitarIDs = new LinkedHashMap<>();

    public Order(int orderID, LocalDate date, int sellerID, int clientID, LinkedHashMap<Guitar, Integer> guitarIDs) {
        this.orderID = orderID;
        this.date = date;
        this.sellerID = sellerID;
        this.clientID = clientID;
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

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public LinkedHashMap<Guitar, Integer> getGuitarIDs() {
        return guitarIDs;
    }

    public void setGuitarIDs(LinkedHashMap<Guitar, Integer> guitarIDs) {
        this.guitarIDs = guitarIDs;
    }
}
