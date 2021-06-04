package org.example.models;

import java.time.LocalDate;

public class Patient extends Person{
    private int patientID;

    public Patient(int patientID, String firstname, String lastname, String middleName, String sex, LocalDate birthdate, int age){
        super(firstname, lastname, middleName, sex, birthdate, age);
        this.patientID=patientID;
    }

    public Patient(){
        this.patientID=0;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
