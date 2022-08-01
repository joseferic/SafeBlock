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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class DokterModeActivity extends AppCompatActivity {

    private static final String TAG = "DokterModeActivity";
    public List<TestCovid> listData;
    Dokter dokter;
    public final String contractAddress = "0x938A5FA7b18699D656FF37f89358DB9ce8dD549C";

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
        recyclerView.setLayoutManager(new LinearLayoutManager(DokterModeActivity.this));

        // this is data from recycler view

        btn_openHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_openHistory.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getAllData();

                // 3. create an adapter
                UserRecyclerViewAdapter mAdapter = new UserRecyclerViewAdapter(DokterModeActivity.this, listData,dokter.privateKey);
                // 4. set adapter
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);


                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void getAllData() {
        listData = new ArrayList<>();
        //List<TestCovid> listDataTemp = new ArrayList<>();
        //listDataFiltered = new ArrayList<Data>();
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String privateKey = dokter.privateKey;

        Credentials credentials = Credentials.create(privateKey);

        /*

        0:
string: testId PCR-JosefEric-DrOcto-18072022-1440
1:
string: patientName Josef Eric
2:
string: detailTest CkOTg66DkR7M+T1aKKTPsVWn7d4kOK0JvwhTwFUxxDtw5tGD+5v3J1/kezPesoouRw9gRYNnaMWF3/7y6ZvDBdMmlQtxVJfzEvRwbcGZt/qEjOlTsDGEC4eCc/Bc5Rjv9bHpXmn6zZeNH0DPouPKA3zV0SELwsWzMG8JrGpgg7wRP8gbXTs7u0MRpUENMr0nhOFI/EfSqJOw2EEVWYX21JTNKFgQKBmcdNoUxc8eypoSH6zQ9YBb41y+A/C4E8o44FmiuUJfO/pULsHtkfV9y4mCBowvxvY2JVU7+ojK4zFpZ6JaFLP3iaUCAKpy7ycqpEnvvpIbzmSSO3MNysb6WlkTIgQ10EVxBIBBqhu0C8N1do79JROrIoQjnFiNONXvpx4GD2JjJxeng257jtN2KRcC1QV+fOCA1Zqz/YjoB0wMPYUcppqMewjJrw+U9YYknz8usedSpDNsiSbwzYcHaAhF4lGiGJaT/FwQNmFRB3TB7cJg90DMC5+kTc7uc5nYCPvYsqjyPz87AN/EJaI2qF3IC6So6kH8nkgmL+tIC/c=
3:
string: stateTest Menunggu Tes Dilaksanakan
4:
bool: resultTest false

         */


        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaru contract = SmartContractBaru_sol_SmartContractBaru.load(contractAddress, web3j, credentials, contractGasProvider);
        try {
            //userData.create_user_data("Josef Eric","Test Place",formatter.format(date)).sendAsync().get();
            Log.v("DokterModeActivity", contract.getListTestCovidByDoctor().sendAsync().get().toString());
            Integer listDataSize = contract.getListTestCovidByDoctor().sendAsync().get().size();
            for (int i=0;i<listDataSize;i++){
                String testId = contract.listTestCovid(BigInteger.valueOf(i)).sendAsync().get().component1();
                String patientName = contract.listTestCovid(BigInteger.valueOf(i)).sendAsync().get().component2();
                String detailTest = contract.listTestCovid(BigInteger.valueOf(i)).sendAsync().get().component3();
                String stateTest = contract.listTestCovid(BigInteger.valueOf(i)).sendAsync().get().component4();
                Boolean resultTest = contract.listTestCovid(BigInteger.valueOf(i)).sendAsync().get().component5();
                //listData.add(new TestCovid()
            }

//            Integer list_length = Integer.valueOf(contract.getDataListLength().sendAsync().get().toString());
//
//            for (int i = 0; i < list_length; i++) {
//
//                String data = contract.data(BigInteger.valueOf(i)).sendAsync().get().component2();
//                Log.v("History User Progres: data = ", data);
//                String dataKey = contract.data(BigInteger.valueOf(i)).sendAsync().get().component3();
//                Log.v("History User Progres: dataKey = ", dataKey);
//                // Log.v("dataKey = ", dataKey);
//                String key = AES.decrypt(dataKey, "SafeBlockIsSafe");
//                Log.v("History User Progres: key = ", key);
//
//                String dataJson = AES.decrypt(data, key);
//
//
//                Log.v("History User Progres: dataJson = ", dataJson);
//                InputData inputData = new Gson().fromJson(dataJson, InputData.class);
//                Log.v("History User Progres: inputData = ", inputData.toString());
//                String transaction_hash = contract.data(BigInteger.valueOf(i)).sendAsync().get().component1();
//
//                Data nData = new Data(
//                        BigInteger.valueOf(i + 1),
//                        transaction_hash,
//                        inputData.PrivateKey,
//                        inputData.UserName,
//                        inputData.Email,
//                        inputData.PlaceName,
//                        inputData.Date,
//                        String.valueOf(inputData.Latitude),
//                        String.valueOf(inputData.Longitude),
//                        inputData.Infected);
//
//                Log.v("Data User Detail ", contract.data(BigInteger.valueOf(i)).sendAsync().get().toString());
//                listData.add(nData);
//
//            }
//            List<String >listName = filterUserName(listData);
//            for (int i = 0; i <listName.size(); i++){
//                Tuple2<String, Boolean> dataCache = contract.checkCurrentUserStatus(listName.get(i)).sendAsync().get();
//                User user = new User(dataCache.component1(),dataCache.component2());
//                listUserName.add(user);
//            }
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        Log.v("Data List User", String.valueOf(listData));
//        Log.v("Data List User ", String.valueOf(listData.size()));
//
//        Log.v("Data List UserName", String.valueOf(listUserName));
//        Log.v("Data List UserName ", String.valueOf(listUserName.size()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//        public List<String> filterUserName(List<Data> listData) {
//        List<String> userName = new ArrayList<>();
//
//        // get All place name
//        for (int i = 0; i < listData.size(); i++) {
//            userName.add(listData.get(i).name);
//        }
//        // Create a list with the distinct elements using stream.
//        List<String> userNamelist = userName.stream().distinct().collect(Collectors.toList());
//
//
//        return userNamelist;
    }
}

