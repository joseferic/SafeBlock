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

import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminModeActivity extends AppCompatActivity {

    private static final String TAG = "AdminModeActivity";
    public List<Data> listData;
    public List<User> listUserName;
    Dokter dokter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mode);

        dokter = getIntent().getParcelableExtra("dokter");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.username_rv);
        Button btn_openHistory = findViewById(R.id.button_openUsername);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView textView = findViewById(R.id.tv_testAdminMode);
        textView.setText("Nama = "+ dokter.name+"\nPublic Key = "+ dokter.publicAddress+
        "\nPrivate Key = "+ dokter.privateKey);

        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminModeActivity.this));

        // this is data from recycler view

        btn_openHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_openHistory.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getAllData();

                // 3. create an adapter
                UserRecyclerViewAdapter mAdapter = new UserRecyclerViewAdapter(AdminModeActivity.this, listUserName, dokter.privateKey);
                // 4. set adapter
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);


                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void getAllData() {
        listData = new ArrayList<>();
        listUserName = new ArrayList<>();
        //listDataFiltered = new ArrayList<Data>();
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = dokter.privateKey;

        Credentials credentials = Credentials.create(privateKey);

        Log.v("Data length list", Credentials.create(privateKey).getAddress().trim());
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        NewSafeBlock_sol_NewSafeBlock contract = NewSafeBlock_sol_NewSafeBlock.load(contractAddress, web3j, credentials, contractGasProvider);
        try {
            //userData.create_user_data("Josef Eric","Test Place",formatter.format(date)).sendAsync().get();
            Log.v("Data length list", contract.getDataListLength().sendAsync().get().toString());
            Integer list_length = Integer.valueOf(contract.getDataListLength().sendAsync().get().toString());

            for (int i = 0; i < list_length; i++) {

                String data = contract.data(BigInteger.valueOf(i)).sendAsync().get().component2();
                Log.v("History User Progres: data = ", data);
                String dataKey = contract.data(BigInteger.valueOf(i)).sendAsync().get().component3();
                Log.v("History User Progres: dataKey = ", dataKey);
                // Log.v("dataKey = ", dataKey);
                String key = AES.decrypt(dataKey, "SafeBlockIsSafe");
                Log.v("History User Progres: key = ", key);

                String dataJson = AES.decrypt(data, key);


                Log.v("History User Progres: dataJson = ", dataJson);
                InputData inputData = new Gson().fromJson(dataJson, InputData.class);
                Log.v("History User Progres: inputData = ", inputData.toString());
                String transaction_hash = contract.data(BigInteger.valueOf(i)).sendAsync().get().component1();

                Data nData = new Data(
                        BigInteger.valueOf(i + 1),
                        transaction_hash,
                        inputData.PrivateKey,
                        inputData.UserName,
                        inputData.Email,
                        inputData.PlaceName,
                        inputData.Date,
                        String.valueOf(inputData.Latitude),
                        String.valueOf(inputData.Longitude),
                        inputData.Infected);

                Log.v("Data User Detail ", contract.data(BigInteger.valueOf(i)).sendAsync().get().toString());
                listData.add(nData);

            }
            List<String >listName = filterUserName(listData);
            for (int i = 0; i <listName.size(); i++){
                Tuple2<String, Boolean> dataCache = contract.checkCurrentUserStatus(listName.get(i)).sendAsync().get();
                User user = new User(dataCache.component1(),dataCache.component2());
                listUserName.add(user);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.v("Data List User", String.valueOf(listData));
        Log.v("Data List User ", String.valueOf(listData.size()));

        Log.v("Data List UserName", String.valueOf(listUserName));
        Log.v("Data List UserName ", String.valueOf(listUserName.size()));

    }


    public List<String> filterUserName(List<Data> listData) {
        List<String> userName = new ArrayList<>();

        // get All place name
        for (int i = 0; i < listData.size(); i++) {
            userName.add(listData.get(i).name);
        }
        // Create a list with the distinct elements using stream.
        List<String> userNamelist = userName.stream().distinct().collect(Collectors.toList());


        return userNamelist;
    }
}

