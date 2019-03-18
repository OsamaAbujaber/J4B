package com.example.jbus.model;

public class Driver
{


    private String id;
    private String pass;



    public Driver() {

    }
    public Driver(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
