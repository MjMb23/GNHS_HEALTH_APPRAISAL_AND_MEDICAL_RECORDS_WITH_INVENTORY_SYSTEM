package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.connection.ConnectionClass;
import org.example.models.Batch;
import org.example.models.Item_Summary;
import org.example.models.Notifications;
import org.example.models.Patient_Information_Summary;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard_Controller implements Initializable {


    //GLOBAL NODES
    @FXML
    private AnchorPane healthAppraisalContainer;

    @FXML
    private AnchorPane inventoryContainer;

    @FXML
    private Text titleText;

    //INVENTORY NODES
    @FXML
    private TableView<Item_Summary> itemSummaryTable;

    @FXML
    private TableColumn<Item_Summary, String> itemNameColumn;

    @FXML
    private TableColumn<Item_Summary, String> itemTypeColumn;

    @FXML
    private TableColumn<Item_Summary, Integer> quantityColumn;

    @FXML
    private TableColumn<Item_Summary, String> unitColumn;

    @FXML
    private TableColumn<Item_Summary, Integer> dispensedColumn;

    @FXML
    private TableColumn<Item_Summary, Integer> availableColumn;

    @FXML
    private TableColumn<Item_Summary, Node> actionColumn;

    @FXML
    private Button createItemButton;

    @FXML
    private ComboBox<String> itemFilterCombobox;

    @FXML
    private TextField itemField;

    //INVENTORY ACTION EVENTS
    @FXML
    void createItemButtonClicked(ActionEvent event) {


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Inventory_Records_Window.fxml"));
            Parent home = loader.load();

            Inventory_Controller inventoryController = loader.getController();
            inventoryController.setDashboardController(Dashboard_Controller.this);

            Scene scene2 = new Scene(home);
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.initOwner(home.getScene().getWindow());
            window.setResizable(false);
            window.setScene(scene2);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void itemFilterComboboxChanged() {
        populateItemSummaryTable(itemFilterCombobox.getValue(), itemField.getText());
    }

    @FXML
    void itemFieldKeyReleased() {
        populateItemSummaryTable(itemFilterCombobox.getValue(), itemField.getText());
    }

    //INVENTORY METHODS
    ConnectionClass connect;
    ObservableList<Item_Summary> item_summary;
    ResultSet result, rs;

    private int selectedPatientID;
    Refill_Window_Controller refillController;

    public void populateItemSummaryTable(String filter, String value) {

        item_summary = FXCollections.observableArrayList();
        int id;

        try {
            connect = new ConnectionClass();
            if (filter.equals("All")) {
                result = connect.select("SELECT items.item_id, items.item_name, items.item_type FROM gnhs_system_db.items ORDER BY item_name ASC; ");
            } else if (filter.equals("Item name")) {
                result = connect.select("SELECT items.item_id, items.item_name, items.item_type FROM gnhs_system_db.items WHERE item_name like '%" + value + "%' ORDER BY item_name ASC; ");
            } else if (filter.equals("Item type")) {
                result = connect.select("SELECT items.item_id, items.item_name, items.item_type FROM gnhs_system_db.items WHERE item_type like '%" + value + "%' ORDER BY item_name ASC; ");
            }
            while (result.next()) {
                id = result.getInt("item_id");

                Button refill = new Button("Refill");
                Button view = new Button("Details");

                refill.getStyleClass().add("actionButtons");
                view.getStyleClass().add("saveButtons");

                HBox buttonContainer = new HBox();
                buttonContainer.setId(id + "");

                view.setPrefWidth(100);
                refill.setPrefWidth(100);

                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);

                buttonContainer.setPadding(new Insets(10, 10, 10, 10));

                buttonContainer.getChildren().addAll(refill, view);

                refill.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        int containerID = Integer.parseInt(refill.getParent().getId());
                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Refill_Window.fxml"));
                            Parent home = loader.load();

                            Refill_Window_Controller refillController = loader.getController();
                            refillController.setDashboardController(Dashboard_Controller.this);

                            Refill_Window_Controller refillItem = loader.getController();
                            refillItem.setItemCombobox(containerID);
                            refillItem.itemSelectionChanged();

                            Scene scene2 = new Scene(home);
                            Stage window = new Stage();
                            window.initModality(Modality.APPLICATION_MODAL);
                            window.initOwner(home.getScene().getWindow());
                            window.setResizable(false);
                            window.setScene(scene2);
                            window.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                view.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int containerID = Integer.parseInt(view.getParent().getId());

                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item_Details_Window.fxml"));
                            Parent home = loader.load();

                            Item_Details_Controller itemDetailsController = loader.getController();
                            itemDetailsController.setDashboardController(Dashboard_Controller.this);

                            Item_Details_Controller itemDetails = loader.getController();
                            itemDetails.setItemCombobox(containerID);
                            itemDetails.itemComboboxSelectionChanged();
                            //itemDetails.populateItemFilterComboBox();
                            itemDetails.setItemFilterComboBox(containerID);
                            itemDetails.itemFilterComboBoxSelectionChange();

                            Scene scene2 = new Scene(home);
                            Stage window = new Stage();
                            window.initModality(Modality.APPLICATION_MODAL);
                            window.initOwner(home.getScene().getWindow());
                            window.setResizable(false);
                            window.setScene(scene2);
                            window.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                Batch batch = getValues(id);
                int quantity = batch.getQuantity();
                String unit = batch.getUnit();
                int dispensed = batch.getDispensed();
                int available = batch.getRemaining();

                item_summary.addAll(new Item_Summary(id, result.getString("item_name"), result.getString("item_type"),
                        quantity, unit, dispensed, available, buttonContainer));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        dispensedColumn.setCellValueFactory(new PropertyValueFactory<>("dispensed"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
        itemSummaryTable.setItems(item_summary);

    }

    private Batch getValues(int id) {

        Batch batch = new Batch();
        int quantity = 0;
        int dispense = 0;
        int available = 0;
        String unit = "";

        try {
            connect = new ConnectionClass();
            rs = connect.select("SELECT * FROM gnhs_system_db.batch WHERE items_item_id='" + id + "'");
            while (rs.next()) {
                quantity += rs.getInt("quantity");
                dispense += rs.getInt("num_of_dispensed");
                available += rs.getInt("remaining");
                unit = rs.getString("unit");
                batch.setQuantity(quantity);
                batch.setUnit(unit);
                batch.setRemaining(available);
                batch.setDispensed(dispense);
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batch;
    }

    private void populateItemsFilter() {
        ObservableList<String> itemFilter = FXCollections.observableArrayList();
        itemFilter.add("All");
        itemFilter.add("Item name");
        itemFilter.add("Item type");

        itemFilterCombobox.setItems(itemFilter);
        itemFilterCombobox.getSelectionModel().select(0);
    }

    //HEALTH APPRAISAL NODES
    @FXML
    private TableView<Patient_Information_Summary> patientInfoTable;

    @FXML
    private TableColumn<Patient_Information_Summary, String> patientFirstnameColumn;

    @FXML
    private TableColumn<Patient_Information_Summary, String> patientLastnameColumn;

    @FXML
    private TableColumn<Patient_Information_Summary, String> patientMiddleNameColumn;

    @FXML
    private TableColumn<Patient_Information_Summary, String> patientActionsColumn;

    @FXML
    private Button createRecordButton;

    @FXML
    private ComboBox<String> recordsFilterCombobox;

    @FXML
    private TextField recordSearchForField;


    //HEALTH APPRAISAL EVENTS
    @FXML
    void recordSearchForFieldEnterClicked(ActionEvent event) {

    }

    @FXML
    void recordsFilterComboboxSelectionChanged(ActionEvent event) {
        populatePatientInfoTable(recordsFilterCombobox.getValue(), recordSearchForField.getText());
    }

    @FXML
    void searchKeyReleased() {
        populatePatientInfoTable(recordsFilterCombobox.getValue(), recordSearchForField.getText());
    }

    @FXML
    void createRecordButtonClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Patient_Info_Window.fxml"));
            Parent home = loader.load();

            Patient_Info_Controller control = loader.getController();
            control.setDashboardController(Dashboard_Controller.this);

            Scene scene2 = new Scene(home);
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.initOwner(home.getScene().getWindow());
            window.setResizable(false);
            window.setScene(scene2);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //HEALTH APPRAISAL METHODS
    public void populatePatientInfoTable(String filter, String value) {

        ObservableList<Patient_Information_Summary> patient_summary = FXCollections.observableArrayList();

        try {
            connect = new ConnectionClass();

            if (filter.equals("All")) {
                rs = connect.select("SELECT patient_id, first_name, last_name, middle_name FROM gnhs_system_db.patients WHERE status='active' ORDER BY last_name ASC;");
            } else if (filter.equals("First name")) {
                rs = connect.select("SELECT patient_id, first_name, last_name, middle_name FROM gnhs_system_db.patients WHERE status='active' AND first_name LIKE '%" + value + "%' ORDER BY last_name ASC");
            } else if (filter.equals("Last name")) {
                rs = connect.select("SELECT patient_id, first_name, last_name, middle_name FROM gnhs_system_db.patients WHERE status='active' AND last_name LIKE '%" + value + "%' ORDER BY last_name ASC");
            } else if (filter.equals("Middle name")) {
                rs = connect.select("SELECT patient_id, first_name, last_name, middle_name FROM gnhs_system_db.patients WHERE status='active' AND middle_name LIKE '%" + value + "%' ORDER BY last_name ASC");

            }

            while (rs.next()) {
                Patient_Information_Summary summary = new Patient_Information_Summary();
                int id = rs.getInt("patient_id");
                summary.setPatientID(id);
                summary.setFirstname(rs.getString("first_name"));
                summary.setLastname(rs.getString("last_name"));
                summary.setMiddleName(rs.getString("middle_name"));

                Button deleteButton = new Button("Delete");
                Button updateButton = new Button("Update");
                Button addRecordButton = new Button("Add Record");

                deleteButton.getStyleClass().add("clearButtons");
                updateButton.getStyleClass().add("saveButtons");
                addRecordButton.getStyleClass().add("actionButtons");

                HBox buttonContainer = new HBox();
                buttonContainer.setId(id + "");

                deleteButton.setPrefWidth(100);
                updateButton.setPrefWidth(100);
                addRecordButton.setPrefWidth(100);

                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);

                buttonContainer.setPadding(new Insets(10, 10, 10, 10));

                buttonContainer.getChildren().addAll(addRecordButton, updateButton, deleteButton);

                updateButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int containerID = Integer.parseInt(updateButton.getParent().getId());
                        setSelectedPatientID(containerID);

                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Patient_Info_Window.fxml"));
                            Parent home = loader.load();

                            Patient_Info_Controller control = loader.getController();
                            control.setDashboardController(Dashboard_Controller.this);

                            Patient_Info_Controller controller = loader.getController();
                            controller.setPatientID(getSelectedPatientID());
                            controller.setUpdating(true);
                            controller.fetchInformation();

                            Scene scene2 = new Scene(home);
                            Stage window = new Stage();
                            window.initModality(Modality.APPLICATION_MODAL);
                            window.initOwner(home.getScene().getWindow());
                            window.setResizable(false);
                            window.setScene(scene2);
                            window.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                addRecordButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int containerID = Integer.parseInt(addRecordButton.getParent().getId());

                        try {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Records_Window.fxml"));
                            Parent home = loader.load();

                            Records_Controller control = loader.getController();
                            control.setDashboardController(Dashboard_Controller.this);

                            Records_Controller recordsController = loader.getController();
                            recordsController.setActiveID(containerID);
                            recordsController.printDetails();

                            Scene scene2 = new Scene(home);
                            Stage window = new Stage();
                            window.initModality(Modality.APPLICATION_MODAL);
                            window.initOwner(home.getScene().getWindow());
                            window.setResizable(false);
                            window.setScene(scene2);
                            window.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int containerID = Integer.parseInt(deleteButton.getParent().getId());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Record");
                        alert.setHeaderText("The record will be deleted permanently.");
                        alert.setResizable(false);
                        alert.setContentText("Are you sure you want to delete this record?");

                        Optional<ButtonType> result = alert.showAndWait();
                        ButtonType button = result.orElse(ButtonType.CANCEL);

                        if (button == ButtonType.OK) {

                            int affectedRows = 0;

                            try {
                                connect = new ConnectionClass();
                                affectedRows = connect.update(String.format("UPDATE gnhs_system_db.patients SET status = 'removed' WHERE (patient_id = '%d')", containerID));

                                if (affectedRows > 0) {
                                    Notifications updateSuccessful = new Notifications("Success!", "Patient record successfully removed.");
                                    updateSuccessful.showInformation();
                                }

                                connect.close();
                            } catch (Exception e) {
                                Notifications updateUnsuccessful = new Notifications("Error!", e.getMessage());
                                updateUnsuccessful.showError();
                            }
                        }
                        populatePatientInfoTable("All", "");
                    }
                });

                summary.setActions(buttonContainer);

                patient_summary.add(summary);

            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        patientFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        patientLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        patientMiddleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        patientActionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
        patientInfoTable.setItems(patient_summary);
    }

    //NAVIGATION NODES
    @FXML
    private Button healthAppraisalNavButton;
    @FXML
    private Button inventoryNavButton;

    //NAVIGATION ACTION EVENTS
    @FXML
    void healthAppraisalNavButtonClicked() {

        if (!healthAppraisalContainer.isVisible()) {
            healthAppraisalContainer.setVisible(true);
            healthAppraisalNavButton.setStyle("-fx-background-color: #1c84c6");
            inventoryNavButton.setStyle("-fx-background-color: transparent");

            if (inventoryContainer.isVisible()) {
                inventoryContainer.setVisible(false);
            }
        }

        populateItemSummaryTable("All", "");
    }

    @FXML
    void inventoryNavButtonClicked() {

        if (!inventoryContainer.isVisible()) {
            inventoryContainer.setVisible(true);
            inventoryNavButton.setStyle("-fx-background-color: #1c84c6");
            healthAppraisalNavButton.setStyle("-fx-background-color: transparent");


            if (healthAppraisalContainer.isVisible()) {
                healthAppraisalContainer.setVisible(false);
            }
        }

        populatePatientInfoTable("All", "");
    }

    public int getSelectedPatientID() {
        return this.selectedPatientID;
    }

    public void setSelectedPatientID(int id) {
        this.selectedPatientID = id;
    }

    private void populateRecordsFilter() {
        ObservableList<String> recordsFiler = FXCollections.observableArrayList();
        recordsFiler.add("All");
        recordsFiler.add("First name");
        recordsFiler.add("Last name");
        recordsFiler.add("Middle name");

        recordsFilterCombobox.setItems(recordsFiler);
        recordsFilterCombobox.getSelectionModel().select(0);
    }

    public void setRefillController(Refill_Window_Controller controller) {
        refillController = controller;
    }

    public String getRecordsFilterValue() {
        return recordsFilterCombobox.getValue();
    }

    public String getSearchValue() {
        return recordSearchForField.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateItemsFilter();
        populateItemSummaryTable("All", "");
        populateRecordsFilter();
        populatePatientInfoTable(recordsFilterCombobox.getValue(), recordSearchForField.getText());
    }
}