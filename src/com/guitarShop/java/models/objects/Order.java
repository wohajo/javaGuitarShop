package com.guitarShop.java.models.objects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Order extends RecursiveTreeObject<Order> {
    private int orderID;
    private LocalDate date;
    private int sellerID;
    String seller;
    private int clientID;
    String client;
    private LinkedHashMap<Guitar, Integer> guitarIDs = new LinkedHashMap<>();

    public Order(int orderID, LocalDate date, int sellerID, String seller, int clientID, String client, LinkedHashMap<Guitar, Integer> guitarIDs) {
        this.orderID = orderID;
        this.date = date;
        this.sellerID = sellerID;
        this.seller = seller;
        this.clientID = clientID;
        this.client = client;
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LinkedHashMap<Guitar, Integer> getGuitarIDs() {
        return guitarIDs;
    }

    public void setGuitarIDs(LinkedHashMap<Guitar, Integer> guitarIDs) {
        this.guitarIDs = guitarIDs;
    }

    @Override
    public String toString() {
        return "Order: " + getDate().toString();
    }
}
