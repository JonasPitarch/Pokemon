package com.example.jugadorapi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.jugadorapi.databinding.FragmentFirstBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private ArrayList<Jugador>jugadors;
    private  JugadorAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jugadors = new ArrayList<>(); // Inicializa la lista de nuestros jugadores
        adapter = new JugadorAdapter(getContext(), jugadors);
        binding.listaJugadores.setAdapter(adapter);

        binding.listaJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Jugador seleccionador = (Jugador) parent.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putSerializable("selectedJugador", seleccionador);

                // Navega al SecondFragment con los datos
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
            }
        });


        // Llamar a la API en segundo plano
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> bucajugador = JugadorApi.buscar();

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (bucajugador != null && !bucajugador.isEmpty()) {
                        jugadors.clear(); // Limpiar la lista actual antes de agregar nuevos datos
                        jugadors.addAll(bucajugador); // Agregar Jugador a la lista del adaptador
                        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                    } else {
                        Log.d("PokedexJonas", "No se encontraron Pokémon.");
                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}