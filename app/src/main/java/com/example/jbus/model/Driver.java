package com.example.jbus.model;

public class Driver
{


    private String id;
    private String pass;
    private double lag;
    private double lan;



    public Driver() {

    }

    public Driver(String id, String pass, double lag, double lan) {
        this.id = id;
        this.pass = pass;
        this.lag = lag;
        this.lan = lan;
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

    public void setLag(double lag) {
        this.lag = lag;
    }

    public double getLag() {
        return lag;
    }

    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }
}
