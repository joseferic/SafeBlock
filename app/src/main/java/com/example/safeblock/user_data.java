package com.example.safeblock;

import android.os.Parcel;
import android.os.Parcelable;


public class user_data implements Parcelable {
    public String privateKey;
    public String name;
    public String email;
    public Boolean infected;



    public user_data(
            String name,
            String email,
            String privateKey,
            Boolean infected
    )
    {
        this.privateKey = privateKey;
        this.name = name;
        this.email = email;
        this.infected = infected;
    }


    protected user_data(Parcel in) {
        privateKey = in.readString();
        name = in.readString();
        email = in.readString();
        byte tmpInfected = in.readByte();
        infected = tmpInfected == 0 ? null : tmpInfected == 1;
    }

    public static final Creator<user_data> CREATOR = new Creator<user_data>() {
        @Override
        public user_data createFromParcel(Parcel in) {
            return new user_data(in);
        }

        @Override
        public user_data[] newArray(int size) {
            return new user_data[size];
        }
    };

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "["
                +", privateKey=" + privateKey
                +", name=" + name
                +", email=" + email
                +", infected=" + infected
                +" ]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(privateKey);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeByte((byte) (infected == null ? 0 : infected ? 1 : 2));
    }
}