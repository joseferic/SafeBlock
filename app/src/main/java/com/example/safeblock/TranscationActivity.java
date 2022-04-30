package com.example.safeblock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


import androidx.appcompat.app.AppCompatActivity;


import com.example.safeblock.databinding.ActivityTranscationBinding;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;


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
        String[] Data = receiveData();
        if (Data != null){
            sendDatatoBlockChain(Data[0],Data[1]);
        }
        else{
            binding.tvReceipt.setText("DATA TIDAK BERHASIL TERKIRIM");
        }

    }


    private String[] receiveData()
    {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();

        String user_name = i.getStringExtra("USER_NAME_KEY");
        String place_name = i.getStringExtra("PLACE_NAME_KEY");
        if (place_name != null && user_name != null){
            return new String[]{user_name, place_name};
        }
        else{
            return null;
        }
    }

    private void sendDatatoBlockChain(String userName,String placeName) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x2efd3Dc020D75f5Abf12C74F011aBB3Be8fc7f6C";
        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);

        TransactionReceipt transactionReceipt = user_dataContract.create_user_data(userName,placeName,formatter.format(date)).sendAsync().join();
        if ((transactionReceipt.getTransactionHash()) != null) {
            binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
            Intent intent = new Intent(TranscationActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            binding.tvReceipt.setText("NULL");
        }


    }


    public user_data getUserData(){
        MainActivity mContext = new MainActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.getContext());
        Gson gson = new Gson();
        String json = preferences.getString("Data", "");
        user_data obj = gson.fromJson(json, user_data.class);
        return obj;
    }

    public PlaceData getPlace(){
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        Gson gson_place = new Gson();
        String json_place_data =  preferences.getString("Data Place","");
        PlaceData obj_place = gson_place.fromJson(json_place_data,PlaceData.class);
        return obj_place;
    }
}