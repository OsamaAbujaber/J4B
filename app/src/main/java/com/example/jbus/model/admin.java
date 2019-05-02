package com.example.jbus.model;

public class admin {
    private String id;
    private String pass;

    public admin() {
    }

    public admin(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }
}
