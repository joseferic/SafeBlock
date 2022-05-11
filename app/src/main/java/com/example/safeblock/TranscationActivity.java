package com.example.safeblock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import java.util.concurrent.ExecutionException;

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
                    sendDatatoBlockChainOther(Data[0], Data[1], dateFormatted, Data[2], Data[3]);
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

        PlaceData placeData = new Gson().fromJson(place_data, PlaceData.class);

        if (place_data != null && user_name != null) {
            return new String[]{user_name, placeData.PlaceName, String.valueOf(placeData.Latitude), String.valueOf(placeData.Longitude)};
        } else {
            return null;
        }
    }

    private void sendDatatoBlockChainOther(String userName, String placeName, String dateFormatted, String Latitude, String Longitude) {
        Log.d(TAG, "sendDatatoBlockChain:");
        binding.tvTitleConfirm.setText("DATA SAVED");
        binding.buttonConfirm.setVisibility(View.INVISIBLE);


        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0xc5F8e80Dd0E58B182A5820B7063b413248039b58";
        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);


        user_dataContract.create_user_data(userName, placeName, dateFormatted, Latitude, Longitude).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                Log.d(TAG, "Transaction Recepit = " + transactionReceipt);
                if ((transactionReceipt.getTransactionHash()) != null) {
                    //binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Transaction Hash = " + transactionReceipt.getTransactionHash());

//            //  String transactionHash = transactionReceipt.getTransactionHash();
//            //TransactionReceipt transactionReceipt1 = user_dataContract.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//            //user_dataContract_addTranscationHash.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//
//
//            Intent intent = new Intent(TranscationActivity.this, AddTransactionHash.class);
//            intent.putExtra("USER_NAME_KEY", userName);
//            intent.putExtra("PLACE_NAME_KEY", placeName);
//            intent.putExtra("TIME_KEY", dateFormatted);
//            intent.putExtra("PLACE_LATITUDE_KEY", Latitude);
//            intent.putExtra("PLACE_LONGITUDE_KEY", Longitude);
//            intent.putExtra("TRANSACTION_HASH_KEY", transactionReceipt.getTransactionHash());
//            startActivity(intent);


                } else {
                    //binding.tvReceipt.setText("NULL");
                }
            }
        });


    }

    private void sendDatatoBlockChain(String userName, String placeName, String dateFormatted, String Latitude, String Longitude) {
        Log.d(TAG, "sendDatatoBlockChain:");
        binding.tvTitleConfirm.setText("DATA SAVED");
        binding.buttonConfirm.setVisibility(View.INVISIBLE);


        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0xc5F8e80Dd0E58B182A5820B7063b413248039b58";
        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);


        TransactionReceipt transactionReceipt = user_dataContract.create_user_data(userName, placeName, dateFormatted, Latitude, Longitude).sendAsync().join();
        Log.d(TAG, "Transaction Recepit = " + transactionReceipt);
        if ((transactionReceipt.getTransactionHash()) != null) {
            //binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
            binding.progressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "Transaction Hash = " + transactionReceipt.getTransactionHash());

//            //  String transactionHash = transactionReceipt.getTransactionHash();
//            //TransactionReceipt transactionReceipt1 = user_dataContract.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//            //user_dataContract_addTranscationHash.update_transaction_hash(userName,placeName,dateFormatted,Latitude,Longitude,transactionReceipt.getTransactionHash()).sendAsync().join();
//
//
//            Intent intent = new Intent(TranscationActivity.this, AddTransactionHash.class);
//            intent.putExtra("USER_NAME_KEY", userName);
//            intent.putExtra("PLACE_NAME_KEY", placeName);
//            intent.putExtra("TIME_KEY", dateFormatted);
//            intent.putExtra("PLACE_LATITUDE_KEY", Latitude);
//            intent.putExtra("PLACE_LONGITUDE_KEY", Longitude);
//            intent.putExtra("TRANSACTION_HASH_KEY", transactionReceipt.getTransactionHash());
//            startActivity(intent);


        } else {
            //binding.tvReceipt.setText("NULL");
        }

    }


    public user_data getUserData() {
        MainActivity mContext = new MainActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.getContext());
        Gson gson = new Gson();
        String json = preferences.getString("Data", "");
        user_data obj = gson.fromJson(json, user_data.class);
        return obj;
    }

    public PlaceData getPlace() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        Gson gson_place = new Gson();
        String json_place_data = preferences.getString("Data Place", "");
        PlaceData obj_place = gson_place.fromJson(json_place_data, PlaceData.class);
        return obj_place;
    }
}