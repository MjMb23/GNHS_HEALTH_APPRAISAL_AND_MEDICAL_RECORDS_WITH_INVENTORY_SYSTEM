package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.connection.ConnectionClass;
import org.example.models.*;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class Item_Details_Controller implements Initializable {

    @FXML
    private VBox infoContainer;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private Label totalQuantityLabel;

    @FXML
    private Label totalQuantityUnit;

    @FXML
    private Label totalDispensedQuantity;

    @FXML
    private Label totalDispensedUnit;

    @FXML
    private Label totalRemainingQuantity;

    @FXML
    private Label totalRemainingUnit;


    @FXML
    private TableView<Batch_Full_Details> batchTable;

    @FXML
    private TableColumn<Batch_Full_Details, Integer> quantityColumn;

    @FXML
    private TableColumn<Batch_Full_Details, LocalDate> entryDateColumn;

    @FXML
    private TableColumn<Batch_Full_Details, LocalDate> expirationDateColumn;

    @FXML
    private TableColumn<Batch_Full_Details, Integer> dispensedColumn;

    @FXML
    private TableColumn<Batch_Full_Details, Integer> remainingColumn;

    @FXML
    private TableColumn<Batch_Full_Details, String> remarksColumn;

    @FXML
    private TableColumn<Batch_Full_Details, String> statusColumn;

    @FXML
    private TableColumn<Batch_Full_Details, Node> actionColumn;

    @FXML
    private ComboBox<Item_Summary> itemCombobox;

    @FXML
    private TableView<Transaction_History> transactionHistoryTable;

    @FXML
    private TableColumn<Transaction_History, String> transactionItemNameColumn;

    @FXML
    private TableColumn<Transaction_History, String> transactionRecipientColumn;

    @FXML
    private TableColumn<Transaction_History, Integer> transactionQuantityColumn;

    @FXML
    private TableColumn<Transaction_History, String> transactionUnitColumn;

    @FXML
    private TableColumn<Transaction_History, LocalDate> transactionDateColumn;

    @FXML
    private ComboBox<Item> itemFilterComboBox;

    private ConnectionClass conn;
    private ResultSet rs, result;
    ObservableList<Batch_Full_Details> items;
    ObservableList<Item_Summary> itemAvailable;
    Notifications error, success, confirm;
    private int totalQuantity;
    private int totalDispensed;
    private int totalRemaining;
    ObservableList<Item> itemsFilter;
    Dashboard_Controller dashboardController = new Dashboard_Controller();

    @FXML
    void itemFilterComboBoxSelectionChange() {

        Item selectedItem = itemFilterComboBox.getSelectionModel().getSelectedItem();
        fetchTransactionHistory(selectedItem.getItemID());

    }

    @FXML
    void itemComboboxSelectionChanged() {
        Item_Summary selectedItem = itemCombobox.getSelectionModel().getSelectedItem();
        int id = selectedItem.getItemID();
        fetchItems(id);
        itemNameLabel.setText(selectedItem.getItemName());
        itemTypeLabel.setText(selectedItem.getItemType());
        totalQuantityLabel.setText(totalQuantity + "");
        totalQuantityUnit.setText(selectedItem.getUnit());
        totalDispensedQuantity.setText(totalDispensed + "");
        totalDispensedUnit.setText(selectedItem.getUnit());
        totalRemainingQuantity.setText(totalRemaining + "");
        totalRemainingUnit.setText(selectedItem.getUnit());
        populateBatchTable();

        setItemFilterComboBox(id);
        itemFilterComboBoxSelectionChange();
    }

    private void fetchItems(int itemId) {

        totalQuantity = 0;
        totalDispensed = 0;
        totalRemaining = 0;

        items = FXCollections.observableArrayList();

        try {
            conn = new ConnectionClass();
            rs = conn.select(String.format("SELECT * FROM gnhs_system_db.items " +
                    "JOIN gnhs_system_db.batch ON items.item_id=batch.items_item_id WHERE items.item_id='%d' AND status!='disposed';", itemId));
            while (rs.next()) {

                Batch_Full_Details item = new Batch_Full_Details();

                int quantity = rs.getInt("quantity");
                int dispensed = rs.getInt("num_of_dispensed");
                int remaining = rs.getInt("remaining");

                totalQuantity += quantity;
                totalDispensed += dispensed;
                totalRemaining += remaining;

                item.setBatchID(rs.getInt("batch_id"));
                item.setItemID(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setQuantity(quantity);
                item.setItemType(rs.getString("item_type"));
                item.setUnit(rs.getString("unit"));
                item.setEntryDate(rs.getDate("entry_date").toLocalDate());
                item.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                item.setDispensed(dispensed);
                item.setRemaining(remaining);
                item.setRemarks(rs.getString("remarks"));
                item.setStatus(rs.getString("status"));

                int id = rs.getInt("batch_id");

                Button dispose = new Button("Dispose");

                HBox buttonContainer = new HBox();
                buttonContainer.setId(id + "");

                dispose.setPrefWidth(100);

                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);
                buttonContainer.setPadding(new Insets(10, 10, 10, 10));
                dispose.getStyleClass().add("clearButtons");

                buttonContainer.getChildren().addAll(dispose);

                dispose.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        int containerID = Integer.parseInt(dispose.getParent().getId());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Record");
                        alert.setHeaderText("The record will be deleted permanently.");
                        alert.setResizable(false);
                        alert.setContentText("Are you sure you want to delete this record?");

                        Optional<ButtonType> result = alert.showAndWait();
                        ButtonType button = result.orElse(ButtonType.CANCEL);

                        if (button == ButtonType.OK) {
                            try {
                                conn = new ConnectionClass();
                                conn.delete(String.format("UPDATE gnhs_system_db.batch SET status = 'disposed' WHERE (`batch_id` = '%d')", containerID));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemComboboxSelectionChanged();
                        }
                    }
                });

                item.setActions(buttonContainer);
                items.add(item);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }

        dashboardController.populateItemSummaryTable(dashboardController.getRecordsFilterValue(), dashboardController.getSearchValue());
    }

    private void populateBatchTable() {

        if (!(items.isEmpty())) {
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            entryDateColumn.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
            expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
            dispensedColumn.setCellValueFactory(new PropertyValueFactory<>("dispensed"));
            remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
            remarksColumn.setCellValueFactory(new PropertyValueFactory<>("remarks"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            actionColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
            batchTable.setItems(items);
        }
    }

    private void getAvailableItems() {

        itemAvailable = FXCollections.observableArrayList();

        try {
            conn = new ConnectionClass();
            rs = conn.select("SELECT DISTINCT items.item_id, items.item_name, items.item_type, batch.unit FROM gnhs_system_db.items " +
                    "LEFT JOIN gnhs_system_db.batch ON items.item_id=batch.items_item_id;");
            while (rs.next()) {
                Item_Summary item = new Item_Summary();
                item.setItemID(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setUnit(rs.getString("unit"));
                item.setItemType(rs.getString("item_type"));

                itemAvailable.add(item);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemCombobox.setItems(itemAvailable);
    }

    public void setItemCombobox(int id) {

        for (Item_Summary item : itemAvailable) {
            if (item.getItemID() == id) {
                itemCombobox.getSelectionModel().select(item);
            }
        }
    }

    public void setItemFilterComboBox(int id) {
        for (Item item : itemsFilter) {
            if (item.getItemID() == id) {
                itemFilterComboBox.getSelectionModel().select(item);
            }
        }
    }

    public void setDashboardController(Dashboard_Controller controller) {
        dashboardController = controller;
    }

    public void fetchTransactionHistory(int itemID) {

        ObservableList<Transaction_History> transaction = FXCollections.observableArrayList();

        try {

            String itemIDCondition = "";

            if (itemID == 0) {
                itemIDCondition = "";
            } else {
                itemIDCondition = String.format("WHERE items.item_id='%d'", itemID);
            }

            conn = new ConnectionClass();
            rs = conn.select(String.format("SELECT distinct items.item_name, CONCAT(patients.first_name, ' ',patients.last_name) AS full_name, medicine_or_equipment_given.quantity, batch.unit , record.date_taken\n" +
                    " FROM gnhs_system_db.medicine_or_equipment_given\n" +
                    " JOIN gnhs_system_db.items ON medicine_or_equipment_given.items_item_id=items.item_id\n" +
                    " JOIN gnhs_system_db.record ON medicine_or_equipment_given.record_record_id=record.record_id\n" +
                    " JOIN gnhs_system_db.patients ON record.patient_id=patients.patient_id\n" +
                    " JOIN gnhs_system_db.batch ON batch.items_item_id=items.item_id %s ORDER BY date_taken DESC", itemIDCondition));

            while (rs.next()) {
                Transaction_History trans = new Transaction_History();
                trans.setItemName(rs.getString("item_name"));
                trans.setRecipient(rs.getString("full_name"));
                trans.setQuantity(rs.getInt("quantity"));
                trans.setUnit(rs.getString("unit"));
                trans.setDate(rs.getDate("date_taken").toLocalDate());

                transaction.add(trans);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        transactionItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        transactionRecipientColumn.setCellValueFactory(new PropertyValueFactory<>("recipient"));
        transactionQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        transactionUnitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        transactionHistoryTable.setItems(transaction);
    }

    public void populateItemFilterComboBox() {
        itemsFilter = FXCollections.observableArrayList();

        Item item = new Item();
        item.setItemID(0);
        item.setItemName("All Items");
        itemsFilter.add(item);

        try {
            conn = new ConnectionClass();
            rs = conn.select("SELECT item_id, item_name FROM gnhs_system_db.items;");

            while (rs.next()) {
                Item newItem = new Item();
                newItem.setItemID(rs.getInt("item_id"));
                newItem.setItemName(rs.getString("item_name"));
                itemsFilter.add(newItem);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemFilterComboBox.setItems(itemsFilter);
        itemFilterComboBox.getSelectionModel().select(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAvailableItems();
        populateItemFilterComboBox();
    }
}