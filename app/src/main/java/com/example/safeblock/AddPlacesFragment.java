package com.example.safeblock;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.safeblock.databinding.FragmentAddPlacesBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class AddPlacesFragment extends Fragment  implements OnMapReadyCallback {



    private static final String TAG = "AddPlacesFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //val mapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment;
       // val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        //SupportMapFragment mapFragment = (SupportMapFragment) childFragmentManager.findFragmentById(binding.map.getId());

        View view = inflater.inflate(R.layout.fragment_add_places,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }




    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        getDeviceLocation();
        if (ContextCompat.checkSelfPermission(getContext(),FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "HILU");
                mMap.setMyLocationEnabled(true);
            }
        }

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                //Add marker
                mMap.addMarker(markerOptions);
            }
        });
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission()");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getContext(),FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(getContext(),COURSE_LOCATION) ==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
            }else{
                ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG,"getDeviceLocation");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            if (mLocationPermissionGranted){
                @SuppressLint("MissingPermission") Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"onCompleted found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG,"onCompleted location null");
                            Toast.makeText(getContext(),"Location Not Found",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG,"getDeviceLocation: Security Exception"+e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG,"moveCamera to: lat:" + latLng.latitude + " + long:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
}