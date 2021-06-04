package org.example.models;

import java.time.LocalDate;

public class Transaction_History {

    private int transactionID;
    private String itemName;
    private String recipient;
    private int quantity;
    private String unit;
    private LocalDate date;

    public Transaction_History(){
        this.transactionID=0;
        this.itemName="";
        this.recipient="";
        this.quantity=0;
        this.unit="";
        this.date=null;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getDate() {
        return date;
    }

}
