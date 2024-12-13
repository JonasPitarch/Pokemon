package com.example.incidencias.ui.home;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.health.connect.datatypes.ExerciseRoute;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.incidencias.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ActivityResultLauncher<String[]> locationPermissionRequest;
    private Location mLastlocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.localitzacio;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        locationPermissionRequest = registerForActivityResult(new ActivityResultContracts
                        .RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null && fineLocationGranted) {
                        getLocation();
                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        getLocation();
                    } else {
                        Toast.makeText(requireContext(), "No hay coincidencia de permisos", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        return root;


    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), "Request permisssions", Toast.LENGTH_SHORT).show();
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            Toast.makeText(requireContext(), "getLocation: permissions granted", Toast.LENGTH_SHORT).show();
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    location -> {
                        if (location != null) {
                            mLastlocation = location;
                            binding.localitzacio.setText(
                                    String.format("Latitud: %1$.4f \n Longitud: %2$.4f\n Hora: %3$tr",
                                            mLastlocation.getLatitude(),
                                            mLastlocation.getLongitude(),
                                            mLastlocation.getTime()));
                        }
                        else {
                            binding.localitzacio.setText("Sin locaclización conocida");
                        }
                    }
            );
        }
    }

    private void fetchAddress(){
        Geocoder geocoder = new Geocoder(mContext,
                Locale.getDefault());
        List<Address>addresses = null;
        String resultMessage="";

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude()
            );
            if (addresses == null || addresses.size() == 0) {
                if (resultMessage.isEmpty()) {
                    resultMessage = "No s'ha trobat cap adreça";
                    Log.e(TAG, resultMessage);
                }
            }
            else {
                Address address = addresses.get(0);
                ArrayList<String>addressParts=new ArrayList<>();

                for (int i =0 ; i<=address.getMaxAddressLineIndex(); i++){
                    addressParts.add(address.getAddressLine(i));
                }

                resultMessage = TextUtils.join("\n", addressParts);
            }
        }
        catch (IOException e){
            resultMessage = "Servei no disponible";
            Log.e(TAG,resultMessage,e);
        }
        catch (IllegalArgumentException illegalArgumentException){
            resultMessage="Coordenades no valides";
            Log.e(TAG, resultMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}