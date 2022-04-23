package com.example.safeblock;





import org.web3j.abi.datatypes.DynamicStruct;

import java.math.BigInteger;

public class user extends DynamicStruct {
    public BigInteger transaction_id;
    public String _walletAddress;
    public String name;
    public String email;
    public String place_visited;
    public String time_visited;
    public Boolean infected;

    public user(BigInteger transaction_id,
                String walletAddressresults,
                String name,
                String email,
                String place_visited,
                String time_visited,
                Boolean infected){
        this.transaction_id = transaction_id;
        this._walletAddress = walletAddressresults;
        this.name = name;
        this.email = email;
        this.place_visited = place_visited;
        this.time_visited = time_visited;
        this.infected = infected;
    }
}


 

