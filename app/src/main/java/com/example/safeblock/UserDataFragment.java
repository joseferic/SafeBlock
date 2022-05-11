package com.example.safeblock;



import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.safeblock.databinding.FragmentUserDataBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;


public class UserDataFragment extends Fragment {

    private FragmentUserDataBinding binding;

    public Boolean stateDataSaved= false;

    private Boolean Hide;

    public user_data userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDataBinding.inflate(inflater,container,false);


        checkState();
        if (stateDataSaved == true){
            Hide = true;
            if (userData != null){
                binding.imageviewQuestionmark.setVisibility(View.INVISIBLE);
                binding.tvUserData.setVisibility(View.VISIBLE);
                binding.tvUserData.setText(
                        "Nama User = " + userData.name +
                                "\n\nEmail User = " + userData.email +
                                "\n\nWallet User = " + userData._walletAddress);
            }
            else if (userData == null){
                binding.tvUserData.setVisibility(View.INVISIBLE);
                binding.imageviewQuestionmark.setVisibility(View.VISIBLE);
            }
            binding.inputNama.setVisibility(View.INVISIBLE);
            binding.inputEmail.setVisibility(View.INVISIBLE);
            binding.inputPrivateKey.setVisibility(View.INVISIBLE);
            binding.simpanButton.setVisibility(View.INVISIBLE);
            binding.backButton.setText("Input Data");

        } else if (stateDataSaved == false){
            Hide = false;
            binding.inputNama.setVisibility(View.VISIBLE);
            binding.inputEmail.setVisibility(View.VISIBLE);
            binding.inputPrivateKey.setVisibility(View.VISIBLE);
            binding.simpanButton.setVisibility(View.VISIBLE);
            binding.backButton.setText("Back");

        } else{
            Toast.makeText(getContext(),"stateDataSaved = null",Toast.LENGTH_LONG).show();
        }

        binding.simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_data data = new user_data(
                        binding.inputNama.getText().toString().trim(),
                        binding.inputEmail.getText().toString().trim(),
                        binding.inputPrivateKey.getText().toString().trim(),
                        false
                );
                saveUserData(data);
                checkState();
                Hide = true;
                if (userData != null){
                    binding.imageviewQuestionmark.setVisibility(View.INVISIBLE);
                    binding.tvUserData.setVisibility(View.VISIBLE);
                    binding.tvUserData.setText(
                            "Nama User = " + userData.name +
                                    "\n\nEmail User = " + userData.email +
                                    "\n\nWallet User = " + userData._walletAddress);
                }
                else if (userData == null){
                    binding.tvUserData.setVisibility(View.INVISIBLE);
                    binding.imageviewQuestionmark.setVisibility(View.VISIBLE);
                }
                binding.inputNama.setVisibility(View.INVISIBLE);
                binding.inputEmail.setVisibility(View.INVISIBLE);
                binding.inputPrivateKey.setVisibility(View.INVISIBLE);
                binding.simpanButton.setVisibility(View.INVISIBLE);
                binding.backButton.setVisibility(View.VISIBLE);
                binding.backButton.setText("Input Data");
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Hide == false){
                    Hide = true;
                    if (userData != null){
                        binding.imageviewQuestionmark.setVisibility(View.INVISIBLE);
                        binding.tvUserData.setVisibility(View.VISIBLE);
                        binding.tvUserData.setText(
                                "Nama User = " + userData.name +
                                        "\n\nEmail User = " + userData.email +
                                        "\n\nWallet User = " + userData._walletAddress);
                    }
                    else if (userData == null){
                        binding.tvUserData.setVisibility(View.INVISIBLE);
                        binding.imageviewQuestionmark.setVisibility(View.VISIBLE);
                    }
                    binding.inputNama.setVisibility(View.INVISIBLE);
                    binding.inputEmail.setVisibility(View.INVISIBLE);
                    binding.inputPrivateKey.setVisibility(View.INVISIBLE);
                    binding.simpanButton.setVisibility(View.INVISIBLE);
                    binding.backButton.setVisibility(View.VISIBLE);
                    binding.backButton.setText("Input Data");
                }else if(Hide == true){
                    Hide = false;
                    binding.tvUserData.setVisibility(View.INVISIBLE);
                    binding.imageviewQuestionmark.setVisibility(View.INVISIBLE);
                    binding.inputNama.setVisibility(View.VISIBLE);
                    binding.inputEmail.setVisibility(View.VISIBLE);
                    binding.inputPrivateKey.setVisibility(View.VISIBLE);
                    binding.backButton.setVisibility(View.VISIBLE);
                    binding.simpanButton.setVisibility(View.VISIBLE);
                    binding.backButton.setText("Back");
                }
            }
        });

        return binding.getRoot();
    }


    public void saveUserData(user_data data){
        //set variables of 'myObject', etc.
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString("Data", json);
        prefsEditor.commit();
    }

    public void checkState(){
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("Data", "");
            if (json != null){
                userData = gson.fromJson(json, user_data.class);
                if (userData != null){
                    stateDataSaved = true;
                }
                else{
                    stateDataSaved = false;
                }
            }
            else{
                stateDataSaved = false;
            }
        }

}

