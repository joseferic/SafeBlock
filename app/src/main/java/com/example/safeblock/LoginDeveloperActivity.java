package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.safeblock.databinding.ActivityLoginDeveloperBinding;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class LoginDeveloperActivity extends AppCompatActivity {

    private static final String TAG = "LoginDeveloperActivity ";
    private ActivityLoginDeveloperBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginDeveloperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCheckDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputNama.getText().toString().trim();
                String address = binding.inputPublicAddress.getText().toString().trim();
                Boolean status = checkDeveloper(name,address);
                if(status == true){
                    binding.imgViewCheck.setVisibility(View.VISIBLE);
                }
                else{
                    binding.imgViewCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
                    binding.imgViewCheck.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnDeveloperMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputNama.getText().toString().trim();
                String address = binding.inputPublicAddress.getText().toString().trim();
                String password = binding.inputPasswordDeveloper.getText().toString().trim();
                Boolean login = loginDeveloper(name,address,password);
                if(login == true){

                    Intent intent = new Intent(LoginDeveloperActivity.this, SignupDokterActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Input Data Develeoper Salah", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private Boolean checkDeveloper(String name, String address){
        Log.d(TAG, "checkDeveloper()");
        Boolean checkDeveloper = false;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        //0x342673B9B479e8FFfcd8dE709f89f8EBaE111a1b
        //0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2
        String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = "8b8b105e6f96b7a230239d87d40e0aa96fb45f41f6f1b901b78c827e28648721";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        NewSafeBlock_sol_NewSafeBlock contract = NewSafeBlock_sol_NewSafeBlock.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            checkDeveloper = contract.verifyDeveloper(address,name).sendAsync().get();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return checkDeveloper;
    }

    private Boolean loginDeveloper(String name, String address, String password){
        Log.d(TAG, "loginDeveloper()");
        Boolean loginDeveloper = false;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        //0x342673B9B479e8FFfcd8dE709f89f8EBaE111a1b
        //0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2
        String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = "8b8b105e6f96b7a230239d87d40e0aa96fb45f41f6f1b901b78c827e28648721";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        NewSafeBlock_sol_NewSafeBlock contract = NewSafeBlock_sol_NewSafeBlock.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            loginDeveloper = contract.verifyLoginDeveloper(address,name,password).sendAsync().get();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return loginDeveloper;
    }

    private void sendDatatoBlockChainOther(String userName, String placeName, String dateFormatted, String Latitude, String Longitude,String email_data,String private_key_wallet_data_key,String status_data) {
        Log.d(TAG, "sendDatatoBlockChain:");



        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x342673B9B479e8FFfcd8dE709f89f8EBaE111a1b";
        String privateKey = private_key_wallet_data_key;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SafeBlock_sol_SafeBlock contract = SafeBlock_sol_SafeBlock
                .load(contractAddress, web3j, credentials, contractGasProvider);

        String userData = new Gson().toJson(new InputData(userName,email_data,privateKey,Boolean.parseBoolean(status_data), placeName,Double.parseDouble(Latitude),Double.parseDouble(Longitude),dateFormatted));
        String key = AES.encrypt(userName,"SafeBlockIsSafe");

        String encryptedString = AES.encrypt(userData,userName);

        TransactionReceipt transactionReceipt = contract.createUserData(encryptedString,key).sendAsync().join();
        Log.d(TAG, "Transaction Receipt = " + transactionReceipt);
        if ((transactionReceipt.getTransactionHash()) != null) {
            //binding.tvReceipt.setText(transactionReceipt.getTransactionHash());

            Log.d(TAG, "Transaction Hash = " + transactionReceipt.getTransactionHash());

//            Intent intent = new Intent(LoginDeveloperActivity.this, AddTransactionHash.class);
//            intent.putExtra("USER_NAME_KEY", userName);
//            intent.putExtra("PLACE_NAME_KEY", placeName);
//            intent.putExtra("TIME_KEY", dateFormatted);
//            intent.putExtra("PLACE_LATITUDE_KEY", Latitude);
//            intent.putExtra("PLACE_LONGITUDE_KEY", Longitude);
//            intent.putExtra("TRANSACTION_HASH_KEY", transactionReceipt.getTransactionHash());
//            intent.putExtra("ENCRYPTED_DATA_KEY",encryptedString);
//            intent.putExtra("PRIVATEKEY_DATA_KEY",privateKey);

//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            overridePendingTransition (0, 0);
        } else {

        }

    }
}