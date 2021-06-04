package org.example.models;

import javafx.scene.Node;

public class History {
    private int historyID;
    private String historyDescription;
    private Node actions;

    public History(){
        this.historyID=0;
        this.historyDescription="";
        this.actions=null;
    }

    public History(int historyID, String historyDescription, Node actions){
        this.historyDescription=historyDescription;
        this.historyID=historyID;
        this.actions=actions;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public void setHistoryDescription(String historyDescription) {
        this.historyDescription = historyDescription;
    }

    public void setActions(Node actions) {
        this.actions = actions;
    }

    public int getHistoryID() {
        return historyID;
    }

    public String getHistoryDescription() {
        return historyDescription;
    }

    public Node getActions() {
        return actions;
    }
}
