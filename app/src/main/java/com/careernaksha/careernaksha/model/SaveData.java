package com.careernaksha.careernaksha.model;

public class SaveData {
    String name,number,email,pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public SaveData()
    {}


    public SaveData(String name, String number, String email, String pass) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.pass = pass;
    }




}
