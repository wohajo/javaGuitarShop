package com.guitarShop.java.models.objects.parts;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class GuitarType extends RecursiveTreeObject<GuitarType> {
    int typeID;
    String name;

    public GuitarType(int typeID, String name) {
        this.typeID = typeID;
        this.name = name;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
