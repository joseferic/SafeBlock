package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.safeblock.databinding.ActivityAddTransactionHashBinding;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddTransactionHash extends AppCompatActivity {

    private static final String TAG = "AddTransactionHashActivity";
    ActivityAddTransactionHashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransactionHashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvTitleConfirm.setVisibility(View.INVISIBLE);
        binding.tvConfirmName.setVisibility(View.INVISIBLE);
        binding.tvConfirmPlace.setVisibility(View.INVISIBLE);
        binding.tvConfirmTime.setVisibility(View.INVISIBLE);
        binding.tvConfirmLatlng.setVisibility(View.INVISIBLE);
        binding.buttonBack.setVisibility(View.INVISIBLE);
        binding.tvTransactionReceipt.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        String transaction_hash_key = i.getStringExtra("TRANSACTION_HASH_KEY");
        String userName = i.getStringExtra("USER_NAME_KEY");
        String placeName = i.getStringExtra("PLACE_NAME_KEY");
        String dateFormatted = i.getStringExtra("TIME_KEY");
        String Latitude = i.getStringExtra("PLACE_LATITUDE_KEY");
        String Longitude = i.getStringExtra("PLACE_LONGITUDE_KEY");
        String EncryptedData = i.getStringExtra("ENCRYPTED_DATA_KEY");
        String PrivateKey = i.getStringExtra("PRIVATEKEY_DATA_KEY");

try {
    sendDataHashOther(userName, placeName, dateFormatted, Latitude, Longitude, transaction_hash_key,EncryptedData,PrivateKey);
}catch (Throwable throwable){
    throwable.printStackTrace();
}

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTransactionHash.this, MainActivity.class));
            }
        });

    }

    private void sendDataHashOther(String userName, String placeName, String dateFormatted, String Latitude, String Longitude, String transaction_hash_key,String EncryptedData,String PrivateKey) {
        Log.d(TAG, "sendDataHash:");

        binding.progressBar.setVisibility(View.VISIBLE);

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x342673B9B479e8FFfcd8dE709f89f8EBaE111a1b";
        String privateKey = PrivateKey;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SafeBlock_sol_SafeBlock contract = SafeBlock_sol_SafeBlock
                .load(contractAddress, web3j, credentials, contractGasProvider);


        TransactionReceipt transactionReceipt = contract.updateTransactionHash(transaction_hash_key,EncryptedData).sendAsync().join();
        if ((transactionReceipt.getTransactionHash()) != null) {


            Log.d(TAG, "sendDataHash:" + " Transaction Hash =" + transaction_hash_key);

            binding.tvTitleConfirm.setVisibility(View.VISIBLE);
            binding.tvConfirmName.setVisibility(View.VISIBLE);
            binding.tvConfirmPlace.setVisibility(View.VISIBLE);
            binding.tvConfirmTime.setVisibility(View.VISIBLE);
            binding.tvConfirmLatlng.setVisibility(View.VISIBLE);
            binding.buttonBack.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);

            // binding.tvTitleConfirm.setVisibility(userName, String placeName,String dateFormatted, String Latitude, String Longitude, String transaction_hash_key);

            //String placeName,String dateFormatted, String Latitude, String Longitude, String transaction_hash_key
            binding.tvConfirmName.setText("Name = \n" + userName);
            binding.tvConfirmPlace.setText("Place Name = \n" + placeName);
            binding.tvConfirmTime.setText("Time = \n" + dateFormatted);
            binding.tvConfirmLatlng.setText("Latitude/Longitude  = \n" + Latitude + " / " + Longitude);
            binding.tvTransactionReceipt.setText("Transaction Hash  = \n" + transaction_hash_key);
            Log.d(TAG, "sendDataHash: Success");
            //TransactionReceipt transactionReceipt1 = user_dataContract.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
            //user_dataContract_addTranscationHash.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();

        } else {
            binding.tvTransactionReceipt.setText("NULL");
            binding.progressBar.setVisibility(View.VISIBLE);
        }


    }
}

