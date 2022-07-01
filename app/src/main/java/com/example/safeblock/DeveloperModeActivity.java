package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.safeblock.databinding.ActivityDeveloperModeBinding;
import com.example.safeblock.databinding.ActivityLoginDeveloperBinding;

import org.web3j.abi.datatypes.Bool;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class DeveloperModeActivity extends AppCompatActivity {

    private static final String TAG = "LoginDeveloperActivity ";
    private ActivityDeveloperModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeveloperModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCheckAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputNamaAdmin.getText().toString().trim();
                String address = binding.inputPublicAddressAdmin.getText().toString().trim();
                if(name.isEmpty() || address.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mohon Isi nama dan address", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean decision = checkAddresAlreadyUsed(name, address);
                    if (decision == true){
                        binding.tvState.setVisibility(View.VISIBLE);
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                    } else{
                        binding.tvState.setText("Address Sudah Digunakan");
                        binding.imgViewCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        binding.btnAddNewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputNamaAdmin.getText().toString().trim();
                String address = binding.inputPublicAddressAdmin.getText().toString().trim();
                String password = binding.inputPublicPassword.getText().toString().trim();
                if(name.isEmpty() || address.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mohon lengkapi data admin baru", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean decision = checkAddresAlreadyUsed(name, address);
                    if (decision == true){
                        binding.tvState.setVisibility(View.VISIBLE);
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                        Boolean signNewAdminSuccess = signNewAdmin(name,address, password);

                    } else{
                        binding.tvState.setText("Address Sudah Digunakan");
                        binding.imgViewCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        binding.tvSeeListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeveloperModeActivity.this, ListAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean checkAddresAlreadyUsed(String name, String address){
        Log.d(TAG, "checkAddresAlreadyUsed()");
        Boolean checkAddress = false;
        Boolean decision = false;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        //0x342673B9B479e8FFfcd8dE709f89f8EBaE111a1b
        //0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2
        String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = "8b8b105e6f96b7a230239d87d40e0aa96fb45f41f6f1b901b78c827e28648721";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        NewSafeBlock_sol_NewSafeBlock contract = NewSafeBlock_sol_NewSafeBlock.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            checkAddress = contract.verifyAdmin(address,name).sendAsync().get();
            if(checkAddress == true) {
                decision = false;
            } else{
                decision = true;
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return decision;
    }

    private Boolean signNewAdmin(String name, String address,String password){
        Boolean result = false;
        Log.d(TAG, "signNewAdmin()");
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        //0x342673B9B479e8FFfcd8dE709f89f8EBaE111a1b
        //0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2
        String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = "8b8b105e6f96b7a230239d87d40e0aa96fb45f41f6f1b901b78c827e28648721";

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        NewSafeBlock_sol_NewSafeBlock contract = NewSafeBlock_sol_NewSafeBlock.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            TransactionReceipt transactionReceipt = contract.createAdmin(address,name, password).sendAsync().join();
            if ((transactionReceipt.getTransactionHash()) != null) {
                binding.tvStateTransaction.setText("Sign New Admin Success\nTransaction Hash:\n"+transactionReceipt.getTransactionHash());
                binding.tvStateTransaction.setVisibility(View.VISIBLE);
                binding.imgViewCheckTransaction.setVisibility(View.VISIBLE);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}