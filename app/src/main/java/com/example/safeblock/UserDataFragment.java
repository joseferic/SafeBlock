package com.example.safeblock;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.safeblock.R;
import com.example.safeblock.databinding.FragmentUserDataBinding;
import com.example.safeblock.user_data;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;


public class UserDataFragment extends Fragment {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private final int GALLERY_REQ_CODE = 1000;
    public Boolean stateDataSaved = false;
    public user_data userData;
    public Uri uriPictureTestResult;
    private FragmentUserDataBinding binding;
    private Boolean Hide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDataBinding.inflate(inflater, container, false);

        binding.tvUserData.setVisibility(View.INVISIBLE);

        binding.inputNama.setVisibility(View.VISIBLE);
        binding.inputEmail.setVisibility(View.VISIBLE);
        binding.inputPrivateKey.setVisibility(View.VISIBLE);
        binding.simpanButton.setVisibility(View.VISIBLE);
        binding.spinner.setVisibility(View.VISIBLE);
        binding.addCovidTestResult.setVisibility(View.VISIBLE);
        binding.imageViewTestResult.setVisibility(View.VISIBLE);
      //  binding.inputPasswordGmail.setVisibility(View.VISIBLE);
        binding.tvInputStatusTerinfeksi.setVisibility(View.VISIBLE);

