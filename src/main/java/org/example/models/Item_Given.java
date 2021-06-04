package org.example.models;

import javafx.scene.Node;

public class Item_Given extends Item{

    private int recordID;
    private int quantity;
    private String unit;
    private Node action;

    public Item_Given(){
        this.quantity=0;
        this.action=null;
        this.recordID=0;
        this.unit="";
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAction(Node action) {
        this.action = action;
    }

    public int getQuantity() {
        return quantity;
    }

    public Node getAction() {
        return action;
    }

}
