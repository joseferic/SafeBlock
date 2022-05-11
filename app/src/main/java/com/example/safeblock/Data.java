package com.example.safeblock;

import java.math.BigInteger;

public class Data {
    public BigInteger getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(BigInteger transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String get_transactionHash() {
        return _transactionHash;
    }

    public void set_transactionHash(String _transactionHash) {
        this._transactionHash = _transactionHash;
    }

    public String get_walletAddress() {
        return _walletAddress;
    }

    public void set_walletAddress(String _walletAddress) {
        this._walletAddress = _walletAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace_visited() {
        return place_visited;
    }

    public void setPlace_visited(String place_visited) {
        this.place_visited = place_visited;
    }

    public String getTime_visited() {
        return time_visited;
    }

    public void setTime_visited(String time_visited) {
        this.time_visited = time_visited;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getInfected() {
        return infected;
    }

    public void setInfected(Boolean infected) {
        this.infected = infected;
    }

    public BigInteger transaction_id;
    public String _transactionHash;
    public String _walletAddress;
    public String name;
    public String email;
    public String place_visited;
    public String time_visited;
    public String latitude;
    public String longitude;
    public Boolean infected;

    public Data(BigInteger transaction_id,
                String _transactionHash,
                String _walletAddress,
                String name,
                String email,
                String place_visited,
                String time_visited,
                String latitude,
                String longitude,
                Boolean infected){
        this.transaction_id = transaction_id;
        this._transactionHash = _transactionHash;
        this._walletAddress = _walletAddress;
        this.name = name;
        this.email = email;
        this.place_visited = place_visited;
        this.time_visited = time_visited;
        this.latitude = latitude;
        this.longitude = longitude;
        this.infected = infected;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[transaction_id = " + transaction_id
                +", _transactionHash = " + _transactionHash
                +", _walletAddress = " + _walletAddress
                +", name = " + name
                +", email = " + email
                +", place_visited= " + place_visited
                +", time_visited = " + time_visited
                +", latitude = " + latitude
                +", longitude = " + longitude
                +", infected = " + infected  + "]";
    }
}
