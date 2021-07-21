package org.example.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.example.connection.ConnectionClass;
import org.example.models.History;
import org.example.models.Notifications;
import org.example.models.Patient_Info;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Patient_Info_Controller implements Initializable {

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private ChoiceBox<String> sexChoiceBox;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private TextField ageField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField BMIField;

    @FXML
    private TextField BMIInterpretationField;

    @FXML
    private TextField currentConditionField;

    @FXML
    private TextField medicalHistoryField;

    @FXML
    private Button addMedicalHistoryButton;

    @FXML
    private TableView<History> medicalHistoryTable;

    @FXML
    private TableColumn<History, String> medicalHistoryDescriptionColumn;

    @FXML
    private TableColumn<History, Node> medicalHistoryActionColumn;

    @FXML
    private TextField vaccinationHistoryField;

    @FXML
    private Button addVaccinationHistoryButton;

    @FXML
    private TableView<History> vaccinationHistoryTable;

    @FXML
    private TableColumn<History, String> vaccinationHistoryDescriptionColumn;

    @FXML
    private TableColumn<History, Node> vaccinationHistoryActionColumn;

    @FXML
    private Button clearButton;

    @FXML
    private Button saveButton;

    private int tempMedicalHistoryID = 0;
    private int tempVaccinationHistoryID = 0;
    private final ObservableList<History> medicalHistory = FXCollections.observableArrayList();
    private final ObservableList<History> vaccinationHistory = FXCollections.observableArrayList();
    private ConnectionClass connect, singleConnect;
    private ResultSet rs;
    private int lastInsertID;
    ResultSet last_inserted_id;
    ArrayList<TextField> fields = new ArrayList();

    private int patientID;

    private boolean updating;

    Dashboard_Controller dashboardController = new Dashboard_Controller();

    @FXML
    void addMedicalHistoryButtonClicked() {

        if (!isUpdating()) {

            if (!(medicalHistoryField.getText().isBlank())) {
                ++tempMedicalHistoryID;
                History history = new History();

                Button removeButton = new Button("Remove");

                HBox buttonContainer = new HBox();

                removeButton.setPrefWidth(80);

                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);
                buttonContainer.setId(tempMedicalHistoryID + "");
                buttonContainer.setPadding(new Insets(10, 10, 10, 10));
                removeButton.getStyleClass().add("clearButtons");

                buttonContainer.getChildren().addAll(removeButton);

                removeButton.setOnAction(event -> {
                    int id = Integer.parseInt(removeButton.getParent().getId());

                    for (Iterator<History> his = medicalHistory.iterator(); his.hasNext(); ) {

                        History sample = his.next();

                        if (sample.getHistoryID() == id) {
                            his.remove();
                            if (medicalHistory != null) {
                                populateMedicalHistoryTable();
                            }
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            medicalHistoryField.requestFocus();
                        }
                    });
                });

                history.setHistoryID(tempMedicalHistoryID);
                history.setHistoryDescription(medicalHistoryField.getText());
                history.setActions(buttonContainer);

                medicalHistory.add(history);

                populateMedicalHistoryTable();
            }

            medicalHistoryField.clear();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    medicalHistoryField.requestFocus();
                }
            });

        } else {

            medicalHistory.clear();
            try {
                connect = new ConnectionClass();
                connect.insert(String.format("INSERT INTO gnhs_system_db.medical_history (history_description, patient_id) VALUES ('%s', '%d')", medicalHistoryField.getText(), getPatientID()));
                connect.close();
            } catch (Exception e) {
                Notifications error = new Notifications("Error!", e.getMessage());
                error.showError();
            }

            medicalHistoryField.clear();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    medicalHistoryField.requestFocus();
                }
            });
            populateHistoryTableForUpdate();

        }
    }

    @FXML
    void addVaccinationHistoryButtonClicked() {

        if (!isUpdating()) {
            if (!(vaccinationHistoryField.getText().isBlank())) {
                ++tempVaccinationHistoryID;
                History history = new History();

                Button removeButton = new Button("Remove");

                HBox buttonContainer = new HBox();

                removeButton.setPrefWidth(80);

                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);
                buttonContainer.setId(tempVaccinationHistoryID + "");
                buttonContainer.setPadding(new Insets(10, 10, 10, 10));
                removeButton.getStyleClass().add("clearButtons");

                buttonContainer.getChildren().addAll(removeButton);

                removeButton.setOnAction(event -> {
                    int id = Integer.parseInt(removeButton.getParent().getId());

                    for (Iterator<History> vacHistory = vaccinationHistory.iterator(); vacHistory.hasNext(); ) {

                        History sample = vacHistory.next();

                        if (sample.getHistoryID() == id) {
                            vacHistory.remove();

                            if (vaccinationHistory != null) {
                                populateVaccineHistoryTable();
                            }
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            vaccinationHistoryField.requestFocus();
                        }
                    });
                });

                history.setHistoryID(tempVaccinationHistoryID);
                history.setHistoryDescription(vaccinationHistoryField.getText());
                history.setActions(buttonContainer);

                vaccinationHistory.add(history);

                populateVaccineHistoryTable();
            }
            vaccinationHistoryField.clear();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vaccinationHistoryField.requestFocus();
                }
            });
        } else {

            vaccinationHistory.clear();
            try {
                connect = new ConnectionClass();
                connect.insert(String.format("INSERT INTO gnhs_system_db.vaccination_or_immunization_history (vaccination_description, patient_id) VALUES ('%s', '%d')", vaccinationHistoryField.getText(), getPatientID()));
                connect.close();
            } catch (Exception e) {
                Notifications error = new Notifications("Error!", e.getMessage());
                error.showError();
            }

            vaccinationHistoryField.clear();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vaccinationHistoryField.requestFocus();
                }
            });
            populateVaccinationTableForUpdate();
        }
    }

    @FXML
    void clearButtonClicked() {
        clear();
    }

    @FXML
    void keyReleasedOnField(KeyEvent event) {
        computeBMI();
    }

    @FXML
    void medicalHistoryFieldEnterPressed() {

        if (!(medicalHistoryField.getText().isBlank())) {
            ++tempMedicalHistoryID;
            History history = new History();

            Button removeButton = new Button("Remove");

            HBox buttonContainer = new HBox();

            removeButton.setPrefWidth(80);

            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setSpacing(10.0);
            buttonContainer.setId(tempMedicalHistoryID + "");
            buttonContainer.setPadding(new Insets(10, 10, 10, 10));

            buttonContainer.getChildren().addAll(removeButton);
            removeButton.getStyleClass().add("clearButtons");

            removeButton.setOnAction(event -> {
                int id = Integer.parseInt(removeButton.getParent().getId());

                for (Iterator<History> his = medicalHistory.iterator(); his.hasNext(); ) {

                    History sample = his.next();

                    if (sample.getHistoryID() == id) {
                        his.remove();
                        if (medicalHistory != null) {
                            populateMedicalHistoryTable();
                        }
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        medicalHistoryField.requestFocus();
                    }
                });
            });

            history.setHistoryID(tempMedicalHistoryID);
            history.setHistoryDescription(medicalHistoryField.getText());
            history.setActions(buttonContainer);

            medicalHistory.add(history);

            populateMedicalHistoryTable();
        }

        medicalHistoryField.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                medicalHistoryField.requestFocus();
            }
        });


    }

    @FXML
    void saveButtonClicked() {

        if (!isUpdating()) {
            addPatientInfo();

            clear();
        } else {
            updatePatient(getPatientID());
            System.out.println();
        }

        dashboardController.populatePatientInfoTable(dashboardController.getRecordsFilterValue(), dashboardController.getSearchValue());

    }

    @FXML
    void vaccinationHistoryFieldClicked() {
        if (!(vaccinationHistoryField.getText().isBlank())) {
            ++tempVaccinationHistoryID;
            History history = new History();

            Button removeButton = new Button("Remove");

            HBox buttonContainer = new HBox();

            removeButton.setPrefWidth(80);

            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setSpacing(10.0);
            buttonContainer.setId(tempVaccinationHistoryID + "");
            buttonContainer.setPadding(new Insets(10, 10, 10, 10));

            removeButton.getStyleClass().add("clearButtons");

            buttonContainer.getChildren().addAll(removeButton);

            removeButton.setOnAction(event -> {
                int id = Integer.parseInt(removeButton.getParent().getId());

                for (Iterator<History> vacHistory = vaccinationHistory.iterator(); vacHistory.hasNext(); ) {

                    History sample = vacHistory.next();

                    if (sample.getHistoryID() == id) {
                        vacHistory.remove();

                        if (vaccinationHistory != null) {
                            populateVaccineHistoryTable();
                        }
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vaccinationHistoryField.requestFocus();
                    }
                });
            });

            history.setHistoryID(tempVaccinationHistoryID);
            history.setHistoryDescription(vaccinationHistoryField.getText());
            history.setActions(buttonContainer);

            vaccinationHistory.add(history);

            populateVaccineHistoryTable();
        }
        vaccinationHistoryField.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vaccinationHistoryField.requestFocus();
            }
        });
    }

    @FXML
    void birthdatePickerDatePicked() {
        computeAge();
    }

    private void updatePatient(int id) {

        Patient_Info patient = new Patient_Info();
        patient.setFirstname(firstnameField.getText());
        patient.setLastname(lastnameField.getText());
        patient.setMiddleName(middleNameField.getText());
        patient.setSex(sexChoiceBox.getValue());
        patient.setBirthdate(birthdatePicker.getValue());
        patient.setAge(Integer.parseInt(ageField.getText()));
        patient.setHeight(Double.parseDouble(heightField.getText()));
        patient.setWeight(Double.parseDouble(weightField.getText()));
        patient.setCurrentCondition(currentConditionField.getText());

        try {

            int affectedRows = 0;
            connect = new ConnectionClass();
            affectedRows = connect.update(String.format("UPDATE `gnhs_system_db`.`patients` " +
                            "SET `first_name` = '%s', `last_name` = '%s', `middle_name` = '%s', `sex` = '%s', " +
                            " `birthdate` = '" + patient.getBirthdate() + "', `age` = '%d', `weight` = '%f', `height` = '%f', `bmi` = '%f', " +
                            " `current_condition` = '%s' WHERE (`patient_id` = '%d');"
                    , patient.getFirstname(), patient.getLastname(), patient.getMiddleName(), patient.getSex(),
                    patient.getAge(), patient.getWeight(), patient.getHeight(), patient.getBMI(), patient.getCurrentCondition(), id));


            if (affectedRows > 0) {
                Notifications success = new Notifications("Success!", "The patient record was successfully updated.");
                success.showInformation();
            }

            connect.close();
        } catch (Exception e) {
            Notifications error = new Notifications("Error!", e.getMessage());
            error.showError();
        }
    }

    private void populateHistoryTableForUpdate() {
        try {
            connect = new ConnectionClass();
            rs = connect.select(String.format("SELECT * FROM gnhs_system_db.medical_history WHERE patient_id = '%d'", getPatientID()));


            while (rs.next()) {
                int medID = rs.getInt("medical_history_id");

                Button removeButton = new Button("Remove");
                HBox buttonContainer = new HBox();
                removeButton.setPrefWidth(80);
                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);
                buttonContainer.setId(medID + "");
                buttonContainer.setPadding(new Insets(10, 10, 10, 10));
                buttonContainer.getChildren().addAll(removeButton);
                removeButton.getStyleClass().add("clearButtons");


                removeButton.setOnAction(event -> {
                    int id = Integer.parseInt(removeButton.getParent().getId());

                    try {
                        connect = new ConnectionClass();
                        connect.delete(String.format("DELETE FROM gnhs_system_db.medical_history WHERE (medical_history_id = '%d') and (patient_id = '%d')", id, getPatientID()));
                        connect.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for (Iterator<History> his = medicalHistory.iterator(); his.hasNext(); ) {

                        History sample = his.next();

                        if (sample.getHistoryID() == id) {
                            his.remove();
                            if (medicalHistory != null) {
                                populateMedicalHistoryTable();
                            }
                        }

                    }

                    //populateMedicalHistoryTable();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            medicalHistoryField.requestFocus();

                        }
                    });


                });

                History medHis = new History();
                medHis.setHistoryID(rs.getInt("medical_history_id"));
                medHis.setHistoryDescription(rs.getString("history_description"));
                medHis.setActions(buttonContainer);
                medicalHistory.add(medHis);
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateVaccinationTableForUpdate() {
        try {
            connect = new ConnectionClass();
            rs = connect.select(String.format("SELECT * FROM gnhs_system_db.vaccination_or_immunization_history WHERE patient_id = '%d'", getPatientID()));

            while (rs.next()) {
                int hisID = rs.getInt("id");
                Button removeButton = new Button("Remove");
                HBox buttonContainer = new HBox();
                removeButton.setPrefWidth(80);
                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setSpacing(10.0);
                buttonContainer.setId(hisID + "");
                buttonContainer.setPadding(new Insets(10, 10, 10, 10));

                removeButton.getStyleClass().add("clearButtons");
                buttonContainer.getChildren().add(removeButton);

                removeButton.setOnAction(event -> {
                    int id = Integer.parseInt(removeButton.getParent().getId());

                    try {
                        connect = new ConnectionClass();
                        connect.delete(String.format("DELETE FROM gnhs_system_db.vaccination_or_immunization_history WHERE (id = '%d') and (patient_id = '%d')", id, getPatientID()));
                        connect.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for (Iterator<History> vacHistory = vaccinationHistory.iterator(); vacHistory.hasNext(); ) {
                        History sample = vacHistory.next();

                        if (sample.getHistoryID() == id) {
                            vacHistory.remove();

                            if (vaccinationHistory != null) {
                                populateVaccineHistoryTable();
                            }
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            vaccinationHistoryField.requestFocus();
                        }
                    });

                });


                History vacHis = new History();
                vacHis.setHistoryID(rs.getInt("id"));
                vacHis.setHistoryDescription(rs.getString("vaccination_description"));
                vacHis.setActions(buttonContainer);
                vaccinationHistory.add(vacHis);
            }

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void computeBMI() {

        String BMIInterpretation = "";
        double height = 0;
        double weight = 0;

        if (weightField.getText().isBlank()) {
            weight = Double.parseDouble(weightField.getPromptText());
        } else {
            weight = Double.parseDouble(weightField.getText());
        }

        if (heightField.getText().isBlank()) {
            height = Double.parseDouble(heightField.getPromptText());
        } else {
            height = Double.parseDouble(heightField.getText());
        }

        double heightInMeter = height / 100;
        double heightInMeterSquared = Math.pow(heightInMeter, 2);

        double BMI = weight / heightInMeterSquared;
        BMIField.setText(String.format("%1.2f", BMI));

        if (BMI >= 0 && BMI <= 16.0) {
            BMIInterpretation = "Severely underweight";
        } else if (BMI >= 16.1 && BMI <= 18.5) {
            BMIInterpretation = "Underweight";
        } else if (BMI >= 18.6 && BMI <= 25) {
            BMIInterpretation = "Normal";
        } else if (BMI >= 25.1 && BMI <= 30) {
            BMIInterpretation = "Overweight";
        } else if (BMI >= 30.1 && BMI <= 35) {
            BMIInterpretation = "Obese";
        }

        BMIInterpretationField.setText(BMIInterpretation);
    }

    private void populateMedicalHistoryTable() {
        medicalHistoryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("historyDescription"));
        medicalHistoryActionColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
        medicalHistoryTable.setItems(medicalHistory);

        medicalHistoryField.setText("");
    }

    private void populateVaccineHistoryTable() {


        vaccinationHistoryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("historyDescription"));
        vaccinationHistoryActionColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
        vaccinationHistoryTable.setItems(vaccinationHistory);

        vaccinationHistoryField.setText("");
    }

    private void addPatient() {

        String firstname = firstnameField.getText();
        String lastName = lastnameField.getText();
        String middleName = middleNameField.getText();
        String sex = sexChoiceBox.getValue();
        LocalDate birthdate = birthdatePicker.getValue();
        double weight = Double.parseDouble(weightField.getText());
        double height = Double.parseDouble(heightField.getText());
        double BMI = Double.parseDouble(BMIField.getText());
        int age = Integer.parseInt(ageField.getText());

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateSexChoices() {
        ObservableList<String> sexChoices = FXCollections.observableArrayList();
        sexChoices.add("Male");
        sexChoices.add("Female");

        sexChoiceBox.setItems(sexChoices);
        sexChoiceBox.getSelectionModel().select(0);
    }

    //Fix accuracy
    private void computeAge() {
        LocalDate dateNow = LocalDate.now();
        LocalDate birthdate = birthdatePicker.getValue();
        int age = 0;

        //System.out.println(birthdatePicker.getValue());

        try {
            while (birthdate.isBefore(dateNow)) {
                birthdate = birthdate.plusYears(1);

                if (birthdate.isBefore(dateNow) || birthdate.isEqual(dateNow)) {
                    age++;
                }
            }
            ageField.setText(age + "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void addPatientInfo() {

        boolean insert = true;
        Patient_Info patient = new Patient_Info();
        try {
            patient.setFirstname(firstnameField.getText());
            patient.setLastname(lastnameField.getText());
            patient.setMiddleName(middleNameField.getText());
            patient.setSex(sexChoiceBox.getValue());
            patient.setBirthdate(birthdatePicker.getValue());
            if (ageField.getText().isBlank()) {
                patient.setAge(Integer.parseInt(ageField.getPromptText()));
            } else {
                patient.setAge(Integer.parseInt(ageField.getText()));
            }
            if (heightField.getText().isBlank()) {
                patient.setHeight(Double.parseDouble(heightField.getPromptText()));
            } else {
                patient.setHeight(Double.parseDouble(heightField.getText()));
            }
            if (weightField.getText().isBlank()) {
                patient.setWeight(Double.parseDouble(weightField.getPromptText()));
            } else {
                patient.setWeight(Double.parseDouble(weightField.getText()));
            }
            if (BMIField.getText().isBlank()) {
                patient.setBMI(Double.parseDouble(BMIField.getPromptText()));
            } else {
                patient.setBMI(Double.parseDouble(BMIField.getText()));
            }
            patient.setCurrentCondition(currentConditionField.getText());
        } catch (Exception e) {
            Notifications error = new Notifications("Error!", "Invalid input or empty field.");
            error.showError();
            insert = false;
        }

        if (insert) {
            try {
                int affectedRows = 0;

                singleConnect = new ConnectionClass();

                affectedRows = singleConnect.insert(String.format("INSERT INTO `gnhs_system_db`.`patients` (`first_name`, `last_name`, `middle_name`, `sex`, `birthdate`, `age`, `weight`, `height`, `bmi`, `current_condition`) " +
                                "VALUES ('%s', '%s', '%s', '%s', '" + patient.getBirthdate() + "', '%d', '%f', '%f', '%f', '%s')"
                        , patient.getFirstname(), patient.getLastname(), patient.getMiddleName(), patient.getSex(), patient.getAge(), patient.getWeight(), patient.getHeight(), patient.getBMI(), patient.getCurrentCondition()));
                last_inserted_id = singleConnect.select("SELECT last_insert_id() as last_inserted_id");

                while (last_inserted_id.next()) {
                    lastInsertID = last_inserted_id.getInt("last_inserted_id");
                }

                if (affectedRows > 0) {
                    Notifications success = new Notifications("Success!", "The patient was added successfully.");
                    success.showInformation();
                }

                singleConnect.close();
            } catch (Exception e) {
                Notifications error = new Notifications("Error!", e.getMessage());
                error.showError();
            }
        }

        System.out.println(lastInsertID);

        addMedicationHistory(lastInsertID);
        addVaccinationHistory(lastInsertID);

    }

    private void addMedicationHistory(int id) {

        if (!(medicalHistory.isEmpty())) {
            for (History medHis : medicalHistory) {
                try {
                    connect = new ConnectionClass();
                    connect.insert(String.format("INSERT INTO `gnhs_system_db`.`medical_history` (`history_description`, `patient_id`) VALUES ('%s', '%d')", medHis.getHistoryDescription(), id));

                    connect.close();
                } catch (Exception e) {
                    Notifications error = new Notifications("Error!", e.getMessage());
                    error.showError();
                }
            }
        }
    }

    private void addVaccinationHistory(int id) {

        if (!(vaccinationHistory.isEmpty())) {
            for (History vacHis : vaccinationHistory) {

                try {
                    connect = new ConnectionClass();
                    connect.insert(String.format("INSERT INTO `gnhs_system_db`.`vaccination_or_immunization_history` (`vaccination_description`, `patient_id`) VALUES ('%s', '%d')", vacHis.getHistoryDescription(), id));

                    connect.close();
                } catch (Exception e) {
                    Notifications error = new Notifications("Error!", e.getMessage());
                    error.showError();
                }

            }
        }
    }

    private void clear() {
        populateSexChoices();

        fields.add(firstnameField);
        fields.add(lastnameField);
        fields.add(middleNameField);
        fields.add(ageField);
        fields.add(weightField);
        fields.add(heightField);
        fields.add(BMIField);
        fields.add(currentConditionField);
        fields.add(medicalHistoryField);
        fields.add(vaccinationHistoryField);

        for (TextField field : fields) {
            field.clear();
        }

        try {
            birthdatePicker.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        medicalHistory.clear();
        vaccinationHistory.clear();
        populateMedicalHistoryTable();
        populateVaccineHistoryTable();
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void fetchInformation() {

        if (isUpdating()) {
            ObservableList<History> vaccine = FXCollections.observableArrayList();
            ObservableList<History> medical = FXCollections.observableArrayList();
            Patient_Info patient = new Patient_Info();
            String BMIInterpretation = "";

            try {
                connect = new ConnectionClass();
                rs = connect.select(String.format("SELECT * FROM gnhs_system_db.patients WHERE patient_id='%d'", getPatientID()));
                vacHistory = connect.select(String.format("SELECT * FROM gnhs_system_db.vaccination_or_immunization_history WHERE patient_id = '%d'", getPatientID()));
                medHistory = connect.select(String.format("SELECT * FROM gnhs_system_db.medical_history WHERE patient_id = '%d'", getPatientID()));

                while (rs.next()) {
                    patient.setPatientID(rs.getInt("patient_id"));
                    patient.setFirstname(rs.getString("first_name"));
                    patient.setLastname(rs.getString("last_name"));
                    patient.setMiddleName(rs.getString("middle_name"));
                    patient.setSex(rs.getString("sex"));
                    patient.setBirthdate(rs.getDate("birthdate").toLocalDate());
                    patient.setAge(rs.getInt("age"));
                    patient.setHeight(rs.getDouble("height"));
                    patient.setWeight(rs.getDouble("weight"));
                    patient.setBMI(rs.getDouble("BMI"));
                    patient.setCurrentCondition(rs.getString("current_condition"));
                }

                while (vacHistory.next()) {
                    int hisID = vacHistory.getInt("id");

                    Button removeButton = new Button("Remove");
                    HBox buttonContainer = new HBox();
                    removeButton.setPrefWidth(80);
                    buttonContainer.setAlignment(Pos.CENTER);
                    buttonContainer.setSpacing(10.0);
                    buttonContainer.setId(hisID + "");
                    buttonContainer.setPadding(new Insets(10, 10, 10, 10));
                    removeButton.getStyleClass().add("clearButtons");
                    buttonContainer.getChildren().addAll(removeButton);
                    removeButton.setOnAction(event -> {
                        int id = Integer.parseInt(removeButton.getParent().getId());

                        try {
                            connect = new ConnectionClass();
                            connect.delete(String.format("DELETE FROM gnhs_system_db.vaccination_or_immunization_history WHERE (id = '%d') and (patient_id = '%d')", id, getPatientID()));
                            connect.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        for (Iterator<History> vacHistory = vaccinationHistory.iterator(); vacHistory.hasNext(); ) {
                            History sample = vacHistory.next();

                            if (sample.getHistoryID() == id) {
                                vacHistory.remove();

                                if (vaccinationHistory != null) {
                                    populateVaccineHistoryTable();
                                }
                            }
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                vaccinationHistoryField.requestFocus();
                            }
                        });

                    });

                    History vacHis = new History();
                    vacHis.setHistoryID(vacHistory.getInt("id"));
                    vacHis.setHistoryDescription(vacHistory.getString("vaccination_description"));
                    vacHis.setActions(buttonContainer);

                    vaccinationHistory.add(vacHis);
                }

                while (medHistory.next()) {
                    int medID = medHistory.getInt("medical_history_id");

                    Button removeButton = new Button("Remove");
                    HBox buttonContainer = new HBox();
                    removeButton.setPrefWidth(80);
                    buttonContainer.setAlignment(Pos.CENTER);
                    buttonContainer.setSpacing(10.0);
                    buttonContainer.setId(medID + "");
                    buttonContainer.setPadding(new Insets(10, 10, 10, 10));

                    removeButton.getStyleClass().add("clearButtons");
                    buttonContainer.getChildren().addAll(removeButton);


                    removeButton.setOnAction(event -> {

                        int id = Integer.parseInt(removeButton.getParent().getId());

                        try {
                            connect = new ConnectionClass();
                            connect.delete(String.format("DELETE FROM gnhs_system_db.medical_history WHERE (medical_history_id = '%d') and (patient_id = '%d')", id, getPatientID()));
                            connect.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        for (Iterator<History> his = medicalHistory.iterator(); his.hasNext(); ) {

                            History sample = his.next();

                            if (sample.getHistoryID() == id) {
                                his.remove();
                                if (medicalHistory != null) {
                                    populateMedicalHistoryTable();
                                }
                            }
                        }

                        //populateMedicalHistoryTable();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                medicalHistoryField.requestFocus();

                            }
                        });


                    });

                    History medHis = new History();
                    medHis.setHistoryID(medHistory.getInt("medical_history_id"));
                    medHis.setHistoryDescription(medHistory.getString("history_description"));
                    medHis.setActions(buttonContainer);
                    medicalHistory.add(medHis);
                }

                connect.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            firstnameField.setText(patient.getFirstname());
            lastnameField.setText(patient.getLastname());
            middleNameField.setText(patient.getMiddleName());
            sexChoiceBox.setValue(patient.getSex());
            birthdatePicker.setValue(patient.getBirthdate());
            ageField.setText(patient.getAge() + "");
            heightField.setText(patient.getHeight() + "");
            weightField.setText(patient.getWeight() + "");
            BMIField.setText(patient.getBMI() + "");
            currentConditionField.setText(patient.getCurrentCondition());

            /*0 to 16.0 = Severely underweight
            16.1 to 18.5 = Underweight
            18.6 to 25 = Normal
            25.1 to 30 = Overweight
            30.1 to 35 = Obese*/

            double BMI = patient.getBMI();

            if (BMI >= 0 && BMI <= 16.0) {
                BMIInterpretation = "Severely underweight";
            } else if (BMI >= 16.1 && BMI <= 18.5) {
                BMIInterpretation = "Underweight";
            } else if (BMI >= 18.6 && BMI <= 25) {
                BMIInterpretation = "Normal";
            } else if (BMI >= 25.1 && BMI <= 30) {
                BMIInterpretation = "Overweight";
            } else if (BMI >= 30.1 && BMI <= 35) {
                BMIInterpretation = "Obese";
            }

            BMIInterpretationField.setText(BMIInterpretation);

            populateVaccineHistoryTable();
            populateMedicalHistoryTable();
        }
    }

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
        saveButton.setText("Save Changes");
        clearButton.setDisable(true);
    }

    private ResultSet vacHistory, medHistory;


    public void setDashboardController(Dashboard_Controller controller) {
        dashboardController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateSexChoices();
    }
}
