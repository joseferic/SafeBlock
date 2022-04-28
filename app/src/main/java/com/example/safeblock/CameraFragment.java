package com.example.safeblock;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        binding.buttonQrscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR();
            }
        });

        return binding.getRoot();
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
                    try {
                        sendDatatoBlockChain(result.getContents());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
                    builder.setMessage("Scanning Result");
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scanQR();
                        }
                    }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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

    private void sendDatatoBlockChain(String placeName) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x2Fd6E9DF12A797D8D2D76C000C8bCB6164f2985d";
        Credentials credentials = Credentials.create("fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_SmartContract user_dataContract = UserData_SmartContract
                .load(contractAddress,web3j,credentials,contractGasProvider);


        user_dataContract.create_user_data("Josef Eric", placeName,formatter.format(date)).flowable()
                .subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
                    @Override
                    public void accept(TransactionReceipt transactionReceipt) throws Exception {
                        Log.d(TAG,transactionReceipt.getTransactionHash());
                        Toast.makeText(getContext(),"Data Saved at Ethereum",Toast.LENGTH_LONG).show();
                    }
                });
        //Log.d(TAG, receipt.get().getTransactionHash());


    }
}