package org.example.models;

import javafx.scene.Node;

import java.time.LocalDate;

public class Batch {
    private int batchID;
    private int itemID;
    private int quantity;
    private String unit;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private int dispensed;
    private int remaining;
    private String remarks;
    private String status;
    private Node actions;

    public Batch(){
        this.batchID=0;
        this.itemID=0;
        this.quantity=0;
        this.unit="";
        this.entryDate=null;
        this.expirationDate=null;
        this.dispensed=0;
        this.remaining=0;
        this.remarks="";
        this.status="";
        this.actions=null;
    }

    public Batch(int batchID, int itemID, int quantity, String unit, LocalDate entryDate, LocalDate expirationDate, int dispensed, String remarks, String status) {
        this.batchID = batchID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unit = unit;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
        this.dispensed = dispensed;
        this.remarks = remarks;
        this.status = status;
    }

    public Batch(int quantity, String unit, int dispensed) {
        this.quantity = quantity;
        this.unit = unit;
        this.dispensed = dispensed;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public Node getActions() {
        return actions;
    }

    public void setActions(Node actions) {
        this.actions = actions;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setDispensed(int dispensed) {
        this.dispensed = dispensed;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBatchID() {
        return batchID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getDispensed() {
        return dispensed;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getStatus() {
        return status;
    }
}
