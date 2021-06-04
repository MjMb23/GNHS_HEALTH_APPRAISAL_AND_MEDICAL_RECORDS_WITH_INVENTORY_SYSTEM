package org.example.controllers;

import org.example.connection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.models.Batch;
import org.example.models.Item;
import org.example.models.Notifications;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Inventory_Controller implements Initializable {
    
    @FXML
    private TextField itemNameField;

    @FXML
    private ChoiceBox<String> itemTypeChoice;

    @FXML
    private TextField itemQuantityField;

    @FXML
    private ChoiceBox<String> itemUnitChoice;

    @FXML
    private DatePicker entryDatePicker;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private TextField numberOfDispensedField;

    @FXML
    private TextArea remarksTextArea;

    @FXML
    private Button saveButton;

    Item item=new Item();
    Dashboard_Controller dashboardController=new Dashboard_Controller();

    private void prepareItem(){

        String itemName=itemNameField.getText();
        String itemType=itemTypeChoice.getValue();

        item.setItemName(itemName);
        item.setItemType(itemType);

    }

    public boolean itemExists(){
        boolean exist=false;
        try{
            ConnectionClass verify=new ConnectionClass();
            ResultSet rs= verify.select("SELECT * FROM gnhs_system_db.items WHERE item_name='"+item.getItemName()+"'");

            if(rs.next()==false){
                exist=false;
            }else{
                exist=true;
            }

            verify.close();

        }catch (Exception e){
            e.getMessage();
        }
        return exist;
    }

    boolean insertBatch;

    private void addItem(){

        insertBatch=true;

        try{

            int affectedRows=0;

            ConnectionClass conn=new ConnectionClass();
            affectedRows=conn.insert("INSERT INTO gnhs_system_db.items(item_name, item_type) VALUES ('"+item.getItemName()+"', '"+item.getItemType()+"');\n");

            if(affectedRows>0){
                Notifications success=new Notifications("Success!", "The item was added successfully.");
                success.showInformation();
            }
            conn.close();
        }catch (Exception e){
            Notifications error=new Notifications("Error",e.getMessage());
            insertBatch=false;
            error.showError();
            e.printStackTrace();
        }
    }

    Batch batch=new Batch();

    private void addBatch(){

        int itemID=getItemID();
        int quantity=Integer.parseInt(itemQuantityField.getText());
        String unit=itemUnitChoice.getValue();
        LocalDate entryDate=entryDatePicker.getValue();
        LocalDate expirationDate=expirationDatePicker.getValue();
        int dispensed=Integer.parseInt(numberOfDispensedField.getText());
        String remarks=remarksTextArea.getText();
        String status="good";

        batch.setItemID(itemID);
        batch.setQuantity(quantity);
        batch.setUnit(unit);
        batch.setEntryDate(entryDate);
        batch.setExpirationDate(expirationDate);
        batch.setDispensed(dispensed);
        batch.setRemarks(remarks);
        batch.setStatus(status);


        if(insertBatch=true){
            try{
                ConnectionClass conn=new ConnectionClass();
                conn.insert(" INSERT INTO gnhs_system_db.batch (quantity, unit, entry_date, expiration_date, num_of_dispensed, remaining, remarks, status, items_item_id) " +
                        " VALUES ('"+batch.getQuantity()+"', '"+batch.getUnit()+"', '"+batch.getEntryDate()+"', '"+batch.getExpirationDate()+"', '"+batch.getDispensed()+"', '"+batch.getQuantity()+"', '"+batch.getRemarks()+"', '"+batch.getStatus()+"', '"+batch.getItemID()+"'); ");

                /*if(affectedRows>0){
                    Notifications success=new Notifications("Success!", "The batch was added successfully.");
                    success.showInformation();
                }*/

                conn.close();
            }catch (Exception e){
                Notifications error=new Notifications("Error!",e.getMessage());
                error.showError();
            }
        }


    }

    private int getItemID(){

        int idNum=0;
        try{
            ConnectionClass getID=new ConnectionClass();
            ResultSet id=getID.select("SELECT item_id FROM gnhs_system_db.items WHERE item_name='"+item.getItemName()+"'");
            while (id.next()){
                item.setItemID(id.getInt("item_id"));
                idNum=item.getItemID();
            }

            getID.close();

        }catch (Exception e){
            Notifications error=new Notifications("Error",e.getMessage());
            error.showError();
            e.printStackTrace();
        }
        return idNum;
    }

    private void populateChoices(){

        ObservableList<String> unit=FXCollections.observableArrayList();
        unit.add("pieces");
        unit.add("set");
        unit.add("pax");
        unit.add("litres");

        ObservableList<String> type=FXCollections.observableArrayList();
        type.add("Medicine");
        type.add("Equipment");
        itemTypeChoice.setItems(type);
        itemUnitChoice.setItems(unit);
    }

    private void setDefaults(){

        LocalDate dateNow=LocalDate.now();
        itemUnitChoice.getSelectionModel().select(0);
        itemTypeChoice.getSelectionModel().select(0);
        itemQuantityField.setText("0");
        numberOfDispensedField.setText("0");
        entryDatePicker.setValue(dateNow);
        itemNameField.setText("");
        expirationDatePicker.setValue(null);
        remarksTextArea.setText("");
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

        prepareItem();

        if (itemExists()) {
            Notifications error=new Notifications("Error!", "An item with a name \""+itemNameField.getText()+"\" already exist.");
            error.showError();
        }else{
            if(!itemNameField.getText().equals("")){
                addItem();
                addBatch();
                setDefaults();
            }else{
                Notifications error=new Notifications("Error","Incomplete fields.");
                error.showError();
            }
        }
        dashboardController.populateItemSummaryTable(dashboardController.getRecordsFilterValue(), dashboardController.getSearchValue());
    }

    public void setDashboardController(Dashboard_Controller controller){
        dashboardController=controller;
    }

    //ArrayList<Node> nodes= new ArrayList<Node>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateChoices();
        setDefaults();
    }
}
