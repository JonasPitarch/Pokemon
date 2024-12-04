package com.example.finalprojectt1;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.finalprojectt1.databinding.FragmentSecondBinding;
public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    private Jugadores jugadorSeleccionado;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            jugadorSeleccionado = (Jugadores) getArguments().getSerializable("jugadorseleccionado");
            mostrarDetallesJugador();
        }
    }
    private void mostrarDetallesJugador() {
        if (jugadorSeleccionado != null) {
            binding.nmp.setText(jugadorSeleccionado.getNombre());
            binding.dp.setText("Dorsal: " + jugadorSeleccionado.getDorsal());
            binding.ppp.setText("Posición: " + jugadorSeleccionado.getPosicion());
            Glide.with(this).load(jugadorSeleccionado.getImg()).into(binding.imgp);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
