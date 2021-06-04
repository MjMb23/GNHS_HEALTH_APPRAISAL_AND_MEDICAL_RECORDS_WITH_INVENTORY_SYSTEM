package org.example.models;

import java.time.LocalDate;

public class Check_Up_Record {

    private int id;
    private int patientID;
    private String BP;
    private float TEMP;
    private int RR;
    private int PR;
    private int O2;
    private String chiefComplaint;
    private String treatment;
    private LocalDate dateTaken;

    public Check_Up_Record(){
        this.id=0;
        this.patientID=0;
        this.BP="";
        this.TEMP=0;
        this.RR=0;
        this.O2=0;
        this.chiefComplaint="";
        this.treatment="";
        this.dateTaken=null;
    }

    public LocalDate getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(LocalDate dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public void setTEMP(float TEMP) {
        this.TEMP = TEMP;
    }

    public void setRR(int RR) {
        this.RR = RR;
    }

    public void setPR(int PR) {
        this.PR = PR;
    }

    public void setO2(int o2) {
        O2 = o2;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getBP() {
        return BP;
    }

    public float getTEMP() {
        return TEMP;
    }

    public int getRR() {
        return RR;
    }

    public int getPR() {
        return PR;
    }

    public int getO2() {
        return O2;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public String getTreatment() {
        return treatment;
    }
}