//    private void sendDataHash(String userName, String placeName, String dateFormatted, String Latitude, String Longitude, String transaction_hash_key) {
//        Log.d(TAG, "sendDataHash:");
//
//        binding.progressBar.setVisibility(View.VISIBLE);
//
//        final Web3j web3j = Web3j.build(
//                new HttpService(
//                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
//                )
//        );
//
//        String contractAddress = "0xc5F8e80Dd0E58B182A5820B7063b413248039b58";
//        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";
//
//        Credentials credentials = Credentials.create(privateKey);
//        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        UserData_sol_UserData user_dataContract = UserData_sol_UserData
//                .load(contractAddress, web3j, credentials, contractGasProvider);
//
//
//        user_dataContract.update_transaction_hash(userName, placeName, dateFormatted, Latitude, Longitude, transaction_hash_key).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
//            @Override
//            public void accept(TransactionReceipt transactionReceipt) throws Exception {
//                if ((transactionReceipt.getTransactionHash()) != null) {
//
//
//                    Log.d(TAG, "sendDataHash:" + " Transaction Hash =" + transaction_hash_key);
//
//                    binding.tvTitleConfirm.setVisibility(View.VISIBLE);
//                    binding.tvConfirmName.setVisibility(View.VISIBLE);
//                    binding.tvConfirmPlace.setVisibility(View.VISIBLE);
//                    binding.tvConfirmTime.setVisibility(View.VISIBLE);
//                    binding.tvConfirmLatlng.setVisibility(View.VISIBLE);
//                    binding.buttonBack.setVisibility(View.VISIBLE);
//                    binding.progressBar.setVisibility(View.INVISIBLE);
//
//                    // binding.tvTitleConfirm.setVisibility(userName, String placeName,String dateFormatted, String Latitude, String Longitude, String transaction_hash_key);
//
//                    //String placeName,String dateFormatted, String Latitude, String Longitude, String transaction_hash_key
//                    binding.tvConfirmName.setText("Name = \n" + userName);
//                    binding.tvConfirmPlace.setText("Place Name = \n" + placeName);
//                    binding.tvConfirmTime.setText("Time = \n" + dateFormatted);
//                    binding.tvConfirmLatlng.setText("Latitude/Longitude  = \n" + Latitude + " / " + Longitude);
//                    binding.tvTransactionReceipt.setText("Transaction Hash  = \n" + transaction_hash_key);
//                    Log.d(TAG, "sendDataHash: Success");
//                    //TransactionReceipt transactionReceipt1 = user_dataContract.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//                    //user_dataContract_addTranscationHash.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//                    startActivity(new Intent(AddTransactionHash.this, MainActivity.class));
//
//                } else {
//                    binding.tvTransactionReceipt.setText("NULL");
//                    binding.progressBar.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//
//    private void sendDataHashOtherOld(String userName, String placeName, String dateFormatted, String Latitude, String Longitude, String transaction_hash_key) {
//        Log.d(TAG, "sendDataHash:");
//
//        binding.progressBar.setVisibility(View.VISIBLE);
//
//        final Web3j web3j = Web3j.build(
//                new HttpService(
//                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
//                )
//        );
//
//        String contractAddress = "0xc5F8e80Dd0E58B182A5820B7063b413248039b58";
//        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";
//
//        Credentials credentials = Credentials.create(privateKey);
//        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        UserData_sol_UserData user_dataContract = UserData_sol_UserData
//                .load(contractAddress, web3j, credentials, contractGasProvider);
//
//
//        TransactionReceipt transactionReceipt = user_dataContract.update_transaction_hash(userName, placeName, dateFormatted, Latitude, Longitude, transaction_hash_key).sendAsync().join();
//        if ((transactionReceipt.getTransactionHash()) != null) {
//
//
//            Log.d(TAG, "sendDataHash:" + " Transaction Hash =" + transaction_hash_key);
//
//            binding.tvTitleConfirm.setVisibility(View.VISIBLE);
//            binding.tvConfirmName.setVisibility(View.VISIBLE);
//            binding.tvConfirmPlace.setVisibility(View.VISIBLE);
//            binding.tvConfirmTime.setVisibility(View.VISIBLE);
//            binding.tvConfirmLatlng.setVisibility(View.VISIBLE);
//            binding.buttonBack.setVisibility(View.VISIBLE);
//            binding.progressBar.setVisibility(View.INVISIBLE);
//
//            // binding.tvTitleConfirm.setVisibility(userName, String placeName,String dateFormatted, String Latitude, String Longitude, String transaction_hash_key);
//
//            //String placeName,String dateFormatted, String Latitude, String Longitude, String transaction_hash_key
//            binding.tvConfirmName.setText("Name = \n" + userName);
//            binding.tvConfirmPlace.setText("Place Name = \n" + placeName);
//            binding.tvConfirmTime.setText("Time = \n" + dateFormatted);
//            binding.tvConfirmLatlng.setText("Latitude/Longitude  = \n" + Latitude + " / " + Longitude);
//            binding.tvTransactionReceipt.setText("Transaction Hash  = \n" + transaction_hash_key);
//            Log.d(TAG, "sendDataHash: Success");
//            //TransactionReceipt transactionReceipt1 = user_dataContract.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//            //user_dataContract_addTranscationHash.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//
//        } else {
//            binding.tvTransactionReceipt.setText("NULL");
//            binding.progressBar.setVisibility(View.VISIBLE);
//        }
//
//
//    }
//
