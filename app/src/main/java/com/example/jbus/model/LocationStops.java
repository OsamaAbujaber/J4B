package com.example.jbus.model;

public class LocationStops {
    private String Lag;
    private String Lan;

    public LocationStops() {
    }


    public LocationStops(String Lan, String Lag) {
         this.Lag = Lag;
        this.Lan = Lan;
    }

    public String getLag() {
        return Lag;
    }

    public String getLan() {
        return Lan;
    }
}
