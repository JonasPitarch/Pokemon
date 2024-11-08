package com.example.pokedexjonas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokedexjonas.databinding.FragmentFirstBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokedexJonas extends Fragment {

    private FragmentFirstBinding binding;
    private PokemonViewModel model; // Asegúrate de inicializar esto si lo usas en reload()
    private ArrayList<Pokemon> pokemons; // Lista de Pokémon para el adaptador
    private PokemonAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokemons = new ArrayList<>(); // Inicializa la lista de Pokémon
        adapter = new PokemonAdapter(getContext(), pokemons);
        binding.listaPokemon.setAdapter(adapter);

        binding.listaPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Pokemon selectedPokemon = (Pokemon) parent.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putSerializable("selectedPokemon", selectedPokemon);

                // Navega al SecondFragment con los datos
                NavHostFragment.findNavController(PokedexJonas.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
            }
        });


        // Llamar a la API en segundo plano
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Pokemon> fetchedPokemonList = PokeApi.buscar();

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (fetchedPokemonList != null && !fetchedPokemonList.isEmpty()) {
                        pokemons.clear(); // Limpiar la lista actual antes de agregar nuevos datos
                        pokemons.addAll(fetchedPokemonList); // Agregar Pokémon a la lista del adaptador
                        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                    } else {
                        Log.d("PokedexJonas", "No se encontraron Pokémon.");
                    }
                });
            }
        });
    }

    private void reload() {
        if (model != null) {
            model.reload(); // Asegúrate de que model esté inicializado antes de llamar a reload()
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
