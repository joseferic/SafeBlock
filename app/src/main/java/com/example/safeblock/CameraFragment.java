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
        PlaceData placeData = getPlace();

        binding.buttonQrscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR();
            }
        });

        return binding.getRoot();
    }

    private void sendData(String userName, String placeName)
    {
        //INTENT OBJ
        Intent i = new Intent(getActivity().getBaseContext(), TranscationActivity.class);

        //PACK DATA
        i.putExtra("USER_NAME_KEY", userName);
        i.putExtra("PLACE_NAME_KEY", placeName);

        //START ACTIVITY
        getActivity().startActivity(i);
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Log.d(TAG,"GAGAL");
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG,"BERHASIL" +result.getContents());
                    Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, String.valueOf((result.getContents()!=null)));
                    binding.tvCameraFragment.setText(result.getContents());
                    sendData(getUserData().name,result.getContents());
//                    AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
//                    builder.setMessage("Scanning Result");
//                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            scanQR();
//                        }
//                    }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
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
        user_data obj = gson.fromJson(json, user_data.class);
        return obj;
    }

    public PlaceData getPlace(){
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson_place = new Gson();
        String json_place_data =  preferences.getString("Data Place","");
        PlaceData obj_place = gson_place.fromJson(json_place_data,PlaceData.class);
        return obj_place;
    }
}