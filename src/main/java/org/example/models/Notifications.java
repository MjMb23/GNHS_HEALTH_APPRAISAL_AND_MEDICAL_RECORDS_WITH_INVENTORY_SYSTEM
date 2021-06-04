package org.example.models;

import javafx.scene.control.Alert;

public class Notifications {

    private String header;
    private String message;

    public Notifications(String header, String message) {
        this.header = header;
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public void showError() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(this.getHeader());
        alert.setContentText(this.getMessage());
        alert.show();
    }

    public void showWarning() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(this.getHeader());
        alert.setContentText(this.getMessage());
        alert.show();
    }

    public void showInformation() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(this.getHeader());
        alert.setContentText(this.getMessage());
        alert.show();
    }

    public void showConfirmation() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(this.getHeader());
        alert.setContentText(this.getMessage());
        alert.show();

    }
}
