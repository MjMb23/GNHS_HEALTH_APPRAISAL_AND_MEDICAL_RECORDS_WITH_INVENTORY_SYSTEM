package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.connection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.example.models.Batch;
import org.example.models.Item_Summary;
import org.example.models.Notifications;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Refill_Window_Controller implements Initializable{


    @FXML
    private ComboBox<Item_Summary> itemCombobox;

    @FXML
    private TextField quantityField;

    @FXML
    private Label unitLabel;

    @FXML
    private DatePicker entryDatePicker;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private TextArea remarksTextArea;

    @FXML
    private Button refillButton;

    private ConnectionClass conn;
    private ResultSet rs;
    ObservableList<Item_Summary> items;
    private int selectedItemID;
    private String selectedItemUnit;
    private String selectedItemName;
    Notifications error, success, confirm;
    Dashboard_Controller dashboard_controller = new Dashboard_Controller();

    @FXML
    void itemSelectionChanged() {
        Item_Summary selectedItem=itemCombobox.getSelectionModel().getSelectedItem();
            unitLabel.setText(selectedItem.getUnit());
            selectedItemName=selectedItem.getItemName();
            selectedItemID=selectedItem.getItemID();
            selectedItemUnit=selectedItem.getUnit();
    }

    @FXML
    void refillButtonClicked() {

        try{
            saveNewBatch();

            try{

                dashboard_controller.populateItemSummaryTable("All","");

            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        clear();

    }

    private void getAvailableItems(){

        items=FXCollections.observableArrayList();
        try{
            conn=new ConnectionClass();
            rs=conn.select("SELECT DISTINCT items.item_id, items.item_name, batch.unit FROM gnhs_system_db.items " +
                                 "JOIN gnhs_system_db.batch ON items.item_id=batch.items_item_id;");
            while (rs.next()){
                Item_Summary item=new Item_Summary();
                item.setItemID(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setUnit(rs.getString("unit"));

                items.add(item);
            }
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        itemCombobox.setItems(items);
    }

    private void saveNewBatch(){
        boolean insert=true;
        Batch newBatch=new Batch();
        try{
            newBatch.setItemID(selectedItemID);
            newBatch.setUnit(selectedItemUnit);
            newBatch.setQuantity(Integer.parseInt(quantityField.getText()));
            newBatch.setEntryDate(entryDatePicker.getValue());
            newBatch.setExpirationDate(expirationDatePicker.getValue());
            newBatch.setRemarks(remarksTextArea.getText());
            newBatch.setStatus("good");
        }catch (Exception e){
            Notifications error=new Notifications("Error!","Invalid input or empty field.");
            error.showError();
            insert=false;
        }

        if(insert){
            int count;

            try{
                conn=new ConnectionClass();
                count=conn.insert(String.format("INSERT INTO gnhs_system_db.batch (quantity, unit, entry_date, expiration_date, num_of_dispensed, remaining, remarks, status, items_item_id) " +
                        "VALUES ('%d', '%s', '"+newBatch.getEntryDate()+"', '"+newBatch.getExpirationDate()+"', '0', '0', '%s', '%s', '%d')",newBatch.getQuantity(), newBatch.getUnit(),newBatch.getRemarks(),newBatch.getStatus(),newBatch.getItemID()));
                if(count>0){
                    Notifications success=new Notifications("Success!", String.format("Item %s successfully refilled.",selectedItemName));
                    success.showInformation();
                }
                conn.close();
            }catch (Exception e){
                Notifications error=new Notifications("Error!",e.getMessage());
                error.showError();
            }
        }
    }

    private void clear(){
        quantityField.clear();
        remarksTextArea.clear();
        expirationDatePicker.setValue(null);

    }

    public void setItemCombobox(int id){
        for (Item_Summary item: items) {
            if(item.getItemID()==id){
                itemCombobox.getSelectionModel().select(item);
            }

        }
    }

    public void setDashboardController(Dashboard_Controller controller){
        dashboard_controller=controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate dateToday=LocalDate.now();
        getAvailableItems();
        entryDatePicker.setValue(dateToday);
    }

}

