package com.guitarShop.java.models.objects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class OrderGuitar extends RecursiveTreeObject<OrderGuitar> {
    int orderID;
    int guitarID;
    int quantity;

    public OrderGuitar(int orderID, int guitarID, int quantity) {
        this.orderID = orderID;
        this.guitarID = guitarID;
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getGuitarID() {
        return guitarID;
    }

    public void setGuitarID(int guitarID) {
        this.guitarID = guitarID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
