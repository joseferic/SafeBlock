package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

public class DetailTestCovidTerbayarActivity extends AppCompatActivity {
    private static final String TAG = "DetailTestCovidTerbayarActivity";
    public final String contractAddress = "0x938A5FA7b18699D656FF37f89358DB9ce8dD549C";
    public TextView tv_tesDataCovid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_test_covid_terbayar);
        tv_tesDataCovid = findViewById(R.id.tv_dataTestCovid);
        //TextView tv_tesDataCovid = findViewById(R.id.tv_dataTestCovid);
        String userName = getIntent().getExtras().getString("userName");
        String privateKey = getIntent().getExtras().getString("userPrivateKey");
        String testId = getIntent().getExtras().getString("testId");
        String tipeTesCovid = getIntent().getExtras().getString("tipeTesCovid");
        String tanggalTes = getIntent().getExtras().getString("tanggalTes");
        String waktuTes = getIntent().getExtras().getString("waktuTes");
        String DoctorName = getIntent().getExtras().getString("DoctorName");
        String DoctorAddress = getIntent().getExtras().getString("DoctorAddress");
        String transactionHash = getIntent().getExtras().getString("transactionHash");
        String statusTransaksi = getIntent().getExtras().getString("statusTransaksi");
        String priceEther = getIntent().getExtras().getString("priceEther");
        //String priceWei = getIntent().getExtras().getString("priceWei");
        String priceRupiah = getIntent().getExtras().getString("priceRupiah");

        DetailTestCovid detailTestCovid = new DetailTestCovid(userName,testId,tipeTesCovid,tanggalTes,
                waktuTes,DoctorName,DoctorAddress,transactionHash,statusTransaksi,priceEther,priceRupiah);


        createTestCovidData(privateKey,detailTestCovid);


//        tv_tesDataCovid.setText(
//                "Nama User = " + userName +
//                        "\n\nNama Dokter = " + DoctorName +
//                        "\n\nAddress Dokter = " + DoctorAddress +
//                        "\n\nTipe Tes Covid-19 = " + tipeTesCovid
//                        + "\n\nTanggal = " + tanggalTes +
//                        "\n\nWaktu = " + waktuTes
//                        + "\n\nTest Id = " + testId
//                        + "\n\nHarga dalam Rupiah = " + priceRupiah
//                        + "\n\nHarga dalam Ether = " + priceEther
//                        + "\n\nStatus Transaksi = " + statusTransaksi
//                        + "\n\nTransaction Hash = " + transactionHash
//        );


    }

    public void createTestCovidData(String privateKey, DetailTestCovid detailTestCovid){


        Log.d(TAG, "createTestCovidData()");

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );


        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaru contract = SmartContractBaru_sol_SmartContractBaru.load(contractAddress, web3j, credentials, contractGasProvider);
        try {
            String detailTest = new Gson().toJson(detailTestCovid);
            //Log.d(TAG, detailTest);
            String encryptedString = AES.encrypt(detailTest,detailTestCovid.userName);
            Log.d(TAG, detailTestCovid.testId.trim());
            Log.d(TAG, detailTestCovid.transactionHash.trim());
            Log.d(TAG, detailTestCovid.DoctorName.trim());
            Log.d(TAG, encryptedString);
            String test = detailTestCovid.tanggalTes +""+ detailTestCovid.waktuTes;



            TransactionReceipt transaksi = contract.createTestCovid(detailTestCovid.testId.trim(),detailTestCovid.userName.trim(),encryptedString).sendAsync().join();
            if (transaksi.getTransactionHash() != null) {
                       //tvTestDataIntent.setText(transaksi.getTransactionHash());
                Log.d(TAG,transaksi.getStatus());
                tv_tesDataCovid.setText(
                        "Nama User = " + detailTestCovid.userName +
                                "\n\nNama Dokter = " + detailTestCovid.DoctorName +
                                "\n\nAddress Dokter = " + detailTestCovid.DoctorAddress +
                                "\n\nTipe Tes Covid-19 = " + detailTestCovid.tipeTesCovid
                                + "\n\nTanggal = " + detailTestCovid.tanggalTes +
                                "\n\nWaktu = " + detailTestCovid.waktuTes
                                + "\n\nTest Id = " + detailTestCovid.testId
                                + "\n\nHarga dalam Rupiah = " + detailTestCovid.priceRupiah
                                + "\n\nHarga dalam Ether = " + detailTestCovid.priceEther
                                + "\n\nStatus Transaksi = " + detailTestCovid.statusTransaksi
                                + "\n\nTransaction Hash = " + detailTestCovid.transactionHash
                );
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}