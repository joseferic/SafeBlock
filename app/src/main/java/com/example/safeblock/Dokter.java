package com.example.safeblock;

import android.os.Parcel;
import android.os.Parcelable;

public class Dokter implements Parcelable {
    String name;
    String publicAddress;
    String password;
    String privateKey;

    public Dokter(String name, String publicAddress, String password, String privateKey){
        this.name = name;
        this.publicAddress = publicAddress;
        this.password = password;
        this.privateKey = privateKey;
    }

    protected Dokter(Parcel in) {
        name = in.readString();
        publicAddress = in.readString();
        password = in.readString();
        privateKey = in.readString();
    }

    public static final Creator<Dokter> CREATOR = new Creator<Dokter>() {
        @Override
        public Dokter createFromParcel(Parcel in) {
            return new Dokter(in);
        }

        @Override
        public Dokter[] newArray(int size) {
            return new Dokter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(publicAddress);
        parcel.writeString(password);
        parcel.writeString(privateKey);
    }
}
