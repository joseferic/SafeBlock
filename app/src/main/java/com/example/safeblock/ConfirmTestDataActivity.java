package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

public class ConfirmTestDataActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmTestDataActivity";
    public final String contractAddress = "0x938A5FA7b18699D656FF37f89358DB9ce8dD549C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_test_data);
        TextView tv_tesDataCovid = findViewById(R.id.tv_dataTestCovid);
        Button btn_bayarTestCovid = findViewById(R.id.button_bayar);

        //Get Data From Prev Activity
        user_data UserData = getIntent().getParcelableExtra("UserData");
        Dokter dokter = getIntent().getParcelableExtra("DokterData");
        String tipeTestCovid = getIntent().getExtras().getString("TestType");
        String tanggal = getIntent().getExtras().getString("Date");
        String waktu = getIntent().getExtras().getString("Time");

        String hargaRupiah = getIntent().getExtras().getString("PriceRupiah");
        String hargaEther = getIntent().getExtras().getString("PriceEther");
        String hargaWei = getIntent().getExtras().getString("PriceWei");

        String testId = createTestId(UserData.name, dokter.name, tipeTestCovid, tanggal, waktu);
        //TestCovid testCovid = new TestCovid()

        tv_tesDataCovid.setText(
                "Nama User = " + UserData.name + "\n\nNama Dokter = " + dokter.name +
                        "\n\nTipe Tes Covid-19 = " + tipeTestCovid + "\n\nTanggal = " + tanggal +
                        "\n\nWaktu = " + waktu
                        + "\n\nTest Id = " + testId
                        + "\n\nHarga dalam Rupiah = " + hargaRupiah
                        + "\n\nHarga dalam Ether = " + hargaEther
        );

        btn_bayarTestCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payTestCovid(testId,UserData.name, tipeTestCovid, tanggal, waktu, UserData.privateKey,dokter.name,dokter.publicAddress,hargaWei,hargaEther,hargaRupiah);
            }
        });


    }

    public void payTestCovid(String testId, String userName, String tipeTesCovid, String tanggalTes, String waktuTes, String privateKey, String DoctorName, String DoctorAddress, String PriceInWei,String PriceInEther,String PriceRupiah) {
        //    TextView tvTestDataIntent = findViewById(R.id.tv_data);
        Log.d(TAG, "payTestCovid()");
        String transactionHash = "transactionHash";
        String statusTransaksi = "status";

        String[] ringkasanTransaksi = null;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        BigInteger price = new BigInteger(PriceInWei);

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaru contract = SmartContractBaru_sol_SmartContractBaru.load(contractAddress, web3j, credentials, contractGasProvider);
        try {

            TransactionReceipt transaksi = contract.transaferEtherAddress(DoctorAddress, DoctorName, price).sendAsync().get();
            if (transaksi.getTransactionHash() != null) {
                //       tvTestDataIntent.setText(transaksi.getTransactionHash());
                transactionHash =  transaksi.getTransactionHash();
                statusTransaksi = transaksi.getStatus();
                Intent intent = new Intent(ConfirmTestDataActivity.this, DetailTestCovidTerbayarActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("userPrivateKey",privateKey);
                intent.putExtra("testId",testId);
                intent.putExtra("tipeTesCovid",tipeTesCovid);
                intent.putExtra("tanggalTes",tanggalTes);
                intent.putExtra("waktuTes",waktuTes);
                intent.putExtra("DoctorName",DoctorName);
                intent.putExtra("DoctorAddress",DoctorAddress);
                intent.putExtra("transactionHash",transactionHash);
                intent.putExtra("statusTransaksi",statusTransaksi);
                intent.putExtra("priceEther",PriceInEther);
                intent.putExtra("priceWei",PriceInWei);
                intent.putExtra("priceRupiah",PriceRupiah);
                startActivity(intent);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }



    private String createTestId(String userName, String doctorName, String testType, String date, String time) {
        //String testId = "testId";
        String craftIdTest = "testId";

        if (testType.equals("Tes Cepat Molekuler")) {
            String testId = "TCM-";
            craftIdTest = testId;
        } else if (testType.equals("Polymerase Chain Reaction")) {
            String testId = "PCR-";
            craftIdTest = testId;
        } else if (testType.equals("Rapid Test Antigen")) {
            String testId = "RapidAntiGen-";
            craftIdTest = testId;
        } else if (testType.equals("Rapid Test Antibodi")) {
            String testId = "RapidAntiBodi-";
            craftIdTest = testId;
        } else {
            return null;
        }

        //hilangkan semua symbol
        doctorName = doctorName.replaceAll("[^a-zA-Z0-9\\s+]", "");
        userName = userName.replaceAll("[^a-zA-Z0-9\\s+]", "");

        doctorName = doctorName.replaceAll(" ", "");
        userName = userName.replaceAll(" ", "");

        date = date.replaceAll("[^a-zA-Z0-9\\s+]", "");
        time = time.replaceAll("[^a-zA-Z0-9\\s+]", "");

        craftIdTest = craftIdTest + userName +"-"+ doctorName +"-" + date +"-"+  time;

        Log.d("testId = ", craftIdTest);
        return craftIdTest;
    }
}