package org.example.models;

public class Item {
    private int itemID;
    private String itemName;
    private String itemType;

    public Item(){
        this.itemID=0;
        this.itemName="";
        this.itemType="";
    }

    public Item(int itemID, String itemName, String itemType) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
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

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return this.itemName;
    }


}
