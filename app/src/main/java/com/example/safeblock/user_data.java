package com.example.safeblock;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import org.web3j.abi.datatypes.DynamicStruct;


public class user_data {
    public String _walletAddress;
    public String name;
    public String email;
    public Boolean infected;
    public String picture;


    public user_data(
            String name,
            String email,
            String walletAddressresults,
            Boolean infected,
            String picture

    )
    {
        this._walletAddress = walletAddressresults;
        this.name = name;
        this.email = email;
        this.infected = infected;
        this.picture = picture;

    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "["
                +", _walletAddress=" + _walletAddress
                +", name=" + name
                +", email=" + email
                +", infected=" + infected
                +", picture =" + picture + " ]";
    }
}