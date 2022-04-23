package com.example.safeblock;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.safeblock.databinding.FragmentCameraBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class CameraFragment extends Fragment   {

    FragmentCameraBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        scanCode();
        return binding.getRoot();
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        // use forSupportFragment or forFragment method to use fragments instead of activity
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
       // integrator.setPrompt("Scan Bacrcode");
//        integrator.setResultDisplayDuration(0); // milliseconds to display result on screen after scan
//        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            Log.v("DATA CAMERA FRAGMENT", String.valueOf((result!=null)));
            if (result.getContents() != null){
                Log.v("DATA CAMERA FRAGMENT", String.valueOf((result.getContents()!=null)));
                binding.tvCameraFragment.setText(result.getContents().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
                builder.setMessage("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

}