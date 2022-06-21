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

import com.example.safeblock.TranscationActivity;
import com.example.safeblock.databinding.FragmentCameraBinding;
import com.example.safeblock.user_data;
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

       binding = FragmentCameraBinding.inflate(inflater, container, false);

        user_data user_data = getUserData();


        binding.buttonQrscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_data !=null){
                    if (user_data.picture.equals("null") || user_data.infected == null){
                        Toast.makeText(getContext(),"Mohon lengkapi status dan sertai bukti hasil pemeriksaan lab Test Covid 19",Toast.LENGTH_LONG).show();
                    }
                    else if (user_data.infected.equals(true)){
                        Toast.makeText(getContext(),"Mohon untuk tetap dirumah dan tidak memasuki tempat ini",Toast.LENGTH_LONG).show();
                    }
                    else {
                        scanQR();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Mohon isi data diri terlebih dahulu",Toast.LENGTH_LONG).show();
                }
            }
        });

        return binding.getRoot();
    }

    private void sendData(String userName, String placeData,String email, String privateKeyWalletUser,Boolean infected)
    {
        //INTENT OBJ
        Intent i = new Intent(getActivity().getBaseContext(), TranscationActivity.class);

        //PACK DATA
        i.putExtra("USER_NAME_KEY", userName);
        i.putExtra("PLACE_DATA_KEY", placeData);
        i.putExtra("EMAIL_DATA_KEY", email);
        i.putExtra("PRIVATE_KEY_WALLET_DATA_KEY", privateKeyWalletUser);
        i.putExtra("STATUS_DATA_KEY", infected);
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

                    sendData(getUserData().name,result.getContents(),getUserData().email,getUserData()._walletAddress,getUserData().infected);

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




}