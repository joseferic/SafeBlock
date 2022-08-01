package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.ArrayList;
import java.util.List;

public class ListDokterActivity extends AppCompatActivity {

    public List<Dokter> listDokterNow;
    private static final String TAG = "ListDokterActivity";
    public final String contractAddress = "0x938A5FA7b18699D656FF37f89358DB9ce8dD549C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listAdmin_rv);
        Button btn_openHistory = findViewById(R.id.button_openListAdmin);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        TextView tvUserData= findViewById(R.id.tv_dataUser);

        user_data UserData = getIntent().getParcelableExtra("User");
        tvUserData.setText(
                "Nama User = " + UserData.name +
                        "\n\nEmail User = " + UserData.email +
                        "\n\nWallet User = " + UserData.privateKey
                        );


        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(ListDokterActivity.this));

        // this is data from recycler view

        btn_openHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_openHistory.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getListAdmin();

                // 3. create an adapter
                DokterRecyclerViewAdapter mAdapter = new DokterRecyclerViewAdapter(ListDokterActivity.this, listDokterNow, UserData);
                // 4. set adapter
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);


                progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }



    public void getListAdmin(){
        Log.d(TAG, "getListAdmin()");
        listDokterNow = new ArrayList<>();
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        String privateKey = "8b8b105e6f96b7a230239d87d40e0aa96fb45f41f6f1b901b78c827e28648721";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaru contract = SmartContractBaru_sol_SmartContractBaru.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            Log.v("Data Dokter length list", contract.getAddressDoctorArrayLength().sendAsync().get().toString());
            Integer list_length = Integer.valueOf(contract.getAddressDoctorArrayLength().sendAsync().get().toString());
            List listAdmin = contract.getArrayDoctorAccountAddress().sendAsync().get();
            Log.v("List Dokter",listAdmin.toString());
            for (int i = 0; i < list_length; i++) {
                String address = listAdmin.get(i).toString();
                String name = contract.getNameDoctorAddress(address).sendAsync().get();
                Log.v("Name Dokter",name);
                Log.v("Address Dokter",address);
                Dokter dokter = new Dokter(name,address,"","");
                listDokterNow.add(dokter);
                Log.v("Dokter Name = ", dokter.name + " " + dokter.publicAddress.trim().toString());
           }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}