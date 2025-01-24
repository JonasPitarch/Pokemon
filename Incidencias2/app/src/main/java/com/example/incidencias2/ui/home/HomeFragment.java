package com.example.incidencias2.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.example.incidencias2.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityResultLauncher<String[]> locationPermissionRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        locationPermissionRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                permissions -> {
                    Boolean fineLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null && fineLocationGranted) {
                        Toast.makeText(requireContext(), "Permiso de ubicación precisa concedido", Toast.LENGTH_SHORT).show();
                        getLocation();
                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        Toast.makeText(requireContext(), "Permiso de ubicación aproximada concedido", Toast.LENGTH_SHORT).show();
                        getLocation();
                    } else {
                        Toast.makeText(requireContext(), "Permisos de ubicación denegados", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        final TextView textView = binding.buttonLocation;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Llamada inicial a la localización
        getLocation();

        return root;
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(requireContext(), "Solicitando permisos", Toast.LENGTH_SHORT).show();
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            Toast.makeText(requireContext(), "Obteniendo localización", Toast.LENGTH_SHORT).show();
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    location -> {
                        if (location != null) {
                            fetchAddress(location);
                        } else {
                            binding.localitzacio.setText("Sin localización conocida");
                        }
                    });
        }
        binding.localitzacio.setText("Cargando...");
    }

    private void fetchAddress(Location location) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        Geocoder geocoder = new Geocoder(requireContext(),
                Locale.getDefault());

        executor.execute(() -> {
            List<Address> addresses = null;
            String resultMessage = "";

            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        1);

                if (addresses == null || addresses.size() == 0) {
                    resultMessage = "No se encontró ninguna dirección";
                    Log.e("INCIDENCIAS", resultMessage);
                } else {
                    Address address = addresses.get(0);
                    ArrayList<String> addressParts = new ArrayList<>();

                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressParts.add(address.getAddressLine(i));
                    }

                    resultMessage = TextUtils.join("\n", addressParts);
                }
            } catch (IOException ioException) {
                resultMessage = "Servicio no disponible";
                Log.e("INCIDENCIAS", resultMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                resultMessage = "Coordenadas no válidas";
                Log.e("INCIDENCIAS", resultMessage + ". Latitude = " +
                        location.getLatitude() + ", Longitude = " +
                        location.getLongitude(), illegalArgumentException);
            }

            String finalResultMessage = resultMessage;
            handler.post(() -> binding.localitzacio.setText(String.format(
                    "Dirección: %1$s \n Hora: %2$tr",
                    finalResultMessage, System.currentTimeMillis())));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
