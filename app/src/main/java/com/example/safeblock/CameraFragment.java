package com.example.safeblock;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.safeblock.databinding.FragmentCameraBinding;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class CameraFragment extends Fragment   {

    FragmentCameraBinding binding;
    private static final String TAG = "CameraFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // View view = inflater.inflate(R.layout.fragment_camera,container,false);
       binding = FragmentCameraBinding.inflate(inflater, container, false);
       // scanCode();
        user_data user_data = getUserData();
        //PlaceData placeData = getPlace();

       // String placeData = new Gson().toJson(new PlaceData("Home",-6.480627, 106.873730));

        binding.buttonQrscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_data !=null){
                    scanQR();
                   // getActivity().startActivity(new Intent(getActivity().getBaseContext(), TranscationActivity.class));
                }
                else{
                    Toast.makeText(getContext(),"Mohon isi data diri terlebih dahulu",Toast.LENGTH_LONG).show();
                }
                //sendDatatoBlockchain(placeData);
//                Intent i = new Intent(getActivity().getBaseContext(), TranscationActivity.class);
//                Toast.makeText(getContext(),"Selesai Diklik",Toast.LENGTH_LONG);
//                getActivity().startActivity(i);
            }
        });

        return binding.getRoot();
    }

    private void sendData(String userName, String placeData)
    {
        //INTENT OBJ
        Intent i = new Intent(getActivity().getBaseContext(), TranscationActivity.class);

        //PACK DATA
        i.putExtra("USER_NAME_KEY", userName);
        i.putExtra("PLACE_DATA_KEY", placeData);

        //START ACTIVITY
        getActivity().startActivity(i);
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Log.d(TAG,"Gagal memuat data QR");
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG,"Result scan QR = " +result.getContents());
                    Toast.makeText(getContext(), "Menyiapkan detail transaksi " + result.getContents(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf((result.getContents()!=null)));
                    //binding.tvCameraFragment.setText(result.getContents());

                    sendData(getUserData().name,result.getContents());

                    //sendDatatoBlockchain(getUserData().name,result.getContents());
                }
            });

    // Launch
    public void scanQR() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setCameraId(0);  // Use a specific camera of the device
        options.setBeepEnabled(false);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false);
        barcodeLauncher.launch(options);
    }

    public user_data getUserData(){
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("Data", "");
        if(!(json.isEmpty())){
            user_data obj = gson.fromJson(json, user_data.class);
            return obj;
        }
        else{
            return null;
        }
    }

    public PlaceData getPlace(){
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson_place = new Gson();
        String json_place_data =  preferences.getString("Data Place","");
        PlaceData obj_place = gson_place.fromJson(json_place_data,PlaceData.class);
        return obj_place;
    }

    public void sendDatatoBlockchain(String place_Data){
        String userName = "Josef Eric";
        PlaceData placeData = new Gson().fromJson(place_Data,PlaceData.class);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

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


        String dateFormatted = formatter.format(date);
        TransactionReceipt transactionReceipt = user_dataContract.create_user_data(userName,placeData.PlaceName,dateFormatted,String.valueOf(placeData.Latitude),String.valueOf(placeData.Longitude)).sendAsync().join();
        Log.d(TAG,"Transaction Recepit = "+transactionReceipt);
        if ((transactionReceipt.getTransactionHash()) != null) {


            user_dataContract.update_transaction_hash(userName,placeData.PlaceName,dateFormatted,String.valueOf(placeData.Latitude),String.valueOf(placeData.Longitude),transactionReceipt.getTransactionHash()).sendAsync().join();
            Log.d(TAG,"Transaction Hash = "+transactionReceipt.getTransactionHash());

        }
        else{

        }

    }
}