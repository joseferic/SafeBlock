package com.example.safeblock;

public class PlaceData {

    String PlaceName;
    Double Latitude;
    Double Longitude;


    public PlaceData(String PlaceName, Double Latitude, Double Longitude){
        this.PlaceName = PlaceName;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "["
                +", PlaceName=" + PlaceName
                +", Latitude=" + Latitude
                +", Longitude=" + Longitude
                +" ]";
    }
}
