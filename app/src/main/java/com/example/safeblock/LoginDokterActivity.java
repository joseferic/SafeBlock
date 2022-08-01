package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.safeblock.databinding.ActivityLoginDokterBinding;


import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class LoginDokterActivity extends AppCompatActivity {

    private static final String TAG = "LoginDokterActivity";
    ActivityLoginDokterBinding binding;
    public final String contractAddress = "0x938A5FA7b18699D656FF37f89358DB9ce8dD549C";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginDokterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.btnCheckDokter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = binding.inputNamaAdmin.getText().toString().trim();
//                String address = binding.inputPublicAddressAdmin.getText().toString().trim();
//                String privateKey = binding.inputPrivateKey.getText().toString().trim();
//                if (name.isEmpty() || address.isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Mohon Isi nama dan address", Toast.LENGTH_LONG).show();
//                } else {
//                    Boolean decision = checkAddresAlreadyUsed(name, address,privateKey);
//                    if (decision == true) {
//                        binding.tvStateTransaction.setVisibility(View.VISIBLE);
//                        binding.imgViewCheck.setVisibility(View.VISIBLE);
//                    } else {
//                        binding.tvStateTransaction.setVisibility(View.VISIBLE);
//                        binding.tvStateTransaction.setText("Informasi Tidak Tersedia.\nCek Kembali Nama & Address");
//                        binding.imgViewCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
//                        binding.imgViewCheck.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });

        binding.btnLoginDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputNamaAdmin.getText().toString().trim();
                String address = binding.inputPublicAddressAdmin.getText().toString().trim();
                String password = binding.inputPublicPassword.getText().toString().trim();
                String privateKey = binding.inputPrivateKey.getText().toString().trim();
                if (name.isEmpty() || address.isEmpty() || password.isEmpty() || privateKey.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mohon lengkapi data dokter baru", Toast.LENGTH_LONG).show();
                } else {
                    Boolean decision = checkAddresAlreadyUsed(name, address, privateKey);
                    if (decision == true) {
                        //binding.tvState.setVisibility(View.VISIBLE);
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                        Boolean loginDokterStatus = loginDokter(name, address, password, privateKey);
                        if (loginDokterStatus == true) {


                            Intent intent = new Intent(LoginDokterActivity.this, DokterModeActivity.class);
                            Dokter dokter = new Dokter(name, address, password, privateKey);
                            intent.putExtra("dokter", dokter);
                            startActivity(intent);

                        } else {
                            Log.v(TAG, "Error");
                        }
                    } else {
                        // binding.tvState.setText("Address Sudah Digunakan");
                        binding.imgViewCheck.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
                        binding.imgViewCheck.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        binding.tvDaftarDokterBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginDokterActivity.this, SignupDokterActivity.class));
            }
        });
    }

    private Boolean loginDokter(String name, String address, String password, String privateKeyAdmin) {
        Log.d(TAG, "loginDokter()");
        Boolean checkAddress;
        Boolean finalResult = false;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
       // String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = privateKeyAdmin;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaru contract = SmartContractBaru_sol_SmartContractBaru.load(contractAddress, web3j, credentials, contractGasProvider);
        try {
            checkAddress = contract.verifyDoctor(address, name).sendAsync().get();
            if (checkAddress == true) {
                Boolean checkValid = contract.verifyLoginDoctor(address, name, password).sendAsync().get();
                finalResult = checkValid;
            } else {
                finalResult = false;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return finalResult;
    }

    private Boolean checkAddresAlreadyUsed(String name, String address,String privateKeyUsed) {
        Log.d(TAG, "checkAddresAlreadyUsed()");
        Boolean checkAddress = false;
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        String privateKey = privateKeyUsed;

        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        SmartContractBaru_sol_SmartContractBaruTest contract = SmartContractBaru_sol_SmartContractBaruTest.load(contractAddress, web3j, credentials, contractGasProvider);
        try {
            String checkWallet = contract.getNameDoctorAddress(address).sendAsync().join();
            Log.v(TAG +"checkWallet =", checkWallet);
            Log.v(TAG+"name = ",name);
            if (checkWallet.equals(name)){
                checkAddress = contract.verifyDoctor(address, name).sendAsync().get();
            }
            else{
                return false;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return checkAddress;
    }
}