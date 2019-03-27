package com.example.jbus.model;

public class Driver
{


    private String id;
    private String pass;
    private String lag;
    private String lan;



    public Driver() {

    }

    public Driver(String id, String pass, String lag, String lan) {
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

    public void setLag(String lag) {
        this.lag = lag;
    }

    public String getLag() {
        return lag;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
