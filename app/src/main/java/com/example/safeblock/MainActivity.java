package com.example.safeblock;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.safeblock.databinding.ActivityMainBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

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

    public  Context getContext(){
        Context mContext = MainActivity.this;
        return mContext;
    }
}

