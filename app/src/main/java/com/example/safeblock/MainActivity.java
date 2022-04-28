package com.example.safeblock;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.safeblock.databinding.ActivityMainBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.tabs.TabLayout;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkGoogleServicesVersion();

        replacedFragment(new UserDataFragment());

        binding.bottomNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home_bottom_nav:
                    replacedFragment(new UserDataFragment());
                    break;
                case R.id.camera_bottom_nav:
                    replacedFragment(new CameraFragment());
                    break;
                case R.id.history_bottom_nav:
                    replacedFragment(new HistoryUserFragment());
                    break;
                case R.id.addPlaces_bottom_nav:
                    replacedFragment(new AddPlacesFragment());
                    break;
            }
            return true;
        });

        //current date and time
        java.util.Date date = new java.util.Date();

        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );

        String contractAddress = "0x2Fd6E9DF12A797D8D2D76C000C8bCB6164f2985d";
        Credentials credentials = Credentials.create("fd20f2be43dd3fa879826279c6067a18aa5b9a40d5ed7f6c2e672e4154876ba5");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        UserData_SmartContract userData = UserData_SmartContract
                .load(contractAddress,web3j,credentials,contractGasProvider);

        try {

            Log.v("Data length list",userData.get_user_list_length().sendAsync().get().toString());
            //int list_length = parseInt(userData.get_user_list_length().sendAsync().get().toString());
            ArrayList<Data> list_user = new ArrayList<>();

            for (int i=0; i<2;i++){
                Data nData = new Data(
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component1(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component2(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component3(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component4(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component5(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component6(),
                        userData.users(BigInteger.valueOf(i)).sendAsync().get().component7()
                );
                Log.v("Data User Detail ",userData.users(BigInteger.valueOf(i)).sendAsync().get().toString());
                list_user.add(nData);
                Log.v("Data User On List ", nData.name);
                Log.v("Data List ", String.valueOf(list_user));
            }
        }catch (Throwable throwable){

        }
    }

    private void replacedFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment,null);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            finish();
        } else{
            backToast = Toast.makeText(getBaseContext(),"Tekan 2x Untuk Keluar",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public boolean checkGoogleServicesVersion(){
        Log.d(TAG, "checkGoogleServicesVersion:Checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "checkGoogleServicesVersion:Google Play Services is working");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "checkGoogleServicesVersion:an error occured");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
            return false;
        } else{
            Toast.makeText(this, "Can't Make Map Requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

//Log.v("Data User Detail ",userData.users(BigInteger.valueOf(1)).sendAsync().get().toString());
//        Test = findViewById(R.id.tv_helloWorld);
//        Test2 = findViewById(R.id.tv_helloWorld2);
//TextView Test;
//    TextView Test2;
//        Test.setText("Change This");
//        Test2.setText("Change This 2");
//Test2.setText(list_user.get(1).toString());
//Log.v("Data List User", list_user.get(1).toString());

  //  UserData_SmartContract userData = UserData_SmartContract
//                .load(contractAddress,web3j,credentials,contractGasProvider);
//        userData.get_user_list_length().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
//            @Override
//            public void accept(BigInteger data) throws Exception {
//                Log.v("Data", String.valueOf(data));
//            }
//        });

//Get_List_Length(web3j,credentials,contractGasProvider, contractAddress);

//Test.setText(List_Length.toString());



//        userData.create_user_data("Test 1", "Hospital",date.toString()).flowable()
//                .subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
//            @Override
//            public void accept(TransactionReceipt transactionReceipt) throws Exception {
//                Log.i("MainActivity",transactionReceipt.getBlockHash());
//            }
//        });
//        userData.create_user_data("Test 2", "Home",date.toString()).flowable()
//                .subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
//            @Override
//            public void accept(TransactionReceipt transactionReceipt) throws Exception {
//                Log.i("MainActivity","Data 2");
//            }
//        });
//        userData.users(BigInteger.valueOf(2)).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<Tuple7<BigInteger, String, String, String, String, String, Boolean>>() {
//            @Override
//            public void accept(Tuple7<BigInteger, String, String, String, String, String, Boolean> Data) throws Exception {
//                Log.i("DATA = ",Data.toString());
//            }
//
//        });

//        final Web3j web3j = Web3j.build(
//                new HttpService(
//                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
//                )
//        );

//        final String ethAddress = "0x249aD383023fFff7fA2d73f264d8907d4b124932";
//        try {
//            final EthGetBalance balanceResponse =
//                    web3j.ethGetBalance(ethAddress, DefaultBlockParameter.valueOf("latest"))
//                    .sendAsync().get(10, TimeUnit.SECONDS);
//            final BigInteger unscaledBalance = balanceResponse.getBalance();
//            Test.setText(unscaledBalance.toString());
//
//            final BigDecimal scaledBalance = new BigDecimal(unscaledBalance)
//                    .divide(new BigDecimal(1000000000000000000L), 10, RoundingMode.HALF_UP);
//            Test2.setText(scaledBalance.toString());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }