package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.safeblock.databinding.ActivitySignupDokterBinding;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class SignupDokterActivity extends AppCompatActivity {

    private static final String TAG = "LoginDeveloperActivity";
    private ActivitySignupDokterBinding binding;
    public final String contractAddress = "0xBb5a99B6B526671FE500e48FFf99aFCfFa88EBb3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupDokterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.btnCheckDokter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = binding.inputNamaAdmin.getText().toString().trim();
//                String address = binding.inputPublicAddressAdmin.getText().toString().trim();
//                String privateKey = binding.inputPrivateKey.getText().toString().trim();
//                if(name.isEmpty() || address.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Mohon Isi nama dan address", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Boolean decision = checkAddresAlreadyUsed(name, address,privateKey);
//                    if (decision == true){
//                        binding.tvState.setVisibility(View.VISIBLE);
//                        binding.imgViewCheck.setVisibility(View.VISIBLE);
//                    } else{
//                        binding.tvState.setText("Address Sudah Digunakan");
//                        binding.imgViewCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
//                        binding.imgViewCheck.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });

        binding.btnAddNewDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputNamaAdmin.getText().toString().trim();
                String address = binding.inputPublicAddressAdmin.getText().toString().trim();
                String password = binding.inputPublicPassword.getText().toString().trim();
                String privateKey = binding.inputPrivateKey.getText().toString().trim();
                if(name.isEmpty() || address.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mohon lengkapi data dokter baru", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean decision = checkAddresAlreadyUsed(name, address, privateKey);
                    if (decision == true){
                        binding.tvState.setVisibility(View.VISIBLE);
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                        Boolean signNewAdminSuccess = signNewDoctor(name,address, password,privateKey);

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
                Intent intent = new Intent(SignupDokterActivity.this, ListDokterActivity.class);
                startActivity(intent);
            }
        });

        binding.tvLoginDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupDokterActivity.this, LoginDokterActivity.class));
            }
        });
    }

    private Boolean checkAddresAlreadyUsed(String name, String address, String privateKeyUsed){
        Log.d(TAG, "checkAddresAlreadyUsed()");
        Boolean checkAddress = false;
        Boolean decision = false;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String privateKey = privateKeyUsed;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaruTest contract = SmartContractBaru_sol_SmartContractBaruTest.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            checkAddress = contract.verifyDoctor(address,name).sendAsync().get();
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

    private Boolean signNewDoctor(String name, String address, String password, String privateKeyUsed){
        Boolean result = false;
        Log.d(TAG, "signNewDoctor()");
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String privateKey = privateKeyUsed;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaruTest contract = SmartContractBaru_sol_SmartContractBaruTest.load(contractAddress, web3j, credentials, contractGasProvider);
        try{
            TransactionReceipt transactionReceipt = contract.createDoctorAccount(address,name, password).sendAsync().join();
            if ((transactionReceipt.getTransactionHash()) != null) {
                binding.tvStateTransaction.setText("Sign New Dokter Success\nTransaction Hash:\n"+transactionReceipt.getTransactionHash());
                binding.tvStateTransaction.setVisibility(View.VISIBLE);
                binding.imgViewCheckTransaction.setVisibility(View.VISIBLE);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}