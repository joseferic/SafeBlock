package com.example.safeblock;

public class InputData {
    String UserName;
    String Email;
    String PrivateKey;
    Boolean Infected;
    String PlaceName;
    Double Latitude;
    Double Longitude;
    String Date;

    public InputData(String UserName, String Email, String PrivateKey, Boolean Infected,
                     String PlaceName, Double Latitude, Double Longitude, String Date){
        this.UserName = UserName;
        this.Email = Email;
        this.PrivateKey = PrivateKey;
        this.Infected = Infected;
        this.PlaceName = PlaceName;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Date = Date;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "["
                +", UserName=" + UserName
                +", Email=" + Email
                +", PrivateKey=" + PrivateKey
                +", Infected=" + Infected
                +", PlaceName=" + PlaceName
                +", Latitude=" + Latitude
                +", Longitude=" + Longitude
                +", Date=" + Date
                +" ]";
    }
}
