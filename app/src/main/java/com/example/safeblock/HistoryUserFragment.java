package com.example.safeblock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.safeblock.databinding.FragmentHistoryUserBinding;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class HistoryUserFragment extends Fragment {

    FragmentHistoryUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryUserBinding.inflate(inflater,container,false);

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("Data", "");
        user_data obj = gson.fromJson(json, user_data.class);
        if (obj !=null){
            binding.historyuserTv.setText(obj.toString());
        }
        else{
            binding.historyuserTv.setText("Data User Null");
        }
       // binding.historyuserTv.setText(obj.toString());
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            Log.v("DATA HISTORY FRAGMENT", String.valueOf((result!=null)));
            if (result.getContents() != null){
                Log.v("DATA HISTORY FRAGMENT", String.valueOf((result.getContents()!=null)));
                AlertDialog.Builder builder = new AlertDialog.Builder((getContext()));
                builder.setMessage("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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