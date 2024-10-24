package com.example.pokedexjonas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pokedexjonas.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokedexJonas extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<String> pokemon;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        pokemon = new ArrayList<>();
        pokemon.add("andres");
        pokemon.add("Iniesta");
        pokemon.add("marcos");

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Pokemon> pokemons = new ArrayList<>(); // Lista que usará el adaptador
        PokemonAdapter adapter = new PokemonAdapter(getContext(), pokemons);
        binding.listaPokemon.setAdapter(adapter);

        // Llamar a la API en segundo plano
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Pokemon> bucapokemon = PokeApi.buscar();
            getActivity().runOnUiThread(() -> {
                if (!bucapokemon.isEmpty()) {
                    pokemons.addAll(bucapokemon); // Agregar Pokémon a la lista del adaptador
                    adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                } else {
                    Log.d("PokemonFragment", "No se encontraron Pokémon.");
                }
            });
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}