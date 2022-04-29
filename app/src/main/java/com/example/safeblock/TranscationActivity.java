package com.example.safeblock;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.safeblock.databinding.ActivityTranscationBinding;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TranscationActivity extends AppCompatActivity {


    private static final String TAG = "TransactionActivity";
    private ActivityTranscationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTranscationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x2efd3Dc020D75f5Abf12C74F011aBB3Be8fc7f6C";
        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);

        TransactionReceipt transactionReceipt = user_dataContract.create_user_data("Josef Eric","Home",formatter.format(date)).sendAsync().join();
        if ((transactionReceipt.getTransactionHash()) != null) {
            binding.tvReceipt.setText(transactionReceipt.getTransactionHash());
        }
        else{
            binding.tvReceipt.setText("NULL");
        }
        // sendDatatoBlockChain("Home");

    }


    private void sendDatatoBlockChain(String placeName) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x2efd3Dc020D75f5Abf12C74F011aBB3Be8fc7f6C";
        String privateKey = "fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_sol_UserData user_dataContract = UserData_sol_UserData
                .load(contractAddress, web3j, credentials, contractGasProvider);


        user_dataContract.create_user_data("Josef Eric", placeName, formatter.format(date)).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                Log.d(TAG, transactionReceipt.getTransactionHash());
                Toast.makeText(getBaseContext(), "Transaksi Selesai", Toast.LENGTH_LONG).show();
                //replacedFragment(new HistoryUserFragment());
            }
        });

    }

    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, null);
        fragmentTransaction.addToBackStack(null).commit();
    }
}