        binding.addCovidTestResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });


            checkState();

        if (stateDataSaved == true) {
            Hide = true;
            String status;
            if (userData != null) {
//                if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
//
//                    binding.imageViewTestResult.setImageURI(Uri.parse(userData.picture));
//                }
                binding.tvUserData.setVisibility(View.VISIBLE);
                binding.imageViewTestResult.setVisibility(View.VISIBLE);
                if (userData.infected == true){
                    status = "Positif";
                }
                else if (userData.infected == false){
                    status = "Negatif";
                }
                else{
                    status = "Negatif";
                }
                binding.tvUserData.setText(
                        "Nama User = " + userData.name +
                                "\n\nEmail User = " + userData.email +
                                "\n\nWallet User = " + userData._walletAddress +
                                "\n\nStatus: " + status);
                binding.spinner.setVisibility(View.INVISIBLE);
                binding.addCovidTestResult.setVisibility(View.INVISIBLE);
            } else if (userData == null) {
                binding.tvUserData.setVisibility(View.INVISIBLE);

            }
            binding.inputNama.setVisibility(View.INVISIBLE);
            binding.inputEmail.setVisibility(View.INVISIBLE);
            binding.inputPrivateKey.setVisibility(View.INVISIBLE);
            binding.simpanButton.setVisibility(View.INVISIBLE);
           // binding.inputPasswordGmail.setVisibility(View.INVISIBLE);
            binding.tvInputStatusTerinfeksi.setVisibility(View.INVISIBLE);

            binding.imageViewTestResult.setVisibility(View.VISIBLE);

            binding.backButton.setText("Input Data");

        } else if (stateDataSaved == false) {
            Hide = false;
            binding.inputNama.setVisibility(View.VISIBLE);
            binding.inputEmail.setVisibility(View.VISIBLE);
            binding.inputPrivateKey.setVisibility(View.VISIBLE);
           // binding.inputPasswordGmail.setVisibility(View.VISIBLE);
            binding.tvInputStatusTerinfeksi.setVisibility(View.VISIBLE);

            binding.spinner.setVisibility(View.VISIBLE);
            binding.simpanButton.setVisibility(View.VISIBLE);
            binding.imageViewTestResult.setVisibility(View.VISIBLE);
            binding.backButton.setText("Back");
        } else {
            Toast.makeText(getContext(), "stateDataSaved = null", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.TrueOrFalse, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean infected;
                String status;
                if (binding.spinner.getSelectedItem().toString().equals("Positif")) {
                    infected = true;
                    status = "Positif";
                } else if (binding.spinner.getSelectedItem().toString().equals("Negatif")) {
                    infected = false;
                    status = "Negatif";
                } else {
                    infected = null;
                    status = null;
                }

                if (uriPictureTestResult == null){
                    uriPictureTestResult = Uri.parse("null");
                }

//                if(binding.inputPasswordGmail.getText().toString().trim().isEmpty()){
//                    Toast.makeText(getContext(),"Mohon isi password gmail",Toast.LENGTH_SHORT);
//                    return;
//                }

                user_data data = new user_data(
                        binding.inputNama.getText().toString().trim(),
                        binding.inputEmail.getText().toString().trim(),
                        binding.inputPrivateKey.getText().toString().trim(),
                        infected,
                        uriPictureTestResult.toString()
                        //binding.inputPasswordGmail.getText().toString().trim()
                );
                saveUserData(data);

                checkState();
                Hide = true;
                if (userData != null) {

                    binding.tvUserData.setVisibility(View.VISIBLE);
//                    if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
//                        binding.imageViewTestResult.setImageURI(Uri.parse(userData.picture));
//                    }
                    binding.tvUserData.setText(
                            "Halo " + userData.name +
                                    "\n\nEmail: " + userData.email +
                                    "\n\nPrivate Key Wallet: " + userData._walletAddress +
                                    "\n\nStatus: "+ status);

                } else if (userData == null) {
                    binding.tvUserData.setVisibility(View.INVISIBLE);

                }
                binding.inputNama.setVisibility(View.INVISIBLE);
                binding.inputEmail.setVisibility(View.INVISIBLE);
                binding.inputPrivateKey.setVisibility(View.INVISIBLE);
               // binding.inputPasswordGmail.setVisibility(View.INVISIBLE);
                binding.tvInputStatusTerinfeksi.setVisibility(View.INVISIBLE);

                binding.simpanButton.setVisibility(View.INVISIBLE);
                binding.spinner.setVisibility(View.INVISIBLE);
                binding.imageViewTestResult.setVisibility(View.VISIBLE);
                binding.addCovidTestResult.setVisibility(View.INVISIBLE);
                binding.backButton.setVisibility(View.VISIBLE);
                binding.backButton.setText("Input Data");
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Hide == false) {
                    Hide = true;

                    String status;
                    if (userData.infected = true) {

                        status = "Positif";
                    } else if (userData.infected = false) {

                        status = "Negatif";
                    } else {
                        status = null;
                    }

                    if (userData != null) {

                        binding.tvUserData.setVisibility(View.VISIBLE);
//                        if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
//                            binding.imageViewTestResult.setImageURI(Uri.parse(userData.picture));
//                        }
                        binding.tvUserData.setText(
                                "Nama User = " + userData.name +
                                        "\n\nEmail User = " + userData.email +
                                        "\n\nWallet User = " + userData._walletAddress +
                                        "\n\nStatus: " + status);
                    } else if (userData == null) {
                        binding.tvUserData.setVisibility(View.INVISIBLE);

                    }
                    binding.inputNama.setVisibility(View.INVISIBLE);
                    binding.inputEmail.setVisibility(View.INVISIBLE);
                    binding.inputPrivateKey.setVisibility(View.INVISIBLE);
                  //  binding.inputPasswordGmail.setVisibility(View.INVISIBLE);
                    binding.tvInputStatusTerinfeksi.setVisibility(View.INVISIBLE);

                    binding.simpanButton.setVisibility(View.INVISIBLE);
                    binding.spinner.setVisibility(View.INVISIBLE);

                    binding.addCovidTestResult.setVisibility(View.INVISIBLE);
                    binding.backButton.setVisibility(View.VISIBLE);
                    binding.backButton.setText("Input Data");
                } else if (Hide == true) {
                    Hide = false;
                    binding.tvUserData.setVisibility(View.INVISIBLE);

                    binding.inputNama.setVisibility(View.VISIBLE);
                    binding.inputEmail.setVisibility(View.VISIBLE);
                    binding.inputPrivateKey.setVisibility(View.VISIBLE);
                  //  binding.inputPasswordGmail.setVisibility(View.VISIBLE);
                    binding.tvInputStatusTerinfeksi.setVisibility(View.VISIBLE);

                    binding.spinner.setVisibility(View.VISIBLE);
                    binding.backButton.setVisibility(View.VISIBLE);
                    binding.simpanButton.setVisibility(View.VISIBLE);
                    binding.spinner.setVisibility(View.VISIBLE);
                    binding.addCovidTestResult.setVisibility(View.VISIBLE);
                    binding.backButton.setText("Back");
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                uriPictureTestResult = data.getData();

                binding.imageViewTestResult.setImageURI(data.getData());
            }
        }

    }

    public void saveUserData(user_data data) {
        //set variables of 'myObject', etc.
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString("Data", json);
        Log.d("UserDataFragment", data.toString());
        prefsEditor.commit();
    }

    public void checkState() {
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("Data", "");
        if (json != null) {
            userData = gson.fromJson(json, user_data.class);
            if (userData != null) {
                stateDataSaved = true;
            } else {
                stateDataSaved = false;
            }
        } else {
            stateDataSaved = false;
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


}

