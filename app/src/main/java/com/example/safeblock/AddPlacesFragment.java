package com.example.safeblock;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.safeblock.databinding.FragmentAddPlacesBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class AddPlacesFragment extends Fragment implements OnMapReadyCallback {


    private static final String TAG = "AddPlacesFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 20f;

    private Boolean mLocationPermissionGranted = false;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private FragmentAddPlacesBinding binding;
    FusedLocationProviderClient client;
    private Boolean HideMap = false;
    private Location currentLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddPlacesBinding.inflate(inflater,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        binding.buttonToHideMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HideMap == false){
                    HideMap = true;
                    binding.buttonToHideMap.setText("Show Map");
                    mapFragment.getView().setVisibility(View.INVISIBLE);
                }else{
                    HideMap = false;
                    binding.buttonToHideMap.setText("Hide Map");
                    mapFragment.getView().setVisibility(View.VISIBLE);
                }
            }
        });

        mapFragment.getMapAsync(this);

        binding.simpanButtonAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideMap = true;
                mapFragment.getView().setVisibility(View.INVISIBLE);
                binding.buttonToHideMap.setText("Show Map");

                if (!(binding.inputNamaTempat.getText().toString().equals(""))){
                    String placeName = binding.inputNamaTempat.getText().toString().trim();
                    if (currentLocation !=null){
                        LatLng latLngCurrentPosition = locationToLatLng(currentLocation);
                        binding.ivQRCode.setVisibility(View.VISIBLE);
                        getQRCode(placeName,latLngCurrentPosition);
                    }
                }
                else{
                    binding.ivQRCode.setVisibility(View.INVISIBLE);

                    Toast.makeText(getContext(),"Mohon Masukan Nama Tempat",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }

    private void getQRCode(String placeName,LatLng latLngPlace){
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            String placeData = new Gson().toJson(new PlaceData(placeName,latLngPlace.latitude,latLngPlace.longitude));
            BitMatrix matrix = writer.encode(placeData, BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            binding.ivQRCode.setImageBitmap(bitmap);
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(binding.inputNamaTempat.getApplicationWindowToken(),0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

       // getCurrentLocation();
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Toast.makeText(getContext(),"Location = " + location.getLatitude() + " "+ location.getLongitude(),Toast.LENGTH_LONG).show();
                    getCurrentPostion(location);
                    moveCamera(locationToLatLng(location),DEFAULT_ZOOM);
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                //Initialize Marker Options
                MarkerOptions markerOptions = new MarkerOptions();

                //Set Position of marker
                markerOptions.position(latLng);

                //Set title of marker
                markerOptions.title(latLng.latitude + " + " + latLng.longitude);

                //Remove All Marker
                mMap.clear();

                //Zoom marker
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

                //Add marker
                mMap.addMarker(markerOptions);
            }
        });


    }

    private void getCurrentPostion(Location location){
        currentLocation = location;
    }

    private LatLng locationToLatLng(Location location){
        return new LatLng(location.getLatitude(),location.getLongitude());
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera to: lat:" + latLng.latitude + " + long:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        makeMarker(latLng);
    }

    private void makeMarker(LatLng latLng){
        //Initialize Marker Options
        MarkerOptions markerOptions = new MarkerOptions();

        //Set Position of marker
        markerOptions.position(latLng);

        //Set title of marker
        markerOptions.title("Current Position");

        //Remove All Marker
        mMap.clear();

        //Zoom marker
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

        //Add marker
        mMap.addMarker(markerOptions);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0)
                && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
           // getCurrentLocation();
        } else {
            // When permission are denied
            // Display toast
            Toast
                    .makeText(getActivity(),
                            "Permission denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

}