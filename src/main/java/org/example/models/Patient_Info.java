package org.example.models;

import javafx.collections.ObservableList;

public class Patient_Info extends Patient{

    private double weight;
    private double height;
    private double BMI;
    private String currentCondition;
    private ObservableList medicalHistory;
    private ObservableList vaccinationHistory;

    public Patient_Info(){
        this.weight=0;
        this.height=0;
        this.BMI=0;
        this.currentCondition="";
        this.medicalHistory=null;
        this.vaccinationHistory=null;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public void setMedicalHistory(ObservableList medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setVaccinationHistory(ObservableList vaccinationHistory) {
        this.vaccinationHistory = vaccinationHistory;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getBMI() {
        return BMI;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public ObservableList getMedicalHistory() {
        return medicalHistory;
    }

    public ObservableList getVaccinationHistory() {
        return vaccinationHistory;
    }
}
