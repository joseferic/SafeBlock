package com.example.safeblock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


import com.example.safeblock.databinding.ActivityTranscationBinding;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class TranscationActivity extends AppCompatActivity {


    private static final String TAG = "TransactionActivity";
    private ActivityTranscationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTranscationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //user_data user_data = getUserData();
        //PlaceData placeData = getPlace();
        //String placeName = receiveData();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        //System.out.println(formatter.format(date));
        String dateFormatted = formatter.format(date);

        String[] Data = receiveData();
        if (Data != null) {
            binding.tvConfirmName.setText("Name = " + Data[0]);
            binding.tvConfirmPlace.setText("Place Name = " + Data[1]);
            binding.tvConfirmTime.setText("Time = " + dateFormatted);
            binding.tvConfirmLatlng.setText("Latitude/Longitude = " + Data[2] + " / " + Data[3]);

            binding.progressBar.setVisibility(View.INVISIBLE);
        } else {
            binding.tvTitleConfirm.setText("GAGAL MEMUAT");
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.tvConfirmName.setVisibility(View.INVISIBLE);
            binding.tvConfirmPlace.setVisibility(View.INVISIBLE);
            binding.tvConfirmTime.setVisibility(View.INVISIBLE);
            binding.tvConfirmLatlng.setVisibility(View.INVISIBLE);
            binding.buttonConfirm.setVisibility(View.INVISIBLE);
        }
        //sendDatatoBlockChain("Josef Eric", "Rumah Josef Eric", dateFormatted, "This is latitude", "this is longitude");
        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Data != null && !(dateFormatted.isEmpty())) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.tvTitleConfirm.setVisibility(View.INVISIBLE);
                    binding.tvConfirmName.setVisibility(View.INVISIBLE);
                    binding.tvConfirmPlace.setVisibility(View.INVISIBLE);
                    binding.tvConfirmTime.setVisibility(View.INVISIBLE);
                    binding.tvConfirmLatlng.setVisibility(View.INVISIBLE);
                    binding.buttonConfirm.setVisibility(View.INVISIBLE);
                    sendDatatoBlockChainOther(Data[0], Data[1], dateFormatted, Data[2], Data[3],Data[4],Data[5],Data[6]);
                } else {

                }
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TranscationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private String[] receiveData() {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();

        String user_name = i.getStringExtra("USER_NAME_KEY");
        String place_data = i.getStringExtra("PLACE_DATA_KEY");
        String email_data = i.getStringExtra("EMAIL_DATA_KEY");
        String private_key_wallet_data_key = i.getStringExtra("PRIVATE_KEY_WALLET_DATA_KEY");
        String status_data = i.getStringExtra("STATUS_DATA_KEY");

        PlaceData placeData = new Gson().fromJson(place_data, PlaceData.class);

        if (place_data != null && user_name != null) {
            return new String[]{user_name, placeData.PlaceName, String.valueOf(placeData.Latitude), String.valueOf(placeData.Longitude),
                    email_data,private_key_wallet_data_key,status_data};
        } else {
            return null;
        }
    }

    private void sendDatatoBlockChain(String userName, String placeName, String dateFormatted, String Latitude, String Longitude,String email_data,String private_key_wallet_data_key,String status_data) {
        Log.d(TAG, "sendDatatoBlockChain:");


        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0xc5F8e80Dd0E58B182A5820B7063b413248039b58";
        String privateKey = private_key_wallet_data_key;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);

        String userData = new Gson().toJson(new InputData(userName,email_data,privateKey,Boolean.parseBoolean(status_data), placeName,Double.parseDouble(Latitude),Double.parseDouble(Longitude),dateFormatted));

        user_dataContract.create_user_data(userName, placeName, dateFormatted, Latitude, Longitude).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                Log.d(TAG, "Transaction Recepit = " + transactionReceipt);
                if ((transactionReceipt.getTransactionHash()) != null) {
                    //binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Transaction Hash = " + transactionReceipt.getTransactionHash());

                    Intent intent = new Intent(TranscationActivity.this, AddTransactionHash.class);
                    intent.putExtra("USER_NAME_KEY", userName);
                    intent.putExtra("PLACE_NAME_KEY", placeName);
                    intent.putExtra("TIME_KEY", dateFormatted);
                    intent.putExtra("PLACE_LATITUDE_KEY", Latitude);
                    intent.putExtra("PLACE_LONGITUDE_KEY", Longitude);
                    intent.putExtra("TRANSACTION_HASH_KEY", transactionReceipt.getTransactionHash());
                    startActivity(intent);
                } else {
                    //binding.tvReceipt.setText("NULL");
                }
            }
        });


    }

    private void sendDatatoBlockChainOtherOld(String userName, String placeName, String dateFormatted, String Latitude, String Longitude,String email_data,String private_key_wallet_data_key,String status_data) {
        Log.d(TAG, "sendDatatoBlockChain:");
        binding.tvTitleConfirm.setText("DATA SAVED");
        binding.buttonConfirm.setVisibility(View.INVISIBLE);


        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0xc5F8e80Dd0E58B182A5820B7063b413248039b58";
        String privateKey = private_key_wallet_data_key;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);

        String userData = new Gson().toJson(new InputData(userName,email_data,privateKey,Boolean.parseBoolean(status_data), placeName,Double.parseDouble(Latitude),Double.parseDouble(Longitude),dateFormatted));


        TransactionReceipt transactionReceipt = user_dataContract.create_user_data(userName, placeName, dateFormatted, Latitude, Longitude).sendAsync().join();
        Log.d(TAG, "Transaction Recepit = " + transactionReceipt);
        if ((transactionReceipt.getTransactionHash()) != null) {
            //binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
            binding.progressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "Transaction Hash = " + transactionReceipt.getTransactionHash());

            Intent intent = new Intent(TranscationActivity.this, AddTransactionHash.class);
            intent.putExtra("USER_NAME_KEY", userName);
            intent.putExtra("PLACE_NAME_KEY", placeName);
            intent.putExtra("TIME_KEY", dateFormatted);
            intent.putExtra("PLACE_LATITUDE_KEY", Latitude);
            intent.putExtra("PLACE_LONGITUDE_KEY", Longitude);
            intent.putExtra("TRANSACTION_HASH_KEY", transactionReceipt.getTransactionHash());
            startActivity(intent);

        } else {

        }

    }

    private void sendDatatoBlockChainOther(String userName, String placeName, String dateFormatted, String Latitude, String Longitude,String email_data,String private_key_wallet_data_key,String status_data) {
        Log.d(TAG, "sendDatatoBlockChain:");
        binding.tvTitleConfirm.setText("DATA SAVED");
        binding.buttonConfirm.setVisibility(View.INVISIBLE);


        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x45FaAD5Aa2E382dce15c4e518eb301bfFdCc249e";
        String privateKey = private_key_wallet_data_key;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SafeBlock_sol_SafeBlock contract = SafeBlock_sol_SafeBlock
                .load(contractAddress, web3j, credentials, contractGasProvider);

        String userData = new Gson().toJson(new InputData(userName,email_data,privateKey,Boolean.parseBoolean(status_data), placeName,Double.parseDouble(Latitude),Double.parseDouble(Longitude),dateFormatted));
        String encryptedString = AES.encrypt(userData,userName);

        TransactionReceipt transactionReceipt = contract.createUserData(encryptedString).sendAsync().join();
        Log.d(TAG, "Transaction Receipt = " + transactionReceipt);
        if ((transactionReceipt.getTransactionHash()) != null) {
            //binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
            binding.progressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "Transaction Hash = " + transactionReceipt.getTransactionHash());

            Intent intent = new Intent(TranscationActivity.this, AddTransactionHash.class);
            intent.putExtra("USER_NAME_KEY", userName);
            intent.putExtra("PLACE_NAME_KEY", placeName);
            intent.putExtra("TIME_KEY", dateFormatted);
            intent.putExtra("PLACE_LATITUDE_KEY", Latitude);
            intent.putExtra("PLACE_LONGITUDE_KEY", Longitude);
            intent.putExtra("TRANSACTION_HASH_KEY", transactionReceipt.getTransactionHash());
            intent.putExtra("ENCRYPTED_DATA_KEY",encryptedString);
            intent.putExtra("PRIVATEKEY_DATA_KEY",privateKey);
            startActivity(intent);

        } else {

        }

    }
//    public user_data getUserData() {
//        MainActivity mContext = new MainActivity();
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.getContext());
//        Gson gson = new Gson();
//        String json = preferences.getString("Data", "");
//        user_data obj = gson.fromJson(json, user_data.class);
//        return obj;
//    }
//
//    public PlaceData getPlace() {
//        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
//        Gson gson_place = new Gson();
//        String json_place_data = preferences.getString("Data Place", "");
//        PlaceData obj_place = gson_place.fromJson(json_place_data, PlaceData.class);
//        return obj_place;
//    }

}