package com.example.safeblock;

import org.web3j.abi.datatypes.Type;

public class TestCovid implements Type {
    @Override
    public int bytes32PaddedLength() {
        return 0;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getTypeAsString() {
        return null;
    }

    public String testId;
    public String transactionHash;
    public String patientName;
    public String doctorName;
    public String detailTest;
    public String stateTest;
    public Boolean resultTest;
    public Boolean statusDone;

    
    public TestCovid(String testId,
            String transactionHash,
            String patientName,
           String doctorName,
             String detailTest,
            String stateTest,
            Boolean resultTest,
            Boolean statusDone
    ){
        this.testId = testId;
        this.transactionHash = transactionHash;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.detailTest = detailTest;
        this.stateTest = stateTest ;
        this.resultTest = resultTest;
        this.statusDone = statusDone;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                +" [ testId = " + testId
                +", transactionHash = " + transactionHash
                +", patientName = " + patientName
                +", doctorName = " + doctorName
                +", detailTest  = " + detailTest
                +", stateTest = " + stateTest
                +", resultTest = " + resultTest
                +", statusDone = " + statusDone
                + " ]";
    }
}
