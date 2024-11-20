package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private JugadorsViewModel model;
    private ArrayList<Jugador> jugadors; // Lista de Pokémon para el adaptador
    private JugadoresAdapter adapter;

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

        jugadors = new ArrayList<>(); // Inicializa la lista de Pokémon
        adapter = new JugadoresAdapter(getContext(), jugadors);
        binding.listajugador.setAdapter((ListAdapter) adapter);

        binding.listajugador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Jugador selectedPokemon = (Jugador) parent.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putSerializable("selectedPokemon", selectedPokemon);

                // Navega al SecondFragment con los datos
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
            }
        });


        // Llamar a la API en segundo plano
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> buscaJugadorList = JugadorAPI.buscar();

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (buscaJugadorList != null && !buscaJugadorList.isEmpty()) {
                        jugadors.clear(); // Limpiar la lista actual antes de agregar nuevos datos
                        jugadors.addAll(buscaJugadorList); // Agregar Pokémon a la lista del adaptador
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