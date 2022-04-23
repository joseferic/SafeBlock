package com.example.safeblock;

import org.web3j.abi.datatypes.DynamicStruct;


public class user_data {
    public String _walletAddress;
    public String name;
    public String email;
    public Boolean infected;

    public user_data(
            String name,
            String email,
            String walletAddressresults,
            Boolean infected
    )
    {
        this._walletAddress = walletAddressresults;
        this.name = name;
        this.email = email;
        this.infected = infected;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "["
                +", _walletAddress=" + _walletAddress
                +", name=" + name
                +", email=" + email
                +", infected=" + infected  + "]";
    }
}