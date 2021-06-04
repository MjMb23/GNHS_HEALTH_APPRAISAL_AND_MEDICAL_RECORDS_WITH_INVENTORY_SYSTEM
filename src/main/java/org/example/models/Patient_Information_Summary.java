package org.example.models;

import javafx.scene.Node;

public class Patient_Information_Summary extends Patient{
    private Node actions;

    public Patient_Information_Summary(){
        this.actions=null;
    }

    public Node getActions() {
        return actions;
    }

    public void setActions(Node actions) {
        this.actions = actions;
    }
}
