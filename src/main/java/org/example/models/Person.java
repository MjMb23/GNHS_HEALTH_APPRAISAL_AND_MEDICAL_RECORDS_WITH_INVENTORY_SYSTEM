package org.example.models;

import java.time.LocalDate;

public class Person {
    private String firstname;
    private String lastname;
    private String middleName;
    private String sex;
    private LocalDate birthdate;
    private int age;

    public Person(String firstname, String lastname, String middleName, String sex, LocalDate birthdate, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middleName = middleName;
        this.sex = sex;
        this.birthdate=birthdate;
        this.age=age;
    }

    public Person(){
        this.firstname="";
        this.lastname="";
        this.middleName="";
        this.sex="";
        this.birthdate=null;
        this.age=0;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSex() {
        return sex;
    }
}
