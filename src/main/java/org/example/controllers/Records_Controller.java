package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.layout.HBox;
import org.example.connection.ConnectionClass;
import org.example.models.*;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Records_Controller implements Initializable {

    @FXML
    private TextArea patientInformationArea;

    @FXML
    private TextField BPField;

    @FXML
    private TextField TEMPField;

    @FXML
    private TextField RRField;

    @FXML
    private TextField PRField;

    @FXML
    private TextField O2Field;

    @FXML
    private TextArea chiefComplaintArea;

    @FXML
    private TextArea managementArea;

    @FXML
    private ChoiceBox<Item> itemChoiceBox;

    @FXML
    private TextField quantityField;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Item_Given> itemTable;

    @FXML
    private TableColumn<Item_Given, String> itemColumn;

    @FXML
    private TableColumn<Item_Given, Integer> quantityColumn;

    @FXML
    private TableColumn<Item_Given, Node> actionColumn;

    @FXML
    private Button clearButton;

    @FXML
    private Button saveButton;

    private int activeID;
    private int recordID;
    private ResultSet recordIDResultSet;
    private ConnectionClass connect;
    private ResultSet patient, vacHis, medHis, rs, checkupHistory, itemsGiven;
    private final ObservableList<Item_Given> itemGiven = FXCollections.observableArrayList();
    private final ObservableList<Item> choices = FXCollections.observableArrayList();
    Dashboard_Controller dashboardController = new Dashboard_Controller();

    @FXML
    void addButtonClicked() {
        addItem();
        quantityField.setText("1");
    }

    @FXML
    void clearButtonClicked() {

        clear();

    }

    @FXML
    void saveButtonClicked() {
        insertCheckUpDetails();
        printDetails();
        insertItems();
        deductItems();
        clear();

        if (!(itemChoiceBox.getItems().isEmpty())) {
            addButton.setDisable(true);
        }
    }

    public int getActiveID() {
        return activeID;
    }

    public void setActiveID(int activeID) {
        this.activeID = activeID;
    }

    public void printDetails() {

        patientInformationArea.clear();

        int id = getActiveID();
        ObservableList<History> vaccineHistory = FXCollections.observableArrayList();
        ObservableList<History> medicalHistory = FXCollections.observableArrayList();
        ArrayList<Check_Up_Record> record = new ArrayList<>();

        Patient_Info patientInfo = new Patient_Info();

        int count = 0;


        try {
            connect = new ConnectionClass();
            patient = connect.select(String.format("SELECT * FROM gnhs_system_db.patients WHERE patient_id = '%d'", id));
            vacHis = connect.select(String.format("SELECT vaccination_description FROM gnhs_system_db.vaccination_or_immunization_history WHERE patient_id='%d'", id));
            medHis = connect.select(String.format("SELECT history_description FROM gnhs_system_db.medical_history WHERE patient_id='%d'", id));
            checkupHistory = connect.select(String.format("SELECT * FROM gnhs_system_db.record WHERE patient_id='%d' ORDER BY record_id DESC", id));

            while (patient.next()) {

                patientInfo.setFirstname(patient.getString("first_name"));
                patientInfo.setLastname(patient.getString("last_name"));
                patientInfo.setMiddleName(patient.getString("middle_name"));
                patientInfo.setBirthdate(patient.getDate("birthdate").toLocalDate());
                patientInfo.setSex(patient.getString("sex"));
                patientInfo.setAge(patient.getInt("age"));
                patientInfo.setWeight(patient.getDouble("weight"));
                patientInfo.setHeight(patient.getDouble("height"));
                patientInfo.setBMI(patient.getDouble("bmi"));

            }

            patientInformationArea.appendText("Full name: " + patientInfo.getLastname() + ", " + patientInfo.getFirstname() + " " + patientInfo.getMiddleName());
            patientInformationArea.appendText("\nBirthdate: " + patientInfo.getBirthdate() + "\t\tSex: " + patientInfo.getSex() + "\t\tAge: " + patientInfo.getAge() +
                    "\t\tWeight: " + patientInfo.getWeight() + "kg." + "\t\tHeight: " + patientInfo.getHeight() + "cm." + "\t\tBMI: " + patientInfo.getBMI());

            String currentCondition;
            if (patientInfo.getCurrentCondition().equals("")) {
                currentCondition = "Not Specified";
            } else {
                currentCondition = patientInfo.getCurrentCondition();
            }
            patientInformationArea.appendText("\n\nCURRENT CONDITION:\n" + currentCondition);

            while (vacHis.next()) {
                History vaccineHis = new History();
                vaccineHis.setHistoryDescription(vacHis.getString("vaccination_description"));
                vaccineHistory.add(vaccineHis);
            }

            patientInformationArea.appendText("\n\nVACCINATION OR IMMUNIZATION HISTORY\n");

            for (History vac : vaccineHistory) {
                patientInformationArea.appendText(vac.getHistoryDescription() + ", ");
            }

            while (medHis.next()) {
                History medicalHis = new History();
                medicalHis.setHistoryDescription(medHis.getString("history_description"));
                medicalHistory.add(medicalHis);
            }

            patientInformationArea.appendText("\n\nMEDICAL HISTORY:\n");

            for (History med : medicalHistory) {
                patientInformationArea.appendText(med.getHistoryDescription() + ", ");
            }

            while (checkupHistory.next()) {
                Check_Up_Record checkRec = new Check_Up_Record();
                checkRec.setId(checkupHistory.getInt("record_id"));
                checkRec.setBP(checkupHistory.getString("bp"));
                checkRec.setTEMP(checkupHistory.getFloat("temp"));
                checkRec.setRR(checkupHistory.getInt("rr"));
                checkRec.setPR(checkupHistory.getInt("pr"));
                checkRec.setO2(checkupHistory.getInt("o2"));
                checkRec.setChiefComplaint(checkupHistory.getString("chief_complaint"));
                checkRec.setTreatment(checkupHistory.getString("management_or_treatment"));
                checkRec.setDateTaken(checkupHistory.getDate("date_taken").toLocalDate());
                record.add(checkRec);
                ++count;
            }


            patientInformationArea.appendText("\n\nCHECKUP HISTORY:");

            for (Check_Up_Record rec : record) {

                ArrayList<Item_Given> givenItems = new ArrayList<>();

                patientInformationArea.appendText(String.format("\n-------------------------------------------------------------- %d --------------------------------------------------------------", count--));
                patientInformationArea.appendText("\nDate Taken: " + rec.getDateTaken());
                patientInformationArea.appendText(String.format("\nBP: %s mmhg\t\tTEMP: %.2f °C\t\tRR: %d brpm\t\tPR: %d bpm\t\tO2: %d",
                        rec.getBP(), rec.getTEMP(), rec.getRR(), rec.getPR(), rec.getO2()));
                patientInformationArea.appendText("\nChief Complaint:");
                patientInformationArea.appendText(" " + rec.getChiefComplaint());
                patientInformationArea.appendText("\nManagement or Treatment:");
                patientInformationArea.appendText(" " + rec.getTreatment());
                patientInformationArea.appendText("\nMedicine or Equipment given: ");

                itemsGiven = connect.select(String.format("SELECT DISTINCT items.item_name, medicine_or_equipment_given.quantity, batch.unit FROM  gnhs_system_db.medicine_or_equipment_given\n" +
                        " JOIN gnhs_system_db.items\n" +
                        " ON medicine_or_equipment_given.items_item_id=items.item_id\n" +
                        " JOIN gnhs_system_db.batch\n" +
                        " ON items.item_id=batch.items_item_id WHERE medicine_or_equipment_given.record_record_id='%d'", rec.getId()));
                String items = "";

                while (itemsGiven.next()) {
                    Item_Given item = new Item_Given();
                    item.setItemName(itemsGiven.getString("item_name"));
                    item.setQuantity(itemsGiven.getInt("quantity"));
                    item.setUnit(itemsGiven.getString("unit"));
                    givenItems.add(item);
                }

                for (Item_Given item : givenItems) {
                    items = items.concat(String.format("%s %d %s, ", item.getItemName(), item.getQuantity(), item.getUnit()));
                }

                patientInformationArea.appendText(items);
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateItemsChoiceBox() {

        try {
            connect = new ConnectionClass();
            rs = connect.select(String.format("SELECT * FROM gnhs_system_db.items"));
            while (rs.next()) {
                Item itemChoice = new Item();
                itemChoice.setItemID(rs.getInt("item_id"));
                itemChoice.setItemName(rs.getString("item_name"));
                choices.add(itemChoice);
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemChoiceBox.setItems(choices);
        itemChoiceBox.getSelectionModel().select(0);
    }

    private void insertCheckUpDetails() {

        boolean insert = true;
        LocalDate dateNow = LocalDate.now();
        String bp = BPField.getText();
        String complaint = chiefComplaintArea.getText();
        String treatment = managementArea.getText();

        Float temp = null;
        int rr = 0;
        int pr = 0;
        int o2 = 0;

        try {
            temp = Float.parseFloat(TEMPField.getText());
            rr = Integer.parseInt(RRField.getText());
            pr = Integer.parseInt(PRField.getText());
            o2 = Integer.parseInt(O2Field.getText());
        } catch (Exception e) {
            //e.printStackTrace();
            insert = false;
            Notifications error = new Notifications("Error", "Invalid input or a field is blank.");
            error.showError();
        }

        if (insert) {
            try {

                int affectedRows = 0;
                connect = new ConnectionClass();
                affectedRows = connect.insert(String.format("INSERT INTO gnhs_system_db.record (`bp`, `temp`, `rr`, `pr`, `o2`, `chief_complaint`, `management_or_treatment`, `date_taken`, `patient_id`) " +
                        "VALUES ('%s', '%f', '%d', '%d', '%d', '%s', '%s', '" + dateNow + "', '%d')", bp, temp, rr, pr, o2, complaint, treatment, getActiveID()));

                recordIDResultSet = connect.select("SELECT last_insert_id() as id");
                while (recordIDResultSet.next()) {
                    recordID = recordIDResultSet.getInt("id");
                    //System.out.println("last inserted record id: "+recordID);
                }

                if (affectedRows > 0) {
                    Notifications success = new Notifications("Success!", "The record was added succesfully.");
                    success.showInformation();
                }
                connect.close();
            } catch (Exception e) {
                //e.printStackTrace();
                Notifications error = new Notifications("Error", e.getMessage());
                error.showError();
            }
        }
    }

    private void populateItemsTable() {

        if (!(itemGiven.isEmpty())) {
            itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
            itemTable.setItems(itemGiven);
        }

    }

    private Node addNodes(int itemID) {

        History history = new History();

        Button removeButton = new Button("x");
        Button plusButton = new Button("+");
        Button minusButton = new Button("-");

        minusButton.setSkin(new ButtonSkin(minusButton) {
            {
                this.consumeMouseEvents(false);
            }
        });

        plusButton.setSkin(new ButtonSkin(plusButton) {
            {
                this.consumeMouseEvents(false);
            }
        });

        HBox buttonContainer = new HBox();

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(5.0);
        buttonContainer.setId(itemID + "");
        buttonContainer.setPadding(new Insets(10, 10, 10, 10));

        removeButton.getStyleClass().add("clearButtons");

        buttonContainer.getChildren().addAll(removeButton);

        removeButton.setOnAction(event -> {

            int id = Integer.parseInt(removeButton.getParent().getId());

            for (Iterator<Item_Given> it = itemGiven.iterator(); it.hasNext(); ) {

                Item_Given item = it.next();

                if (item.getItemID() == id) {
                    it.remove();
                    Item removed = new Item();
                    removed.setItemID(id);
                    removed.setItemName(item.getItemName());
                    choices.add(removed);
                    itemChoiceBox.getSelectionModel().select(0);
                    if (!(itemChoiceBox.getItems().isEmpty())) {
                        addButton.setDisable(false);
                    }
                    if (itemGiven != null) {
                        populateItemsTable();
                    }
                }
            }
        });

        /*plusButton.setOnAction(event -> {

            int id = Integer.parseInt(removeButton.getParent().getId());

            try{
                for(Iterator<Item_Given> it = itemGiven.iterator(); it.hasNext();){

                    Item_Given item=it.next();

                    if (item.getItemID() == id) {
                        int quantity=item.getQuantity();
                        item.setQuantity(quantity+1);
                        try{
                            populateItemsTable();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            populateItemsTable();
        });



        minusButton.setOnAction(event -> {



            int id = Integer.parseInt(removeButton.getParent().getId());

            try{
                for(Iterator<Item_Given> it = itemGiven.iterator(); it.hasNext();){

                    Item_Given item=it.next();

                    if (item.getItemID() == id) {
                        int quantity=item.getQuantity();
                        item.setQuantity(quantity-1);
                        if(quantity<=0){
                            choices.add(item);
                            itemGiven.remove(item);
                        }
                        try{
                            populateItemsTable();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }


            populateItemsTable();
        });*/

        return buttonContainer;
    }

    private void addItem() {

        Item selectedItem = itemChoiceBox.getValue();
        Item_Given item = new Item_Given();
        item.setItemID(selectedItem.getItemID());
        item.setItemName(selectedItem.getItemName());
        item.setQuantity(Integer.parseInt(quantityField.getText()));
        item.setAction(addNodes(item.getItemID()));
        item.setRecordID(recordID);

        choices.remove(selectedItem);
        itemChoiceBox.getSelectionModel().select(0);
        if (itemChoiceBox.getItems().isEmpty()) {
            addButton.setDisable(true);
        }

        itemGiven.add(item);
        populateItemsTable();
    }

    private void insertItems() {

        if (!(itemGiven.isEmpty())) {
            for (Item_Given item : itemGiven) {
                try {
                    connect = new ConnectionClass();
                    connect.insert(String.format("INSERT INTO gnhs_system_db.medicine_or_equipment_given (items_item_id, quantity, record_record_id) " +
                            " VALUES ('%d', '%d', '%d')", item.getItemID(), item.getQuantity(), recordID));
                    connect.close();
                } catch (Exception e) {
                    Notifications error = new Notifications("Error!", e.getMessage());
                    error.showError();
                }
            }
        }
    }

    public ArrayList<Batch> fetchAvailableBatch(int itemID) {

        ArrayList<Batch> batch = new ArrayList<>();

        try {
            connect = new ConnectionClass();
            rs = connect.select(String.format("SELECT batch_id, remaining FROM gnhs_system_db.batch WHERE items_item_id='%d' AND remaining!='0'", itemID));

            while (rs.next()) {
                Batch available = new Batch();
                available.setBatchID(rs.getInt("batch_id"));
                available.setRemaining(rs.getInt("remaining"));
                batch.add(available);
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return batch;
    }

    private void deductItems() {
        int count = 1;

        for (Item_Given item : itemGiven) {

            int innerCount = 1;

            System.out.printf("Iteration [%d] %s:\n", count, item.getItemName());

            int excess=0;
            boolean withExcess=false;

            for (Iterator<Batch> it = fetchAvailableBatch(item.getItemID()).iterator(); it.hasNext(); ) {
                Batch batch = it.next();

                int quantity=item.getQuantity();
                int remaining=batch.getRemaining();

                if(withExcess){

                    quantity=excess;
                }

                if (item.getQuantity() <= batch.getRemaining()) {

                    withExcess=false;
                    System.out.printf("\t\tBatch no: [%d] | [less than or equal remaining]\n", innerCount);
                    System.out.printf("\t\tdeducting quantity of %d from remaining: %d\n\n",quantity, remaining);

                    //System.out.printf("item quantity: %d - batch quantity: %d\n",item.getQuantity(), batch.getRemaining());

                    try {
                        connect = new ConnectionClass();
                        connect.update(String.format("UPDATE gnhs_system_db.batch SET num_of_dispensed = num_of_dispensed + '%d', remaining = remaining - '%d' " +
                                " WHERE (batch_id = '%d') and (items_item_id = '%d');", quantity, quantity, batch.getBatchID(), item.getItemID()));
                        connect.close();

                        //System.out.printf("less than remaining: deducting %d from remaining %d int batch id: %d\n", item.getQuantity(), batch.getRemaining(), batch.getBatchID());

                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (item.getQuantity() > batch.getQuantity() && it.hasNext()) {

                    excess=item.getQuantity()-batch.getRemaining();
                    withExcess=true;

                    System.out.printf("\t\tBatch no: [%d] | [more than remaining]\n", innerCount);
                    System.out.printf("\t\tdeducting quantity of %d from remaining: %d\n\n",remaining, remaining);

                    //System.out.printf("item quantity: %d - excess: %d\n",item.getQuantity(), excess);

                    try {
                        connect = new ConnectionClass();
                        connect.update(String.format("UPDATE gnhs_system_db.batch SET num_of_dispensed = num_of_dispensed + '%d', remaining = remaining - '%d' " +
                                " WHERE (batch_id = '%d') and (items_item_id = '%d');", remaining, remaining, batch.getBatchID(), item.getItemID()));
                        connect.close();
                        //System.out.printf("more than remaining: deducting %d from remaining %d int batch id: %d\n", excess, batch.getRemaining(), batch.getBatchID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                innerCount++;
            }
            count++;
        }
    }

    private void clear() {
        BPField.clear();
        TEMPField.clear();
        PRField.clear();
        RRField.clear();
        O2Field.clear();
        chiefComplaintArea.clear();
        managementArea.clear();
        quantityField.setText("1");
        itemGiven.clear();
        populateItemsChoiceBox();
        populateItemsTable();
    }

    public void setDashboardController(Dashboard_Controller controller) {
        dashboardController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateItemsChoiceBox();
    }
}
