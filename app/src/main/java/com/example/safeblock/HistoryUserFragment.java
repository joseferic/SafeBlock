package com.example.safeblock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.safeblock.databinding.FragmentHistoryUserBinding;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class HistoryUserFragment extends Fragment {

    FragmentHistoryUserBinding binding;


    public List<Data> listData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_user, container, false);
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.history_rv);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // this is data from recycler view
        user_data user_data = getUserData();
        getAllData(user_data.name);


        // 3. create an adapter
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(getContext(),listData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void getAllData(String username){
        listData = new ArrayList<>();
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x2efd3Dc020D75f5Abf12C74F011aBB3Be8fc7f6C";
        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData userData = UserData_sol_UserData.load(contractAddress,web3j,credentials,contractGasProvider);
        try {
            //userData.create_user_data("Josef Eric","Test Place",formatter.format(date)).sendAsync().get();
            Log.v("Data length list",userData.get_user_list_length().sendAsync().get().toString());
            Integer list_length = Integer.valueOf(userData.get_user_list_length().sendAsync().get().toString());

            for (int i=0; i<list_length;i++){
                Data nData = new Data(
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component1(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component2(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component3(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component4(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component5(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component6(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component7()
                );
                Log.v("Data User On List ", nData.transaction_id.toString());
                Log.v("Data User Detail ",userData.users(BigInteger.valueOf(i)).sendAsync().get().toString());
                listData.add(nData);
            }

            Log.v("Data List Sebelum Filter", String.valueOf(listData));
            Log.v("Data List Sebelum Filter ", String.valueOf(listData.size()));


            for (int i=0; i<listData.size(); i++){
                if (listData.get(i).name != username){
                    listData.remove(i);
                }
            }
            Log.v("Data List Sesudah Filter", String.valueOf(listData));
            Log.v("Data List Sesudah Filter ", String.valueOf(listData.size()));
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }

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

    //        user_data obj = getUserData();
//
//        if (obj !=null){
//            binding.historyuserTv.setText(obj.toString());
//        }
//        else{
//            binding.historyuserTv.setText("Data User Null");
//        }
//
//        PlaceData obj_place = getPlace();
//
//        if (obj_place !=null){
//            binding.tvTestDataGmaps.setText(obj_place.toString());
//        }
//        else{
//            binding.tvTestDataGmaps.setText("Data Gmaps Null");
//        }
    // binding.historyuserTv.setText(obj.toString());
}