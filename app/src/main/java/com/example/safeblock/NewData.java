package com.example.safeblock;

import java.math.BigInteger;

public class NewData {

    public String transactionHash;
    public String data;

    public NewData(String _transactionHash,
                   String data){

        this.transactionHash = _transactionHash;
        this.data = data;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[ "
                +", transactionHash = " + transactionHash
                +", data = " + data + " ]";
    }
}
