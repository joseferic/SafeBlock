package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

public class InputDataTestCovidActivity extends AppCompatActivity {

    private static final String TAG = "InputDataTestCovidActivity";
    public final String contractAddress = "0xBb5a99B6B526671FE500e48FFf99aFCfFa88EBb3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_test_covid);
        TextView tvTestDataIntent = findViewById(R.id.tv_data);
        Button btnSendEther = findViewById(R.id.button_payDoctor);

        user_data UserData = getIntent().getParcelableExtra("UserData");
        Dokter dokter = getIntent().getParcelableExtra("DokterData");

        tvTestDataIntent.setText(UserData.name + "\n\n" + UserData.privateKey + "\n\n" +"\n\n"+dokter.name + "\n"+ dokter.publicAddress);

        btnSendEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payTestCovid(UserData.privateKey, dokter.name,dokter.publicAddress,"1000000000000000000");
            }
        });

    }

    public String payTestCovid(String privateKey, String DoctorName, String DoctorAddress, String PriceInWei){
        TextView tvTestDataIntent = findViewById(R.id.tv_data);
        Log.d(TAG, "payTestCovid()");
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        BigInteger price = new BigInteger(PriceInWei);

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaruTest contract = SmartContractBaru_sol_SmartContractBaruTest.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            //Log.v("Data Dokter length list", contract.getAddressDoctorArrayLength().sendAsync().get().toString());
            //Integer list_length = Integer.valueOf(contract.getAddressDoctorArrayLength().sendAsync().get().toString());
            TransactionReceipt transaksi = contract.transaferEtherAddress(DoctorAddress,DoctorName,price).sendAsync().get();
            if(transaksi != null){
                tvTestDataIntent.setText(transaksi.getTransactionHash());
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }
}