package org.example.models;

import javafx.scene.Node;

public class Item_Summary {
    private int itemID;
    private String itemName;
    private String itemType;
    private int quantity;
    private String unit;
    private int dispensed;
    private int remaining;
    private Node actions;

    public Item_Summary(){
        this.itemID=0;
        this.itemName="";
        this.itemType="";
        this.quantity=0;
        this.unit="";
        this.dispensed=0;
        this.actions=null;
        this.remaining=0;
    }

    public Item_Summary(int itemID, String itemName, String itemType, int quantity, String unit, int dispensed,int remaining, Node actions) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.unit = unit;
        this.dispensed = dispensed;
        this.remaining=remaining;
        this.actions=actions;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public void setActions(Node actions) {
        this.actions = actions;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDispensed(int dispensed) {
        this.dispensed = dispensed;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public int getDispensed() {
        return dispensed;
    }

    public Node getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return this.getItemName();
    }
}
