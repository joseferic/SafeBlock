package com.example.safeblock;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safeblock.databinding.FragmentUserDataBinding;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class UserDataFragment extends Fragment {

    private FragmentUserDataBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDataBinding.inflate(inflater,container,false);
        binding.inputNama.setText("Josef Eric");
        binding.inputEmail.setText("joseferic1@gmail.com");
        binding.inputPrivateKey.setText("fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5");

        binding.simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_data data = new user_data(
                        binding.inputNama.getText().toString().trim(),
                        binding.inputEmail.getText().toString().trim(),
                        binding.inputPrivateKey.getText().toString().trim(),
                        false
                );
                //set variables of 'myObject', etc.
                SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = preferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(data);
                prefsEditor.putString("Data", json);
                prefsEditor.commit();


//                To retrieve:
//                Gson gson = new Gson();
//                String json = mPrefs.getString("data", "");
//                MyObject obj = gson.fromJson(json, MyObject.class);
            }
        });



        return binding.getRoot();
    }


}