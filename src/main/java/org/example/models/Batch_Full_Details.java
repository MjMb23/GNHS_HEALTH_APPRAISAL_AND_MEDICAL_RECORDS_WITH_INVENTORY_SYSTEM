package org.example.models;

public class Batch_Full_Details extends Batch{
    private  String itemName;
    private String itemType;


    public Batch_Full_Details(){
        this.itemName="";
        this.itemType="";

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return this.getItemName();
    }
}
